package com.sample.tasks

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.beans.factory.DisposableBean
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.coroutineContext

class TaskService(val broadcastChannel: BroadcastChannel<String>) : DisposableBean {
    override fun destroy() = tasks.job.cancel(CancellationException("Spring exiting"))

    private val taskMutex = Mutex()
    private var tasks = TaskScope()

    suspend fun launchTask(): String {
        val taskName = tasks.launchTask()
        broadcastChannel.send("launched task $taskName")
        return taskName
    }

    fun tasks(): Flow<Task> =
        tasks.tasks.toList().map { (n, j) -> j.toTask(n) }.sortedBy { it.name }.asFlow()

    suspend fun stopAll() = taskMutex.withLock {
        broadcastChannel.send("stopping tasks")
        tasks.job.cancel(CancellationException("Stopping tasks"))
        tasks = TaskScope()
    }
}

class TaskScope() : CoroutineScope by CoroutineScope(Dispatchers.IO + Job()) {
    val job: Job
        get() = coroutineContext[Job]!!

    val count = AtomicInteger()
    val tasks = ConcurrentHashMap<String, Job>()

    fun launchTask(): String {
        val i = count.incrementAndGet()
        val name = "$i-task"
        val job = launch(CoroutineName(name)) { longRunning() }
        tasks.put(name, job)
        return name
    }

}

suspend fun name(): String? = coroutineContext[CoroutineName]?.name

suspend fun longRunning() {
    var counter = 0
    while (true) {
        delay(5000)
        println("${name()} ${counter++} Running")
    }
}

package com.sample.tasks

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.coroutineContext

/** TaskService uses TaskScope to implement its methods */
class TaskService() {

    suspend fun launchTask(): String {
        return "todo"
    }

    fun tasks(): Flow<Task> = TODO()

    suspend fun stopAll():Unit = TODO()
}

/** TaskScope is able to launch coroutines, keep track of them and cancel them all.
 * To do that it has a CoroutineScope. (Or alternatively _is_ a CoroutineScope )
 * Taskscope can only be used once: after canceling it's tasks a new instance needs to be created.
 */
class TaskScope() {

    fun launchTask(): String {
        return "task name"
    }
}

suspend fun name(): String? = coroutineContext[CoroutineName]?.name

/** A CoroutineScope needs a dispatcher and a job */
fun createScope() = CoroutineScope(Dispatchers.Default + Job())

/** our task that is 'doing' some long running work. In our case it just prints every 5 sec and keeps running until */
suspend fun longRunning() {
    //TODO
}

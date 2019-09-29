package com.sample.task

import com.sample.tasks.TaskScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking<Unit>{
    withTimeout(15000) {
        val channel = BroadcastChannel<String>(1)

        val scope = TaskScope(channel)
        scope.launchTask()

        scope.launchTask()

        delay(15000)
    }
}
package com.sample.task

import com.sample.tasks.TaskScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking<Unit>{
    withTimeout(15000) {

        val scope = TaskScope()
        scope.launchTask()

        scope.launchTask()

        delay(15000)
    }
}
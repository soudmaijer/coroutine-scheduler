package com.sample.tasks

import kotlinx.coroutines.Job

data class Task(val name: String, val isActive: Boolean, val isCompleted: Boolean, val isCancelled: Boolean, val cancellationMsg: String?)

fun Job.toTask(name: String) = Task(name, isActive, isCompleted, isCancelled, isActive.takeIf { !it }?.let { getCancellationException().message })

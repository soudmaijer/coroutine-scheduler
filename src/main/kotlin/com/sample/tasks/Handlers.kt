package com.sample.tasks

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.renderAndAwait


@Suppress("UNUSED_PARAMETER")
class TaskHandler(private val taskService: TaskService){
	suspend fun tasks(request: ServerRequest) =
		ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(taskService.tasks())

	suspend fun tasksView(request: ServerRequest) =
		ok().renderAndAwait("tasks",mapOf("tasks" to taskService.tasks()))

	suspend fun start(request: ServerRequest) =
		ok().bodyValueAndAwait(taskService.launchTask())

	suspend fun stopTasks(request: ServerRequest) =
		ok().bodyValueAndAwait(taskService.stopAll())
}
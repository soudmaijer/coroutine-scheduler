package com.sample.tasks

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

// TODO
@ExperimentalCoroutinesApi
fun routes(
//	taskHandler: TaskHandler
) = coRouter {
//	GET("/", taskHandler::tasksView)
//	GET("/api/start", taskHandler::start)
//	GET("/api/stop", taskHandler::stopTasks)
}

suspend fun dummy(request: ServerRequest):ServerResponse= ServerResponse.ok().bodyValueAndAwait("bla")

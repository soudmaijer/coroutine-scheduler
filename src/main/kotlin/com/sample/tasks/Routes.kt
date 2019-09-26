package com.sample.tasks

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.web.reactive.function.server.coRouter

@ExperimentalCoroutinesApi
fun routes(taskHandler: TaskHandler) = coRouter {
	GET("/", taskHandler::tasksView)
	GET("/api/start", taskHandler::start)
	GET("/api/stop", taskHandler::stopTasks)
}

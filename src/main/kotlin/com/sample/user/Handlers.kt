package com.sample.user

import com.sample.SampleProperties
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.renderAndAwait

@ExperimentalCoroutinesApi
@Suppress("UNUSED_PARAMETER")
class UserHandler(
    private val repository: UserRepository,
    private val configuration: SampleProperties) {

    suspend fun listApi(request: ServerRequest) =
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait<User>(repository.findAll())

    suspend fun userApi(request: ServerRequest) =
        ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(repository.findOne(request.pathVariable("login")))

    suspend fun listView(request: ServerRequest) =
        ServerResponse.ok().renderAndAwait("users", mapOf("users" to repository.findAll()))

    suspend fun conf(request: ServerRequest) =
        ServerResponse.ok().bodyValueAndAwait(configuration.message)

}

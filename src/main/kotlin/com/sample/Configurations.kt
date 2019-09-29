package com.sample

import com.sample.tasks.routes
import com.sample.tasks.toWebsocketHandler
import com.sample.user.UserRepository
import com.sample.ws.logReceivedMessageWsHandler
import com.sample.ws.websockets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.asFlux
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbcH2
import org.springframework.fu.kofu.webflux.mustache
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import reactor.core.publisher.Mono

val webConfig = configuration {
    beans {
        bean(::routes)
        websockets {
            mapOf(
                "/ws/pingpong" to logReceivedMessageWsHandler,
                "ws/task" to ref<BroadcastChannel<String>>().toWebsocketHandler()
            )
        }
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        mustache()
        codecs {
            string()
            jackson()
        }
    }
}



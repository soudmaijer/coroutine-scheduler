package com.sample.tasks

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactor.asFlux
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage

@FlowPreview
inline fun <A> BroadcastChannel<A>.toWebsocketHandler(crossinline serializer: (a: A) -> String = { it.toString() }) = WebSocketHandler { session ->
    println("Websocket opened ${session.id}")

    val flow: Flow<WebSocketMessage> = this.openSubscription().consumeAsFlow()
        .map { session.textMessage(serializer(it)) }
    val sendingMessages = flow
        .asFlux()
    session.send(sendingMessages)
        .and(session.receive()
            .doOnNext { println("Websocket ${session.id} received msg $it ") }
            .then())
}


package com.sample.ws

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Mono

fun BeanDefinitionDsl.websocket(vararg urlMapping: Pair<String, WebSocketHandler>) =
    websocket(urlMapping.toMap())

fun BeanDefinitionDsl.websocket(urlMap: Map<String, WebSocketHandler>) {
    websockets { urlMap }
}

fun BeanDefinitionDsl.websockets(wsHandlers: BeanDefinitionDsl.BeanSupplierContext.() -> Map<String, WebSocketHandler>) {
    bean<HandlerMapping> {
        bean<WebSocketHandlerAdapter>()
        val order = -1 // before annotated controllers
        SimpleUrlHandlerMapping(wsHandlers(), order)
    }
}

/** enable websocket handling in webflux */
fun BeanDefinitionDsl.websockets() {
    bean<WebSocketHandlerAdapter>()
}

/** example websocket handler that logs everything received and sends one hello msg */
val logReceivedMessageWsHandler: WebSocketHandler = WebSocketHandler { session ->
    val sendingFlux = Mono.just(session.textMessage("hello"))
    session.send(sendingFlux)
        .and(
            session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .log()
                .then())
}

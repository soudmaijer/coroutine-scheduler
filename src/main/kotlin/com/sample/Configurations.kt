package com.sample

import com.sample.user.routes
import com.sample.ws.logReceivedMessageWsHandler
import com.sample.ws.websockets
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.webflux.mustache
import org.springframework.fu.kofu.webflux.webFlux

val webConfig = configuration {
    beans {
        bean(::routes) // TODO: replace with task routes
        websockets {
            mapOf(
                "/ws/pingpong" to logReceivedMessageWsHandler
                // TODO
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



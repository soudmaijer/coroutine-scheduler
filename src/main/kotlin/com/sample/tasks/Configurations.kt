package com.sample.tasks

import kotlinx.coroutines.channels.BroadcastChannel
import org.springframework.fu.kofu.configuration

val taskConfig = configuration {
    beans {
        bean<TaskService>()
        bean<TaskHandler>()
        bean<BroadcastChannel<String>> { BroadcastChannel(1) }
    }
}

package com.sample.tasks

import org.springframework.fu.kofu.configuration

val taskConfig = configuration {
    beans {
        bean<TaskService>()
        bean<TaskHandler>()
    }
}

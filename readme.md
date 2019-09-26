# Coroutine workshop

### Goals
Have fun with suspendable functions, channels & flows, coroutines. Especially ones that run for extended time, ie not scoped within method. 
Also we'll interact with Spring Reactor and work with websockets.

### Exercises
The application uses the upcoming coroutine enabled Spring Boot 2.2 and [functional configuration](https://github.com/spring-projects-experimental/spring-fu) instead of annotations.

#### Exercise A1

Implement a suspendable function that runs forever, printing every 500 ms an increasing counter. This will be the long running task we want to schedule.
Implement a `TaskScope` with a coroutine scope and that implements a method start and method tasks. `start` starts a new scheduled task. `tasks` returns the currently running tasks. Use a counter to distinguish tasks.
Implement a `TaskService` that uses `TaskScope` to implements `start`, `tasks` and `stopAll` 
implement routes for TaskService.
try it out

#### Exercise A2
Send messages to a websocket.
Implement a webflux [WebsocketHandler](https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/webflux-websocket.adoc) such that we can send to the websockets that connect to it from our `TaskService`.
Use as little of Reactor as possible; we have coroutines after all.
Look at the provided `WebsocketHandler` called `logReceivedMessageWsHandler`.
Use http://www.websocket.org/echo.html to listen to your websocket
         
#### Exercise A3
Coroutines and exception handling.

Change the longrunning suspendable function to now and then throw an exception.
Change the TaskService so that other tasks keep on running when an exception occurs. Log the exception and publish to the websocket. 
         
#### Exercise A4
Store the scheduler history to a database using R2DBC. See the `user` package for example code that you can adapt.


#### Exercise B 
Advanced: parallel processing on Flows is still WIP within Jetbrains. They're still considering multiple options. In the meantime: implement it yourself.
Implement the following signature. 
```
fun <T, R> Flow<T>.parallelMap(
                                            bufferSize: Int,
                                            concurrency: Int,
                                            transform: suspend (value: T) -> R
                                        ): Flow<R>
```
Either using deferred values or by having workers that read from input channel (fan out) and write to output channel (fan in)


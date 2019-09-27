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
hidden hints:
- taskscope: either it _has_ a scope that we'll use when launching coroutines, or it _is_ a CoroutineScope by having a coroutinecontext with a Job and a Dispatcher. Use delegation!
- listing the tasks can be done in two ways: 1. maintain a `Map<String,Job>` or 2. use the children of the job. How do we know the name of the Job then?
- think about race conditions in updating data

#### Exercise A2
Send messages to a websocket.
Implement a webflux [WebsocketHandler](https://github.com/spring-projects/spring-framework/blob/master/src/docs/asciidoc/web/webflux-websocket.adoc) such that we can send to the websockets that connect to it from our `TaskService`.
Use as little of Reactor as possible; we have coroutines after all.
Look at the provided `WebsocketHandler` called `logReceivedMessageWsHandler`.
Use http://www.websocket.org/echo.html to listen to your websocket

Hidden hints:
1. create broadcastchannel as spring managed bean
2. send "start" msg to it from TaskService
3. implement the function `BroadcastChannel<String>.toWebsocketHandler(): WebSocketHandler`.
  hints: the websocketsession.send method takes a reactive streams Producer as an argument
         you can consume a broadcastchannel as flow. which you can turn into a Flux. 
         next: implement for BroadcastChannel<A>
4. register websocket handler. Test with websocket.org
         
#### Exercise A3
Coroutines and exception handling.

Change the longrunning suspendable function to now and then throw an exception.
Change the TaskService so that other tasks keep on running when an exception occurs. Log the exception and publish to the websocket. 

hidden hints:
    either wrap the launch call in a try catch. Or use a different root Job: SupervisorJob and CoroutineExceptionHandler
         
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


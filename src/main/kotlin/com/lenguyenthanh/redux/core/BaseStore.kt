package com.lenguyenthanh.redux.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

open class BaseStore<State, Action>(
    private val reducer: Reducer<State, Action>,
    initialStateSupplier: () -> State,
    private val log: Log? = null
) : Store<State, Action> {

    private val incomingAction = Channel<Action>(37)

    private var state = initialStateSupplier()
    private lateinit var listener: Listener<State>

    override suspend fun dispatch(action: Action) {
        incomingAction.send(action)
    }

    fun CoroutineScope.init() = launch {
        while (true) {
            select<Unit> {
                incomingAction.onReceive {
                    val newState = reducer(state, it)
                    log?.log("SimpleRedux", "Thread: ${Thread.currentThread().name}")
                    log?.log("SimpleRedux", "old $state, action: $it, new: $newState")
                    listener.onStateChange(newState)
                    state = newState
                }
            }
        }
    }


    override fun currentState(): State =
        state

    fun setListener(listener: Listener<State>) {
        this.listener = listener
    }
}
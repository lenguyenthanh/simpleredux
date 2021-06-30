package com.lenguyenthanh.redux.core

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.updateAndGet


open class BaseStore<State, Action>(
    private val reducer: Reducer<State, Action>,
    initialStateSupplier: () -> State,
    private val log: Log? = null
) : Store<State, Action> {

    private val state = atomic(initialStateSupplier())
    private var listener: Listener<State>? = null

    override fun dispatch(action: Action) {
        state.updateAndGet {
            val newState = reducer(it, action)
            log?.log("SimpleRedux", "old $it, action: $action, new: $newState")
            listener?.onStateChange(newState)
            newState
        }
    }

    override fun currentState(): State =
        state.value

    override fun setListener(listener: Listener<State>) {
        this.listener = listener
    }

}
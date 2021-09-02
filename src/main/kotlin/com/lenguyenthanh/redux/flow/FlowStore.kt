package com.lenguyenthanh.redux.flow

import com.lenguyenthanh.redux.core.BaseStore
import com.lenguyenthanh.redux.core.Listener
import com.lenguyenthanh.redux.core.Log
import com.lenguyenthanh.redux.core.Reducer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class FlowStore<State, Action>(
    reducer: Reducer<State, Action>,
    initialStateSupplier: () -> State,
    log: Log? = null
) : BaseStore<State, Action>(reducer, initialStateSupplier, log) {

    private val stateSubject by lazy {
        MutableStateFlow(currentState())
    }

    private val listener = object : Listener<State> {
        override fun onStateChange(state: State) {
            runBlocking {
                stateSubject.emit(state!!)
            }
        }
    }

    init {
        setListener(listener)
    }

    fun states(): StateFlow<State> = stateSubject

}
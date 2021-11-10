package com.lenguyenthanh.redux.flow

import com.lenguyenthanh.redux.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FlowStore<State, Action>(
    private val reducer: Reducer<State, Action>,
    private val initialStateSupplier: () -> State,
    private val log: Log? = null
) : Store<State, Action> {

    private val store = BaseStore(reducer, initialStateSupplier, log)

    private val listener = object : Listener<State> {
        override suspend fun onStateChange(state: State) {
            println("emit $state")
            stateSubject.emit(state)
        }
    }

    init {
        store.setListener(listener)
    }

    fun CoroutineScope.init() = with(store) {
        init()
    }

    private val stateSubject by lazy {
        MutableSharedFlow<State>(1, 37)
    }

    fun states(): Flow<State> = stateSubject

    override suspend fun dispatch(action: Action) {
        store.dispatch(action)
    }

    override fun currentState(): State {
        return store.currentState()
    }

}
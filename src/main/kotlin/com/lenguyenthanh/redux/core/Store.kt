package com.lenguyenthanh.redux.core


interface Store<State, Action> : Dispatcher<Action> {
    fun currentState(): State
    fun setListener(listener: Listener<State>)
}
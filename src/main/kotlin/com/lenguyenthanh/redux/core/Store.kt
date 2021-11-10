package com.lenguyenthanh.redux.core

import kotlinx.coroutines.CoroutineScope


interface Store<State, Action> : Dispatcher<Action> {
    fun currentState(): State
}
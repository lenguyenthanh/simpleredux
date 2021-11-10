package com.lenguyenthanh.redux.core

interface Dispatcher<Action> {
    suspend fun dispatch(action: Action)
}
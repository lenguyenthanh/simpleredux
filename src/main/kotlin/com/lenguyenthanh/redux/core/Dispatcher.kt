package com.lenguyenthanh.redux.core

interface Dispatcher<Action> {
    fun dispatch(action: Action)
}
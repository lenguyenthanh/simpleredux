package com.lenguyenthanh.redux

interface Dispatcher<Action> {
    fun dispatch(action: Action)
}
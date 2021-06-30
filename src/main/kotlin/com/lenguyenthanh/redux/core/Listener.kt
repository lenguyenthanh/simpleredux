package com.lenguyenthanh.redux.core


interface Listener<State> {
    fun onStateChange(state: State)
}
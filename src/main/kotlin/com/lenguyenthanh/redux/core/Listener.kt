package com.lenguyenthanh.redux.core

import kotlinx.coroutines.CoroutineScope


interface Listener<State> {
    suspend fun onStateChange(state: State)
}
package com.lenguyenthanh.redux

fun <State> combineReducers(vararg reducers: Reducer<State>): Reducer<State> {
    return { initial, action ->
        reducers.fold(initial) { state, reducer ->
            reducer(state, action)
        }
    }
}





package com.lenguyenthanh.redux.core

fun <State, Action> combineReducers(vararg reducers: Reducer<State, Action>): Reducer<State, Action> {
    return { initial, action ->
        reducers.fold(initial) { state, reducer ->
            reducer(state, action)
        }
    }
}

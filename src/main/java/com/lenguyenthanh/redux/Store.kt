package com.lenguyenthanh.redux

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Store<State>(private val reducer: Reducer<State>, initialState: State) : Dispatcher {

    private val subject: BehaviorSubject<State> by lazy {
        BehaviorSubject.createDefault<State>(initialState)
    }

    fun states(): Observable<State> {
        return subject.hide().distinctUntilChanged()
    }

    override fun dispatch(action: Action) {
        val newState = reducer(subject.value!!, action)
        subject.onNext(newState)
    }

    fun currentState(): State {
        return subject.value!!
    }
}
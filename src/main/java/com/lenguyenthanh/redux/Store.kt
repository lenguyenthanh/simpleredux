package com.lenguyenthanh.redux

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Store<State, Action>(private val reducer: Reducer<State, Action>, initialStateSupplier: () -> State, private val log: Log? = null) : Dispatcher<Action> {

    private val subject: BehaviorSubject<State> by lazy {
        BehaviorSubject.createDefault<State>(initialStateSupplier())
    }

    private val threadSafeSubject by lazy { subject.toSerialized() }

    fun states(): Observable<State> {
        return threadSafeSubject.hide().distinctUntilChanged()
    }

    override fun dispatch(action: Action) {
        val newState = reducer(subject.value!!, action)
        log?.log("SimpleRedux", "old ${subject.value}, action: $action, new: $newState")
        threadSafeSubject.onNext(newState)
    }

    fun currentState(): State {
        return subject.value!!
    }
}
package com.lenguyenthanh.redux.rx2

import com.lenguyenthanh.redux.core.BaseStore
import com.lenguyenthanh.redux.core.Listener
import com.lenguyenthanh.redux.core.Log
import com.lenguyenthanh.redux.core.Reducer
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class RxStore<State, Action>(
    reducer: Reducer<State, Action>,
    initialStateSupplier: () -> State,
    log: Log? = null
) : BaseStore<State, Action>(reducer, initialStateSupplier, log) {

    private val subject: BehaviorSubject<State> by lazy {
        BehaviorSubject.createDefault(currentState())
    }

    private val threadSafeSubject by lazy { subject.toSerialized() }

    private val listener = object : Listener<State> {
        override fun onStateChange(state: State) {
            threadSafeSubject.onNext(state!!)
        }
    }

    init {
        setListener(listener)
    }

    fun states(): Observable<State> =
        threadSafeSubject.hide().distinctUntilChanged()

}
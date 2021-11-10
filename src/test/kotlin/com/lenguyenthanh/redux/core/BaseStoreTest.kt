package com.lenguyenthanh.redux.core

import com.lenguyenthanh.redux.flow.FlowStore
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class BaseStoreTest : StringSpec({


    val reducer: Reducer<State, Action> = { state, action ->
        when (action) {
            is Action.ChangeAge -> state.copy(age = state.age + action.age)
            is Action.ChangeName -> state.copy(name = action.name)
        }
    }



    "dispatch FlowStore" {

        val store = FlowStore(reducer, { State("Joe", 0) }, object : Log {
            override fun log(tag: String, message: String) {
                println(message)
            }
        })
        val stop = with(store) {
            GlobalScope.init()
        }
        var all = 0
        GlobalScope.launch {
            val list = store.states().map { it.age }
//                .take(100)
                .collect {
                    println("Collected Flow $it")
                }
//            println(list)
        }
        val n = 10
        val ages = 1..n
        val result = ages.forEach { age ->
            GlobalScope.launch {
                store.dispatch(Action.ChangeAge(age))
            }
        }
        println("result $result")

//        delay(1000)
        Thread.sleep(4000)
    }

    "dispatch BaseStore" {
        val store = BaseStore(reducer, { State("Joe", 0) }, object : Log {
            override fun log(tag: String, message: String) {
                println(message)
            }
        })
        val mutableStateFlow = MutableStateFlow()<State>(1, 10)
        store.setListener(object : Listener<State> {
            override suspend fun onStateChange(state: State) {
                mutableStateFlow.emit(state)
            }

        })
        val stop = with(store) {
            GlobalScope.init()
        }

        GlobalScope.launch {
            val list = mutableStateFlow.map { it.age }
                .collect {
                    println("Collected BaseStore $it")
                }
        }

        val n = 10
        val ages = 1..n
        val result = ages.forEach { age ->
            GlobalScope.launch {
                store.dispatch(Action.ChangeAge(age))
            }
        }
        println("result $result")

//        delay(1000)
        Thread.sleep(4000)
    }
})

data class State(val name: String, val age: Int)
sealed class Action {
    data class ChangeName(val name: String) : Action()
    data class ChangeAge(val age: Int) : Action()
}

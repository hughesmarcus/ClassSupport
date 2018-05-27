package com.supporter.marcus.classsupport.ui

/**
 * Abstract State
 */
open class State


object LoadingState : State()

object LoadingMoreState : State()

object EmptyListState : State()

/**
 * Generic Error state
 * @param error - caught error
 */
data class ErrorState(val error: Throwable) : State()
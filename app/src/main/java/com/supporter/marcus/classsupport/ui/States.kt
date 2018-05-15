package com.supporter.marcus.classsupport.ui

/**
 * Abstract State
 */
open class State

/**
 * Generic Loading State
 */
object LoadingState : State()

/**
 * Generic Error state
 * @param error - caught error
 */
data class ErrorState(val error: Throwable) : State()
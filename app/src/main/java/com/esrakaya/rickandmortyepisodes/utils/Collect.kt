package com.esrakaya.rickandmortyepisodes.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

fun <T> Fragment.collectState(stateFlow: StateFlow<T>, action: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        stateFlow.collect {
            action(it)
        }
    }
}

fun <T> Fragment.collectEvent(sharedFlow: SharedFlow<T>, action: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        sharedFlow.collect {
            action(it)
        }
    }
}
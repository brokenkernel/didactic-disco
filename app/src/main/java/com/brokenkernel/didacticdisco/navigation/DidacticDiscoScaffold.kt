package com.brokenkernel.didacticdisco.navigation

import android.os.Parcelable
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.debug.impl.debugScreenEntryBuilder
import com.brokenkernel.didacticdisco.homescreen.api.HomeScreenNavgiationKey
import com.brokenkernel.didacticdisco.homescreen.impl.homeScreenEntryBuilder
import com.brokenkernel.didacticdisco.spectogram.api.SpectogramNavgiationKey
import com.brokenkernel.didacticdisco.spectogram.impl.spectogramEntryBuilder

@Composable
private fun <T : Parcelable> rememberParcelableBackStack(vararg elements: T): SnapshotStateList<T> =
    rememberSaveable {
        mutableStateListOf(*elements)
    }

@Composable
public fun DidacticDiscoScaffold(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        val backStack =
            rememberParcelableBackStack<DidacticDiscoRoute>(
                HomeScreenNavgiationKey,
            )
        SharedTransitionLayout {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                entryProvider =
                    entryProvider {
                        homeScreenEntryBuilder(
                            onGoToSpectogram = {
                                backStack.add(SpectogramNavgiationKey)
                            },
                        )
                        spectogramEntryBuilder()
                        debugScreenEntryBuilder(
                            onGoTo = {
                                backStack.add(it)
                            },
                        )
                    },
                sharedTransitionScope = this,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

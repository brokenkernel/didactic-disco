package com.brokenkernel.didacticdisco.navigation

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.brokenkernel.didacticdisco.coreinfra.BackStack
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.coreinfra.TAG
import com.brokenkernel.didacticdisco.homescreen.api.HomeScreenNavgiationKey

@Composable
private fun <T : Parcelable> rememberParcelableBackStack(vararg elements: T): SnapshotStateList<T> =
    rememberSaveable {
        mutableStateListOf(*elements)
    }

public class DidacticDiscoNavigator internal constructor(
    backStack: BackStack,
) {
    // Needs to be public because NavDisplay directly observes it.
    public val backstack: BackStack = backStack

    public fun goTo(destination: DidacticDiscoRoute) {
        backstack.add(destination)
    }

    public fun goBack() {
        backstack.removeLastOrNull()
    }

    public fun current(): DidacticDiscoRoute = backstack.last()

    internal object Factory {
        @Composable
        internal fun create(): DidacticDiscoNavigator {
            val backstack: BackStack =
                rememberParcelableBackStack(
                    HomeScreenNavgiationKey,
                )
            LaunchedEffect(backstack.toList()) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, """Back stack: ${backstack.joinToString(" -> ")}""")
                }
            }

            return DidacticDiscoNavigator(backstack)
        }
    }
}

package com.brokenkernel.didacticdisco

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.brokenkernel.didacticdisco.homescreen.api.HomeScreenNavgiationKey
import com.brokenkernel.didacticdisco.homescreen.impl.homeScreenEntryBuilder
import com.brokenkernel.didacticdisco.spectogram.api.SpectogramNavgiationKey
import com.brokenkernel.didacticdisco.spectogram.impl.spectogramEntryBuilder
import com.brokenkernel.didacticdisco.ui.theme.DidacticDiscoTheme

@Composable
private fun <T : Parcelable> rememberParcelableBackStack(vararg elements: T): SnapshotStateList<T> =
    rememberSaveable {
        mutableStateListOf(*elements)
    }

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DidacticDiscoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                                        // TODO: figure out better way to handle general navigation goto class
                                        onGoToSpectogram = {
                                            backStack.add(SpectogramNavgiationKey)
                                        },
                                    )
                                    spectogramEntryBuilder()
                                },
                            sharedTransitionScope = this,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}

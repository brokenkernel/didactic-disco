package com.brokenkernel.didacticdisco

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.didacticdisco.ui.theme.DidacticDiscoTheme
import kotlinx.parcelize.Parcelize

private sealed interface Route : Parcelable

@Parcelize
private data object RouteA : Route

@Parcelize
private data class RouteB(
    val id: String,
) : Route

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
                    val backStack = rememberParcelableBackStack<Route>(RouteA)
                    SharedTransitionLayout {
                        NavDisplay(
                            backStack = backStack,
                            onBack = { backStack.removeLastOrNull() },
                            entryProvider =
                                entryProvider {
                                    entry<RouteA> {
                                        Column {
                                            Greeting(
                                                name = "Route a",
                                            )
                                            Button(
                                                onClick =
                                                    dropUnlessResumed {
                                                        backStack.add(RouteB("123"))
                                                    },
                                            ) {
                                                Text("Click to navigate")
                                            }
                                        }
                                    }
                                    entry<RouteB> { key ->
                                        Greeting(
                                            name = "Route B $key",
                                        )
                                    }
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

@Composable
internal fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    DidacticDiscoTheme {
        Greeting("Android")
    }
}

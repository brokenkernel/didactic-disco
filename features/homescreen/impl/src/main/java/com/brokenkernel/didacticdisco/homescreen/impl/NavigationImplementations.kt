package com.brokenkernel.didacticdisco.homescreen.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.homescreen.api.HomeScreenNavgiationKey

public fun EntryProviderScope<DidacticDiscoRoute>.homeScreenEntryBuilder(onGoToSpectogram: () -> Unit) {
    entry<HomeScreenNavgiationKey> {
        Column {
            Text("You Found the Homescreen Impl")
            Button(
                onClick =
                    dropUnlessResumed {
                        onGoToSpectogram()
                    },
            ) {
                Text("Go To Spectogram Screen")
            }
        }
    }
}

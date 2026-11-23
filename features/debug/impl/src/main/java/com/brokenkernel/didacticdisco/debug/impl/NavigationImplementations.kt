package com.brokenkernel.didacticdisco.debug.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.debug.api.DebugScreenNavgiationKey
import com.brokenkernel.didacticdisco.spectogram.api.SpectogramScreenNavgiationKey

public fun EntryProviderScope<DidacticDiscoRoute>.debugScreenEntryBuilder(onGoTo: (DidacticDiscoRoute) -> Unit) {
    entry<DebugScreenNavgiationKey> {
        Column {
            Text("You Found the Debug Impl")
            Button(
                onClick =
                    dropUnlessResumed {
                        onGoTo(SpectogramScreenNavgiationKey)
                    },
            ) {
                Text("Go To Spectogram Screen")
            }
        }
    }
}

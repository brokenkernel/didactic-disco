package com.brokenkernel.didacticdisco.spectogram.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.spectogram.api.SpectogramNavgiationKey

public fun EntryProviderScope<DidacticDiscoRoute>.spectogramEntryBuilder() {
    entry<SpectogramNavgiationKey> {
        Column {
            Text("You Found the Spectogram Impl")
        }
    }
}

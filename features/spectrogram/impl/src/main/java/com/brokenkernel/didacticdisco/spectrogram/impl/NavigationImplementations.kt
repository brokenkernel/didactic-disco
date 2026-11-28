package com.brokenkernel.didacticdisco.spectrogram.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.spectrogram.api.SpectrogramScreenNavgiationKey

public fun EntryProviderScope<DidacticDiscoRoute>.spectrogramEntryBuilder() {
    entry<SpectrogramScreenNavgiationKey> {
        Column {
            Text("You Found the Spectrogram Impl")
        }
    }
}

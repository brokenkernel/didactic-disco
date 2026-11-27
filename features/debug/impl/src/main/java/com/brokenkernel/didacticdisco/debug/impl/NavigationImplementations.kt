package com.brokenkernel.didacticdisco.debug.impl

import androidx.compose.foundation.layout.Column
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.debug.api.DebugScreenNavgiationKey
import com.brokenkernel.didacticdisco.debug.impl.presentation.view.DetailedDebugTab

public fun EntryProviderScope<DidacticDiscoRoute>.debugScreenEntryBuilder(onGoTo: (DidacticDiscoRoute) -> Unit) {
    entry<DebugScreenNavgiationKey> {
        Column {
            DetailedDebugTab()
        }
    }
}

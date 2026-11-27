package com.brokenkernel.didacticdisco.debug.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.debug.api.DebugScreenNavgiationKey
import com.brokenkernel.didacticdisco.debug.impl.presentation.view.DetailedDebugTab

public fun EntryProviderScope<DidacticDiscoRoute>.debugScreenEntryBuilder(
    onGoTo: (DidacticDiscoRoute) -> Unit,
    snackbarHostState: SnackbarHostState,
    repositoryURL: String,
) {
    entry<DebugScreenNavgiationKey> {
        Column {
            DetailedDebugTab(
                repositoryURL = repositoryURL,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}

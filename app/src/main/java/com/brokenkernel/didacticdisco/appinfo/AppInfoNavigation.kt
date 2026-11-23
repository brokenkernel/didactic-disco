package com.brokenkernel.didacticdisco.appinfo

import androidx.navigation3.runtime.EntryProviderScope
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import kotlinx.parcelize.Parcelize

@Parcelize
internal object LibrariesScreenNavgiationKey : DidacticDiscoRoute

internal fun EntryProviderScope<DidacticDiscoRoute>.appInfoScreensEntryBuilder() {
    entry<LibrariesScreenNavgiationKey> {
        LibrariesTab()
    }
}

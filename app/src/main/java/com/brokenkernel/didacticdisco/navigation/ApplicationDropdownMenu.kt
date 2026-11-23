package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.navigation.NavigableScreens

@Composable
internal fun ApplicationDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    goTo: (DidacticDiscoRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        val allDestinations: List<NavigableScreens> =
            NavigableScreens
                .entries
                .filter { it.additionalMenuNavigationWeight != null }
                .sortedBy { it.additionalMenuNavigationWeight }

        Column {
            allDestinations.forEach {
                DropdownMenuItem(
                    text = { Text(stringResource(it.titleResource)) },
                    onClick = {
                        goTo(it.matchingRoute)
                        onDismissRequest()
                    },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = it.icon(),
//                            contentDescription = stringResource(it.contentDescription),
//                        )
//                    },
                )
            }
        }
    }
}

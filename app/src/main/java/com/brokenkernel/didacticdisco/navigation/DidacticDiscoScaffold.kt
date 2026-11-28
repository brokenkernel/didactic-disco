package com.brokenkernel.didacticdisco.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.runtime.result.rememberResultEventBus
import androidx.navigation3.runtime.result.rememberResultEventBusNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.brokenkernel.didacticdisco.R
import com.brokenkernel.didacticdisco.appinfo.appInfoScreensEntryBuilder
import com.brokenkernel.didacticdisco.components.SimpleIconButton
import com.brokenkernel.didacticdisco.debug.impl.debugScreenEntryBuilder
import com.brokenkernel.didacticdisco.homescreen.impl.homeScreenEntryBuilder
import com.brokenkernel.didacticdisco.spectrogram.impl.spectrogramEntryBuilder
import com.brokenkernel.improvtools.application.presentation.view.ApplicationDropdownMenu

@Composable
internal fun DidacticDiscoScaffold(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }

    val navigator = DidacticDiscoNavigator.Factory.create()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                title = {
                    // TODO
                },
                actions = {
                    var isDropdownMenuExpanded: Boolean by remember { mutableStateOf(false) }
                    SimpleIconButton(
                        onClick = { isDropdownMenuExpanded = !isDropdownMenuExpanded },
                        icon =
                            ImageVector.vectorResource(
                                R.drawable.more_vert_24dp_1f1f1f_fill0_wght400_grad0_opsz24,
                            ),
                        contentDescription = stringResource(R.string.navigation_open_screen_specific_menu),
                    )
                    ApplicationDropdownMenu(
                        isDropdownMenuExpanded,
                        onDismissRequest = { isDropdownMenuExpanded = false },
                        goTo = navigator::goTo,
                    )
                },
            )
        },
    ) { innerPadding ->

        val allDestinations: List<NavigableScreens> =
            NavigableScreens
                .entries
                .filter { it.navigationBarNavigationWeight != null }
                .sortedBy { it.navigationBarNavigationWeight }

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                allDestinations.forEach {
                    item(
                        selected = it.matchingRoute == navigator.current(),
                        onClick = {
                            navigator.goTo(it.matchingRoute)
                        },
                        icon = {
//                            Icon(
//                                imageVector = it.icon(),
//                                contentDescription = stringResource(it.contentDescription),
//                            )
                        },
                        label = { Text(stringResource(it.titleResource)) },
                    )
                }
            },
        ) {
            SharedTransitionLayout {
                val resultEventBus = rememberResultEventBus()
                NavDisplay(
                    backStack = navigator.backstack,
                    onBack = { navigator.goBack() },
                    entryDecorators =
                        listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                            rememberResultEventBusNavEntryDecorator(resultEventBus = resultEventBus),
                        ),
                    entryProvider =
                        entryProvider {
                            homeScreenEntryBuilder(
                                onGoTo = navigator::goTo,
                            )
                            spectrogramEntryBuilder()
                            debugScreenEntryBuilder(
                                onGoTo = navigator::goTo,
                                snackbarHostState = snackbarHostState,
                                repositoryURL = "https://github.com/brokenkernel/didactic-disco",
                            )
                            appInfoScreensEntryBuilder()
                        },
                    sharedTransitionScope = this,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}

package com.brokenkernel.didacticdisco.navigation

import androidx.annotation.StringRes
import com.brokenkernel.didacticdisco.R
import com.brokenkernel.didacticdisco.coreinfra.DidacticDiscoRoute
import com.brokenkernel.didacticdisco.debug.api.DebugScreenNavgiationKey
import com.brokenkernel.didacticdisco.homescreen.api.HomeScreenNavgiationKey

internal enum class NavigableScreens(
    @param:StringRes @field:StringRes internal val titleResource: Int,
    @param:StringRes @field:StringRes internal val contentDescription: Int,
//    @param:DrawableRes @field:DrawableRes private val iconDrawable: Int,
    /**
     * Low is first. High is last. null isn't shown.
     */
    val navigationBarNavigationWeight: Int?,
    /**
     * Low is first. High is last. null isn't shown.
     */
    val additionalMenuNavigationWeight: Int?,
    internal val matchingRoute: DidacticDiscoRoute,
) {
    HomeScreen(
        titleResource = R.string.navigation_screen_home,
        contentDescription = R.string.navigation_screen_home_screen,
        navigationBarNavigationWeight = 100,
        additionalMenuNavigationWeight = null,
        matchingRoute = HomeScreenNavgiationKey,
    ),
    DebugScreen(
        titleResource = R.string.navigation_screen_debug,
        contentDescription = R.string.navigation_screen_debug_screen,
        navigationBarNavigationWeight = 100,
        additionalMenuNavigationWeight = 100,
        matchingRoute = DebugScreenNavgiationKey,
    ),

//    @Composable
//    internal fun icon(): ImageVector {
//        return ImageVector.vectorResource(iconDrawable)
//    }
}

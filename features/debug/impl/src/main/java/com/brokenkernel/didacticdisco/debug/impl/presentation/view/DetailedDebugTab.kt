package com.brokenkernel.didacticdisco.debug.impl.presentation.view

import android.app.NotificationManager
import android.content.ClipData
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.PackageInfoFlags
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import com.brokenkernel.didacticdisco.components.ExpandIcon
import com.brokenkernel.didacticdisco.debug.impl.R
import kotlinx.coroutines.launch

@Composable
internal fun DetailedDebugTab(modifier: Modifier = Modifier) {
    val crScope = rememberCoroutineScope()
    val resources = LocalResources.current
    val packageManager = LocalContext.current.packageManager
    val context = LocalContext.current
    val stringDebugData = stringResource(R.string.about_debugging_data)
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val packageInfo: PackageInfo? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(context.packageName, PackageInfoFlags.of(0))
        } else {
            null
        }
    val versionName: String? = packageInfo?.versionName
    val packageName = packageInfo?.packageName
    val longVersionCode: Long? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo?.longVersionCode
        } else {
            null
        }
    val localConfig = LocalConfiguration.current

    val clipboard: Clipboard = LocalClipboard.current

    fun generateGeneralInformationText(): String {
        val result: String =
            """
        |<big>${resources.getString(R.string.about_contact_info)}</big>
        |🌐 <u><a href="https://github.com/brokenkernel/didactic-disco">${
                resources.getString(
                    R.string.about_contact_code_repo,
                )
            }</a></u>
            """.trimMargin().replace("\n", "<br/>")
        return result
    }

    fun generateDebugInformationText(): String {
        val basicResult: String =
            """
            |<big>${resources.getString(R.string.about_version_information)}</big>
            |${resources.getString(R.string.about_long_version_code, longVersionCode)}
            |${resources.getString(R.string.about_package_name, packageName)}
            |${resources.getString(R.string.about_version, versionName ?: "(null)")}
            |<big>${resources.getString(R.string.about_debug_information)}</big>
            |${resources.getString(R.string.about_is_safe_mode, packageManager.isSafeMode)}
            |
            |<big>Build Constants</big>
            |<b>BOARD=</b>${Build.BOARD}
            |<b>BOOTLOADER=</b>${Build.BOOTLOADER}
            |<b>BRAND=</b>${Build.BRAND}
            |<b>DEVICE=</b>${Build.DEVICE}
            |<b>DISPLAY=</b>${Build.DISPLAY}
            |<b>FINGERPRINT=</b>${Build.FINGERPRINT}
            |<b>HARDWARE=</b>${Build.HARDWARE}
            |<b>HOST=</b>${Build.HOST}
            |<b>ID=</b>${Build.ID}
            |<b>MANUFACTURER=</b>${Build.MANUFACTURER}
            |<b>MODEL=</b>${Build.MODEL}
            |<b>PRODUCT=</b>${Build.PRODUCT}
            |SUPPORTED_<b>ABIS=</b>${Build.SUPPORTED_ABIS.contentToString()}
            |<b>TAGS=</b>${Build.TAGS}
            |<b>TIME=</b>${Build.TIME}
            |<b>TYPE=</b>${Build.TYPE}
            |<b>USER=</b>${Build.USER}
            |
            |<big>LocalConfig</big>
            |<b>Locales=</b>${localConfig.locales}
            |<b>NavigationType=</b>${localConfig.navigation}""" +
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    """|<b>ColorMode=</b>${localConfig.colorMode}"""
                } else {
                    ""
                } +
                """|<b>DensityDPI=</b>${localConfig.densityDpi}
            |<b>NavigationHidden=</b>${localConfig.navigationHidden}
            |<b>Orientation=</b>${localConfig.orientation}
            |<b>ScreenLayout=</b>${localConfig.screenLayout}""" +
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    """|<b>ScreenHDR=</b>${localConfig.isScreenHdr}
                        |<b>WideColorGamut=</b>${localConfig.isScreenWideColorGamut}"""
                } else {
                    ""
                } +
                """|<b>ScreenRound=</b>${localConfig.isScreenRound}
            |<b>uiMode=</b>${localConfig.uiMode}
            |<b>Touchscreen=</b>${localConfig.touchscreen}
            |<b>smallestScreenWidthDp=</b>${localConfig.smallestScreenWidthDp}
            |<b>layoutDirection=</b>${localConfig.layoutDirection}
            |<b>layoutDirection=</b>${localConfig.layoutDirection}
            |<br>
                """.trimMargin().replace("\n", "<br/>")

        val bubblesAllowed =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BAKLAVA) {
                """<b>Bubbles Allowed:</b> ${notificationManager.canPostPromotedNotifications()}"""
            } else {
                ""
            }
        // Technically this skips some information for 33,34, but not that important.
        val additionalDataForSDK35Plus =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                """
                |<br><big>API 36 Build</big>
                |<b>ODM_SKU=</b>${Build.ODM_SKU}
                |<b>SKU=</b>${Build.SKU}
                |<b>SOC_MANUFACTURER=</b>${Build.SOC_MANUFACTURER}
                |<b>SOC_MODEL=</b>${Build.SOC_MODEL}
                |
                |<br><big>Additional LocalConfig</big>
                |<b>NightMode=</b>${localConfig.isNightModeActive}
                |<b>grammaticalGender=</b>${localConfig.grammaticalGender}
                |<br>
                |<big>Notification Debug</big>
                |<b>Notifications Are Paused:</b> ${notificationManager.areNotificationsPaused()}
                |<b>Notifications Are Enabled:</b> ${notificationManager.areNotificationsEnabled()}
                |<b>Bubble Preference:</b> ${notificationManager.bubblePreference}
                |$bubblesAllowed
                |<b>Importance:</b> ${notificationManager.importance}
                |<b>Policy Access Granted:</b> ${notificationManager.isNotificationPolicyAccessGranted}
                |<b>Full Screen:</b> ${notificationManager.canUseFullScreenIntent()}
                |
                """.trimMargin().replace("\n", "<br/>")
            } else {
                ""
            }

        val result = basicResult + additionalDataForSDK35Plus

        // further debug information for the future
//    Build.getFingerprintedPartitions()
//    Build.getRadioVersion("versionName")
//    Build.getMajorSdkVersion()
        return result
    }

    fun copyAboutText() {
        val wordString = AnnotatedString.fromHtml(generateDebugInformationText())
        val clipData =
            ClipData.newHtmlText(
                stringDebugData,
                wordString.text,
                wordString.toString(),
            )
        val clipEntry = clipData.toClipEntry()

        crScope.launch {
            clipboard.setClipEntry(clipEntry)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .weight(100f),
        ) {
            SelectionContainer {
                Column {
                    Text(
                        AnnotatedString.fromHtml(generateGeneralInformationText()),
                    )
//                    HtmlText(
//                        resources.getString(
//                            R.string.about_buildconfig_version_code,
//                            BuildConfig.VERSION_CODE,
//                        ),
//                    )
                    var isDebugDataExpanded by rememberSaveable { mutableStateOf(false) }
                    // TODO: make utility collapsible card
                    Card(
                        modifier =
                            Modifier
                                .clickable(
                                    onClick = { isDebugDataExpanded = !isDebugDataExpanded },
                                    onClickLabel = stringResource(R.string.component_collapse_card),
                                    enabled = true,
                                    role = Role.Switch,
                                ),
                    ) {
                        Row {
                            if (isDebugDataExpanded) {
                                Text(
                                    AnnotatedString.fromHtml(generateDebugInformationText()),
                                )
                            } else {
                                Text(
                                    stringResource(R.string.about_show_debug_data),
                                )
                                // not shown when expanded, but that's okay.
                                ExpandIcon(isDebugDataExpanded)
                            }
                        }
                    }
//                    TextButton(
//                        onClick = {
//                            onGoToPrivacyTab()
//                        },
//                    ) {
//                        Text(stringResource(R.string.about_show_privacy_screen))
//                    }
//                    TextButton(
//                        onClick = {
//                            onGoToLibrariesTab()
//                        },
//                    ) {
//                        Text("Show Libraries Screen")
//                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
        ) {
//            val launcher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.StartActivityForResult(),
//                onResult = {},
//            )
            FilledTonalButton(
                onClick = {
                    copyAboutText()
                },
            ) {
                Text(stringResource(R.string.about_copy_all))
            }
//            ElevatedButton(
//                onClick = {
//                    val textToBeEmailed = generateDebugInformationText()
//
//                    val intent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
//                        .putExtra(
//                            Intent.EXTRA_EMAIL,
//                            arrayOf(resources.getString(R.string.about_contact_email_address)),
//                        )
//                        .putExtra(
//                            Intent.EXTRA_SUBJECT,
//                            resources.getString(R.string.about_improvtools_feature_request),
//                        )
//                        .putExtra(
//                            Intent.EXTRA_TEXT,
//                            Html.fromHtml(textToBeEmailed, FROM_HTML_MODE_COMPACT),
//                        )
//                    try {
//                        launcher.launch(intent)
//                    } catch (_: ActivityNotFoundException) {
//                        crScope.launch {
//                            snackbarHostState.showSnackbar(
//                                resources.getString(R.string.error_no_email_application_available),
//                                withDismissAction = true,
//                                duration = SnackbarDuration.Short,
//                            )
//                        }
//                    }
//                },
//            ) {
//                Text(stringResource(R.string.about_send_email))
//            }
        }
    }
}

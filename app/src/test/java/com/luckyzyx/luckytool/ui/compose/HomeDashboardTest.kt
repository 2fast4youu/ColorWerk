package at.gregor.colorwerk.ui.compose

import android.app.Application
import android.os.Bundle
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.pages.HomeDashboard
import at.gregor.colorwerk.ui.compose.pages.HomeDashboardState
import at.gregor.colorwerk.ui.shell.FloatingBottomBarStyle
import at.gregor.colorwerk.ui.shell.LocalEnableFloatingBottomBar
import at.gregor.colorwerk.ui.shell.LocalFloatingBottomBarStyle
import at.gregor.colorwerk.ui.theme.AppSettings
import at.gregor.colorwerk.ui.theme.ColorMode
import at.gregor.colorwerk.ui.theme.LocalUiMode
import at.gregor.colorwerk.ui.theme.AppTheme
import at.gregor.colorwerk.ui.theme.UiMode
import at.gregor.colorwerk.utils.DeviceInfoEntry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], application = Application::class, qualifiers = "de-rDE-w411dp-h880dp-xhdpi")
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class HomeDashboardTest {

    companion object {
        private const val model = "realme RMX5200 真我 GT8 Pro"
        private const val rootLine = "Root-Anbieter: 3.3.1 (32680)"
        private const val frameworkLine = "Framework-Version: LSPosed v2.2.1 (7912)"
        private val sample = HomeDashboardState(
            moduleActive = true, moduleVersion = "1.3.5_beta (22848)",
            rootVersion = "Root 来源：3.3.1 (32680)", frameworkVersion = "框架版本：LSPosed v2.2.1 (7912)",
            showNotice = true,
            deviceInfo = listOf(
                DeviceInfoEntry(R.string.model, model),
                DeviceInfoEntry(R.string.system, "Android 17 (37) · OS V17.0.0 (40)"),
                DeviceInfoEntry(R.string.version, "RMX5200_11.C.27_1270_202610051850"),
                DeviceInfoEntry(R.string.product, "RMX5200 RE6030L1 SM8850 24624 B"),
                DeviceInfoEntry(R.string.build_version, "RMX5200_17.0.0.106(CN01) 97 b45da55c"),
                DeviceInfoEntry(R.string.flash, "SKhynix HN8T274EJKX130 A401"),
                DeviceInfoEntry(R.string.home_device_pos, "SAMPLE-PCB-000001"),
                DeviceInfoEntry(R.string.home_device_sas, "SAMPLE-SERIAL-000001 SAMPLE-CHIP-000001"),
            ),
        )
    }

    @Test
    fun `both themes present device values as body text in light and dark appearances`() {
        for (mode in UiMode.entries) {
            for (color in listOf(ColorMode.LIGHT, ColorMode.DARK)) {
                HomeHost(mode, color).use { h ->
                    assertNotNull(h.ui.textNode("Modul aktiviert"))
                    assertNull(h.ui.textNode("RMX5200_17.0.0.106(CN01) 97 b45da55c"))
                    h.scrollTo(model)
                    val body = h.layout(model)
                    assertEquals(FontWeight.Normal, body.layoutInput.style.fontWeight)
                    h.ui.snapshot("home-${mode.name.lowercase()}-${color.name.lowercase()}")
                    assertFalse("$mode ${body.size} paragraph=${body.multiParagraph.width}x${body.multiParagraph.height} lines=${body.lineCount}", body.hasVisualOverflow)
                    h.scrollTo("Mehr Geräteinformationen")
                    h.ui.clickText("Mehr Geräteinformationen")
                    h.ui.advance()
                    h.scrollTo("RMX5200_17.0.0.106(CN01) 97 b45da55c")
                    assertEquals(FontWeight.Normal, h.layout("RMX5200_17.0.0.106(CN01) 97 b45da55c").layoutInput.style.fontWeight)
                    h.ui.snapshot("home-${mode.name.lowercase()}-${color.name.lowercase()}-details")
                }
            }
        }
    }

    @Test
    fun `home actions remain available and expansion survives theme changes and recreation`() {
        val saved: Bundle
        HomeHost(UiMode.Coui, ColorMode.LIGHT, update = true).use { h ->
            h.ui.clickLabel(h.ui.activity.getString(R.string.menu_reboot))
            h.ui.clickLabel(h.ui.activity.getString(R.string.about_title))
            assertEquals(1, h.restart)
            assertEquals(1, h.about)
            h.longClick("Modul aktiviert")
            assertEquals(1, h.status)
            val updateHint = h.ui.activity.getString(R.string.check_update_hint)
            h.scrollTo(updateHint)
            h.ui.clickText(updateHint)
            assertEquals(1, h.update)
            h.scrollTo(model)
            h.longClick(model)
            assertEquals(1, h.copy)
            h.scrollTo("Mehr Geräteinformationen")
            h.ui.clickText("Mehr Geräteinformationen")
            h.ui.advance()
            h.scrollTo(frameworkLine)
            assertNotNull(h.ui.textNode(rootLine))
            h.mode = UiMode.Material
            h.ui.advance()
            h.scrollTo("Weniger Geräteinformationen")
            assertNotNull(h.ui.textNode("Weniger Geräteinformationen"))
            h.ui.clickText("Weniger Geräteinformationen")
            h.ui.advance()
            h.scrollTo("Entwicklung unterstützen")
            h.ui.clickText("Entwicklung unterstützen")
            h.longClick("Entwicklung unterstützen")
            assertEquals(1, h.donate)
            assertEquals(1, h.donateMethods)
            h.scrollTo("Mehr Geräteinformationen")
            h.ui.clickText("Mehr Geräteinformationen")
            h.ui.advance()
            saved = h.ui.saveAndClose()
        }
        HomeHost(UiMode.Material, ColorMode.LIGHT, saved = saved, update = true).use { h ->
            h.scrollTo("Weniger Geräteinformationen")
            assertNotNull(h.ui.textNode("Weniger Geräteinformationen"))
        }
    }

    @Test
    fun `inactive and loading states remain readable with large text on narrow RTL screens`() {
        for (mode in UiMode.entries) {
            HomeHost(mode, ColorMode.DARK, width = 720, fontScale = 1.4f, rtl = true).use { h ->
                h.data = h.data.copy(moduleActive = false, deviceInfo = null, rootVersion = null, frameworkVersion = null)
                h.ui.advance()
                assertNotNull(h.ui.textNode(h.ui.activity.getString(R.string.module_is_disabled)))
                val loadingHint = h.ui.activity.getString(R.string.home_info_loading)
                h.scrollTo(loadingHint)
                assertNotNull(h.ui.textNode(loadingHint))
                assertNull(h.ui.textNode("Mehr Geräteinformationen"))
                h.data = sample.copy(moduleActive = false)
                h.ui.advance()
                h.scrollTo(model)
                assertFalse(h.layout(model).hasVisualOverflow)
                val bounds = h.ui.textNode(model)!!.boundsInRoot
                assertTrue(bounds.left >= 0 && bounds.right <= 720)
                h.ui.snapshot("home-${mode.name.lowercase()}-large-rtl")
                h.scrollTo("Entwicklung unterstützen")
                h.ui.clickText("Entwicklung unterstützen")
                assertEquals(1, h.donate)
            }
        }
    }

    @Test
    fun `Coui active status stays readable with large text and dynamic colors`() {
        for (color in listOf(ColorMode.LIGHT, ColorMode.DARK, ColorMode.MONET_LIGHT, ColorMode.MONET_DARK)) {
            HomeHost(UiMode.Coui, color, width = 720, fontScale = 1.4f, rtl = true).use { h ->
                val version = "${h.ui.activity.getString(R.string.version)}: 1.3.5_beta (22848)"
                val framework = "${h.ui.activity.getString(R.string.framework_version)} LSPosed v2.2.1 (7912)"
                assertFalse(h.layout(version).hasVisualOverflow)
                assertFalse(h.layout(framework).hasVisualOverflow)
                assertTrue(h.ui.textNode(version)!!.boundsInRoot.bottom <= h.ui.textNode(framework)!!.boundsInRoot.top)
                h.longClick("Modul aktiviert")
                assertEquals(1, h.status)
                h.ui.snapshot("home-status-${color.name.lowercase()}-large-rtl")
            }
        }
    }

    private class HomeHost(
        mode: UiMode,
        color: ColorMode,
        saved: Bundle? = null,
        width: Int = 822,
        fontScale: Float = 1f,
        rtl: Boolean = false,
        update: Boolean = false,
    ) : AutoCloseable {
        val ui = ComposeGestureHost(saved, width)
        var mode by mutableStateOf(mode)
        var data by mutableStateOf(sample.copy(updateVersion = if (update) "1.3.6 (22900)" else null))
        var restart = 0
        var about = 0
        var status = 0
        var update = 0
        var copy = 0
        var donate = 0
        var donateMethods = 0

        init {
            ui.setContent {
                CompositionLocalProvider(
                    LocalDensity provides Density(2f, fontScale),
                    LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr,
                    LocalUiMode provides this.mode,
                    LocalEnableFloatingBottomBar provides (this.mode == UiMode.Material),
                    LocalFloatingBottomBarStyle provides FloatingBottomBarStyle.IosLike,
                ) {
                    AppTheme(AppSettings(colorMode = color, dynamicColor = false), uiMode = this.mode) {
                        val pager = rememberMainPagerState(rememberPagerState(initialPage = 2) { 5 })
                        MainTabs(pager, isActive = true) { tab, _ ->
                            if (tab == MainTab.Home) {
                                HomeDashboard(
                                    state = data,
                                    onRestart = { restart++ }, onAbout = { about++ }, onUpdate = { this.update++ },
                                    onStatusLongClick = { status++ }, onCopyDeviceInfo = { copy++ },
                                    onDonate = { donate++ }, onDonateMethods = { donateMethods++ }, onOpenNotice = {},
                                )
                            } else Text(tab.name)
                        }
                    }
                }
            }
            ui.advance()
        }

        fun scrollTo(text: String) {
            repeat(15) {
                val node = ui.textNode(text)
                if (node != null && node.boundsInRoot.top in 180f..1300f) return
                val up = node != null && node.boundsInRoot.top < 180f
                ui.drag(360f, if (up) 500f else 1400f, 360f, if (up) 1250f else 500f)
            }
            check(ui.textNode(text) != null) { "Missing $text" }
        }

        fun layout(text: String): TextLayoutResult {
            val results = mutableListOf<TextLayoutResult>()
            check(ui.textNode(text)!!.config.getOrNull(SemanticsActions.GetTextLayoutResult)!!.action!!.invoke(results))
            return results.single()
        }

        fun longClick(text: String) {
            var node = requireNotNull(ui.textNode(text))
            while (node.config.getOrNull(SemanticsActions.OnLongClick)?.action == null) {
                node = requireNotNull(node.parent)
            }
            check(node.config.getOrNull(SemanticsActions.OnLongClick)!!.action!!.invoke())
        }

        override fun close() = ui.close()
    }
}

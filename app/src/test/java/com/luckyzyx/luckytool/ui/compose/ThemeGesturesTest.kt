package at.gregor.colorwerk.ui.compose

import android.app.Application
import android.os.Bundle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import at.gregor.colorwerk.ui.compose.pages.ThemeScreen
import at.gregor.colorwerk.ui.shell.FloatingBottomBarStyle
import at.gregor.colorwerk.ui.shell.LocalFloatingBottomBarStyle
import at.gregor.colorwerk.ui.shell.LocalEnableBlur
import at.gregor.colorwerk.ui.shell.LocalEnableSwipeDismiss
import at.gregor.colorwerk.ui.shell.LocalPagerInterceptionMode
import at.gregor.colorwerk.ui.shell.PagerModeCrossAxis
import at.gregor.colorwerk.ui.shell.ShellSettingsController
import at.gregor.colorwerk.ui.theme.ColorMode
import at.gregor.colorwerk.ui.theme.AppTheme
import at.gregor.colorwerk.ui.theme.ThemeController
import at.gregor.colorwerk.ui.theme.UiMode
import at.gregor.colorwerk.utils.SettingsPrefs
import at.gregor.colorwerk.utils.appPrefs
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
class ThemeGesturesTest {
    @Test
    // 需要「低于预测性返回 API」的系统环境，返回手势才以开关呈现而不是系统提示。
    @Config(sdk = [32])
    fun `both appearances expose working gesture preferences below the predictive back API level`() {
        for (mode in UiMode.entries) {
            for (color in listOf(ColorMode.LIGHT, ColorMode.DARK)) {
                ThemeHost(mode, color).use { h ->
                    assertTrue(h.swipe)
                    assertEquals(PagerModeCrossAxis, h.pagerMode)
                    h.scrollTo("Zurück per Wischgeste")
                    assertNull(h.ui.textNode("Vorausschauende Zurück-Geste"))
                    h.ui.clickText("Zurück per Wischgeste")
                    h.ui.advance()
                    assertFalse(h.swipe)
                    assertFalse(ShellSettingsController.get(h.ui.activity).swipeDismiss)
                    h.scrollTo("Wischgesten für Seiten")
                    h.ui.snapshot("theme-${mode.name.lowercase()}-${color.name.lowercase()}")
                    for ((index, label) in listOf(2 to "Wie bei iOS", 0 to "Ausfall", 1 to "Achsenübergreifend")) {
                        h.ui.clickText("Wischgesten für Seiten")
                        h.ui.advance()
                        h.ui.snapshot("theme-${mode.name.lowercase()}-${color.name.lowercase()}-menu")
                        h.ui.clickText(label)
                        h.ui.advance()
                        assertEquals(index, h.pagerMode)
                        assertEquals(index, ShellSettingsController.get(h.ui.activity).pagerInterceptionMode)
                    }
                    assertFalse("These preferences must not recreate the activity", h.ui.activity.isChangingConfigurations)
                }
            }
        }
    }

    @Test
    @Config(sdk = [35])
    fun `supported Android shows predictive back before swipe and pager mode`() {
        for (mode in UiMode.entries) {
            ThemeHost(mode, ColorMode.LIGHT).use { h ->
                h.scrollTo("Vorausschauende Zurück-Geste")
                assertNotNull(h.ui.textNode("Zurück per Wischgeste"))
                assertNotNull(h.ui.textNode("Wischgesten für Seiten"))
                val predictiveY = h.ui.textNode("Vorausschauende Zurück-Geste")!!.boundsInRoot.top
                val swipeY = h.ui.textNode("Zurück per Wischgeste")!!.boundsInRoot.top
                val pagerY = h.ui.textNode("Wischgesten für Seiten")!!.boundsInRoot.top
                assertTrue(predictiveY < swipeY && swipeY < pagerY)
            }
        }
    }

    @Test
    fun `gesture choices persist through activity recreation`() {
        for (mode in listOf(UiMode.Material)) {
            val saved: Bundle
            ThemeHost(mode, ColorMode.LIGHT).use { h ->
                h.scrollTo("Zurück per Wischgeste")
                h.ui.clickText("Zurück per Wischgeste")
                h.ui.advance()
                h.scrollTo("Wischgesten für Seiten")
                h.ui.clickText("Wischgesten für Seiten")
                h.ui.advance()
                h.ui.clickText("Wie bei iOS")
                h.ui.advance()
                saved = h.ui.saveAndClose()
            }
            ThemeHost(mode, ColorMode.LIGHT, saved).use { h ->
                assertFalse(h.swipe)
                assertEquals(2, h.pagerMode)
                h.scrollTo("Wischgesten für Seiten")
                assertNotNull(h.ui.textNode("Wie bei iOS"))
            }
        }
    }

    @Test
    fun `floating styles are revealed by the switch and persist with independent global blur`() {
        val saved: Bundle
        ThemeHost(UiMode.Material, ColorMode.LIGHT).use { h ->
            h.scrollTo("Schwebende Navigationsleiste")
            assertNull(h.ui.textNode("Stil der schwebenden Navigationsleiste"))
            h.ui.clickText("Schwebende Navigationsleiste")
            h.ui.advance()
            h.scrollTo("Stil der schwebenden Navigationsleiste")
            for ((style, label) in listOf(
                FloatingBottomBarStyle.IosLike to "iOS-Stil (Liquid Glass)",
                FloatingBottomBarStyle.Classic to "Klassisch",
                FloatingBottomBarStyle.IosLike to "iOS-Stil (Liquid Glass)",
            )) {
                h.ui.clickText("Stil der schwebenden Navigationsleiste")
                h.ui.advance()
                h.ui.snapshot("theme-floating-style-menu")
                h.ui.clickText(label)
                h.ui.advance()
                assertEquals(style, h.floatingStyle)
                assertEquals(style, ShellSettingsController.get(h.ui.activity).floatingBottomBarStyle)
                assertFalse("Style choice must not turn on global blur", h.blur)
            }
            h.ui.clickText("Unschärfe")
            h.ui.advance()
            assertTrue(h.blur)
            assertEquals(FloatingBottomBarStyle.IosLike, h.floatingStyle)
            h.ui.snapshot("theme-floating-style-selected")
            saved = h.ui.saveAndClose()
        }
        ThemeHost(UiMode.Material, ColorMode.LIGHT, saved).use { h ->
            assertEquals(FloatingBottomBarStyle.IosLike, h.floatingStyle)
            assertTrue(h.blur)
            assertTrue(ShellSettingsController.get(h.ui.activity).enableFloatingBottomBar)
        }
    }

    @Test
    fun `coui owns its bottom navigation so the floating bar settings are hidden`() {
        ThemeHost(UiMode.Coui, ColorMode.LIGHT).use { h ->
            repeat(8) { h.ui.drag(420f, 1400f, 420f, 650f) }
            assertNull(h.ui.textNode("Schwebende Navigationsleiste"))
            assertNull(h.ui.textNode("Stil der schwebenden Navigationsleiste"))
            h.ui.snapshot("theme-coui-without-floating-bar")
        }
    }

    private class ThemeHost(mode: UiMode, color: ColorMode, saved: Bundle? = null) : AutoCloseable {
        val ui = ComposeGestureHost(saved)
        var swipe = true
        var pagerMode = -1
        var floatingStyle = FloatingBottomBarStyle.Classic
        var blur = false

        init {
            if (saved == null) {
                ui.activity.appPrefs(SettingsPrefs).edit().clear()
                    .putString(ThemeController.KEY_UI_MODE, mode.value)
                    .putString("dark_theme", color.value.toString())
                    .putBoolean("use_dynamic_color", false)
                    .commit()
            }
            ui.setContent {
                CompositionLocalProvider(LocalDensity provides Density(2f)) {
                    AppTheme {
                        val enabled = LocalEnableSwipeDismiss.current
                        val interceptionMode = LocalPagerInterceptionMode.current
                        val style = LocalFloatingBottomBarStyle.current
                        val blurEnabled = LocalEnableBlur.current
                        SideEffect {
                            swipe = enabled
                            pagerMode = interceptionMode
                            floatingStyle = style
                            blur = blurEnabled
                        }
                        androidx.compose.material3.Scaffold(contentWindowInsets = WindowInsets(0, 0, 0, 0)) {
                            ThemeScreen(onBack = {})
                        }
                    }
                }
            }
            ui.advance()
        }

        fun scrollTo(text: String) {
            repeat(8) {
                val node = ui.textNode(text)
                if (node != null && node.boundsInRoot.top in 250f..1200f) return
                ui.drag(420f, 1400f, 420f, 650f)
            }
            check(ui.textNode(text) != null) { "Could not scroll to $text" }
        }

        override fun close() = ui.close()
    }
}

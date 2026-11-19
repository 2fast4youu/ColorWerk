package at.gregor.colorwerk.ui.compose

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.itos.coui.effect.CouiFloatingRenderer
import at.gregor.colorwerk.ui.compose.components.PageScaffold
import at.gregor.colorwerk.ui.compose.components.RootScaffold
import at.gregor.colorwerk.ui.compose.components.TabsScaffold
import at.gregor.colorwerk.ui.compose.components.ScopeScreen
import at.gregor.colorwerk.ui.theme.ColorMode
import at.gregor.colorwerk.ui.theme.AppTheme
import at.gregor.colorwerk.ui.theme.ThemeController
import at.gregor.colorwerk.ui.theme.ThemePrefs
import at.gregor.colorwerk.ui.theme.UiMode
import at.gregor.colorwerk.utils.PrefState
import at.gregor.colorwerk.utils.SettingsPrefs
import at.gregor.colorwerk.utils.appPrefs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], application = Application::class, qualifiers = "de-rDE-w411dp-h880dp-xhdpi")
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ThemeSwitchCrashTest {
    @Test
    fun `switching ui mode from material to coui at runtime does not crash`() {
        val host = ComposeGestureHost()
        host.activity.appPrefs(SettingsPrefs).edit().clear()
            .putString(ThemeController.KEY_UI_MODE, UiMode.Material.value)
            .putString("dark_theme", ColorMode.LIGHT.value.toString())
            .putBoolean("use_dynamic_color", false)
            .commit()
        host.setContent {
            CompositionLocalProvider(LocalDensity provides Density(2f)) {
                AppTheme {
                    val context = LocalContext.current
                    val settings = remember { PrefState.of(context, SettingsPrefs) }
                    PageScaffold(title = "Einstellungen") { padding ->
                        ScopeScreen(
                            state = settings,
                            modifier = Modifier.fillMaxSize().padding(padding),
                        ) {
                            group()
                            list(
                                key = ThemeController.KEY_UI_MODE,
                                title = "Oberflächenstil",
                                entries = UiMode.entries.map { it.name }.toTypedArray(),
                                entryValues = UiMode.entries.map { it.value }.toTypedArray(),
                                default = UiMode.DEFAULT_VALUE,
                                onChange = { ThemePrefs.notifyChanged() },
                            )
                        }
                    }
                }
            }
        }
        assertEquals(UiMode.Material, ThemeController.getUiMode(host.activity))

        host.clickText("Oberflächenstil")
        host.advance()
        host.clickText("Coui")
        host.advance(3000)

        assertEquals(UiMode.Coui, ThemeController.getUiMode(host.activity))
        assertNotNull("COUI 页壳应渲染出页面标题", host.textNode("Einstellungen"))
        assertNotNull("切换后行尾应显示当前值 Coui", host.textNode("Coui"))
    }

    @Test
    fun `coui floating renderer constructs in robolectric`() {
        val host = ComposeGestureHost()
        var rendererOk = false
        var message = "none"
        host.setContent {
            AppTheme {
                val renderer = remember {
                    runCatching { CouiFloatingRenderer() }.onFailure { message = it.toString() }.getOrNull()
                }
                SideEffect { rendererOk = renderer != null }
                Text(if (renderer != null) "renderer-ok" else "renderer-null")
            }
        }
        println("COUI_RENDERER_OK=$rendererOk MESSAGE=$message")
    }

    @Test
    fun `switching ui mode with tabs scaffold does not crash`() {
        val host = ComposeGestureHost()
        host.activity.appPrefs(SettingsPrefs).edit().clear()
            .putString(ThemeController.KEY_UI_MODE, UiMode.Material.value)
            .putString("dark_theme", ColorMode.LIGHT.value.toString())
            .putBoolean("use_dynamic_color", false)
            .commit()
        host.setContent {
            CompositionLocalProvider(LocalDensity provides Density(2f)) {
                AppTheme {
                    val pagerState = rememberPagerState(initialPage = MainTab.Home.ordinal) { MainTab.entries.size }
                    val mainPagerState = rememberMainPagerState(pagerState)
                    MainTabs(
                        mainPagerState = mainPagerState,
                        isActive = true,
                    ) { tab, _ ->
                        Box(Modifier.fillMaxSize()) { Text(tab.name) }
                    }
                }
            }
        }
        assertEquals(UiMode.Material, ThemeController.getUiMode(host.activity))
        assertNotNull("Material 标签外壳应渲染出首页", host.textNode("Home"))

        host.activity.appPrefs(SettingsPrefs).edit()
            .putString(ThemeController.KEY_UI_MODE, UiMode.Coui.value).commit()
        ThemePrefs.notifyChanged()
        host.advance(3000)

        assertEquals(UiMode.Coui, ThemeController.getUiMode(host.activity))
        assertNotNull("COUI 标签外壳应仍渲染首页", host.textNode("Home"))
    }

    @Test
    fun `switching ui mode with full shell nesting does not crash`() {
        val host = ComposeGestureHost()
        host.activity.appPrefs(SettingsPrefs).edit().clear()
            .putString(ThemeController.KEY_UI_MODE, UiMode.Material.value)
            .putString("dark_theme", ColorMode.LIGHT.value.toString())
            .putBoolean("use_dynamic_color", false)
            .commit()
        host.setContent {
            CompositionLocalProvider(LocalDensity provides Density(2f)) {
                AppTheme {
                    RootScaffold { _ ->
                        TabsScaffold(bottomBar = { Text("底栏") }) { tabPadding ->
                            PageScaffold(title = "Einstellungen") { pagePadding ->
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(tabPadding)
                                        .padding(pagePadding),
                                ) { Text("页面内容") }
                            }
                        }
                    }
                }
            }
        }
        assertEquals(UiMode.Material, ThemeController.getUiMode(host.activity))
        assertNotNull("Material 全壳应渲染页面内容", host.textNode("页面内容"))

        host.activity.appPrefs(SettingsPrefs).edit()
            .putString(ThemeController.KEY_UI_MODE, UiMode.Coui.value).commit()
        ThemePrefs.notifyChanged()
        host.advance(3000)

        assertEquals(UiMode.Coui, ThemeController.getUiMode(host.activity))
        assertNotNull("COUI 全壳应仍渲染页面内容", host.textNode("页面内容"))
        assertNotNull("COUI 全壳应渲染出页面标题", host.textNode("Einstellungen"))
    }
}

package at.gregor.colorwerk.ui.compose.components

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.getOrNull
import at.gregor.colorwerk.ui.compose.ComposeGestureHost
import at.gregor.colorwerk.ui.theme.AppSettings
import at.gregor.colorwerk.ui.theme.AppTheme
import at.gregor.colorwerk.ui.theme.ColorMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * 顶栏「更多」弹层的宽度约束：弹层是独立窗口、按 WRAP_CONTENT 测量，
 * 条目行又是 fillMaxWidth，所以列必须先按最宽条目定宽，否则整行会铺满屏幕。
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35], application = Application::class, qualifiers = "de-rDE-w411dp-h880dp-xhdpi")
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class TopBarOverflowMenuWidthTest {

    private val labels = listOf(
        "Wählen Sie alle Share Intent",
        "Alle Aktionen für langes Drücken auf Text auswählen",
        "Alle Öffnen-Aktionen auswählen",
        "Wählen Sie alle Browser Intention",
        "Alle Daten löschen",
    )

    @Test
    fun overflowMenuColumnKeepsIntrinsicWidth() {
        ComposeGestureHost().use { host ->
            host.setContent {
                AppTheme(AppSettings(colorMode = ColorMode.LIGHT, dynamicColor = false)) {
                    Box(Modifier.fillMaxSize()) {
                        TopBarOverflowMenu(
                            items = labels.map { TopBarOverflowItem(it) {} },
                            contentDescription = "Mehr mehr",
                        )
                    }
                }
            }
            host.advance()
            host.clickLabel("Mehr mehr")
            host.advance()

            // 动作键的点击面也带 OnClick 语义，条目行的 top 在它下面。
            val rows = host.nodes().filter { node ->
                node.config.getOrNull(SemanticsActions.OnClick) != null &&
                    node.boundsInRoot.width > 0f &&
                    node.boundsInRoot.top > 0f
            }
            assertEquals("弹层条目数", labels.size, rows.size)
            rows.forEach { row ->
                // 160dp 的最小列宽在 xhdpi 下是 320px，且必须窄于窗口（822px）。
                assertTrue("Menu accommodates German labels within 160–320 dp", row.boundsInRoot.width in 320f..640f)
                assertEquals(rows.first().boundsInRoot.width, row.boundsInRoot.width)
                assertTrue("条目行不得铺满窗口", row.boundsInRoot.width < host.width)
            }
        }
    }
}

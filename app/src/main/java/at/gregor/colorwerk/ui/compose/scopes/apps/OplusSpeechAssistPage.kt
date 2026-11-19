package at.gregor.colorwerk.ui.compose.scopes.apps

import at.gregor.colorwerk.R
import at.gregor.colorwerk.ui.compose.scopes.ScopePageSpec
import at.gregor.colorwerk.utils.ModulePrefs
import at.gregor.colorwerk.utils.getOSVersionCode

/**
 * 语音助手页：强制开启 AI 语音助手通话的开关，只有 osCode >= 30 才渲染。
 */
object OplusSpeechAssistPage {

    val spec = ScopePageSpec(
        pageKey = "oplus_speech_assist",
        prefsName = ModulePrefs,
        packName = "com.heytap.speechassist",
        scopes = arrayOf("com.heytap.speechassist"),
    ) {
        val c = requireNotNull(context) { "ScopeScreen: Context fehlt" }
        if (getOSVersionCode >= 30) {
            switch(
                key = "force_enable_ai_speechassist_call",
                title = c.getString(R.string.force_enable_ai_speechassist_call),
            )
        }
    }
}

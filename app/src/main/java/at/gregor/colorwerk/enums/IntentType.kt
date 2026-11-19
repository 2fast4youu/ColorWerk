package at.gregor.colorwerk.enums

import org.lsposed.lsparanoid.Obfuscate

@Obfuscate
enum class IntentType {
    SINGLE_SHARE, MULTI_SHARE,
    PROCESS_TEXT,
    CONTENT, FILE,
    HTTP_LINK, HTTPS_LINK,
    UNKNOWN;
}
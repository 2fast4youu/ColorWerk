# ColorWerk

**Deutscher, unabhängig gepflegter Fork von [LuckyTool](https://github.com/luckyzyx/LuckyTool) für ColorOS.**

Originalentwicklung: **luckyzyx**. Deutscher Fork: **2fast4youu**. Dieses Projekt ist kein offizielles LuckyTool-Release und wird nicht vom ursprünglichen Entwickler betreut.

## Download und Voraussetzungen

Die installierbare APK liegt unter **[GitHub Releases](https://github.com/2fast4youu/ColorWerk/releases)**.

- ColorOS-Gerät mit Android 11 oder neuer; einzelne Funktionen benötigen neuere Versionen.
- Passendes **LSPosed-/Xposed-Framework**; für einige Funktionen zusätzlich Root.
- Architektur: **arm64-v8a**.
- Die ColorOS-Hooks sind aus dem Upstream übernommen. Ihre Funktion hängt von Geräte-, Firmware- und Frameworkversion ab.

## Was dieser Fork ändert

- Neuer Anzeigename **ColorWerk** und eigene Paketkennung **`at.gregor.colorwerk`**.
- Deutsche Benutzeroberfläche einschließlich Einstellungen, Funktionsbeschreibungen und Dialogen.
- Deutsche Standardtexte; technische Kennungen, Paketnamen, Formatcodes und Eigennamen bleiben unverändert.
- Updates werden ausschließlich aus diesem Fork geladen, nicht aus den inkompatibel signierten Original-Releases.
- Eigener Signierschlüssel. **Kein direktes Update über eine originale LuckyTool-Installation.**

Nicht gleichzeitig mit LuckyTool für dieselben Ziel-Apps aktivieren: doppelte Hooks können Konflikte verursachen. Einstellungen werden nicht automatisch aus dem Original übernommen.

## Installation

1. APK aus diesem Repository herunterladen und installieren.
2. ColorWerk im Framework-Manager aktivieren und die benötigten Ziel-Apps auswählen.
3. Gerät bzw. betroffene Prozesse neu starten.
4. In ColorWerk die gewünschten Funktionen aktivieren.

Root-/Xposed-Module verändern Systemprozesse. Sichere wichtige Daten und halte einen Weg zum Deaktivieren des Moduls bereit. Ein erfolgreicher APK-Build ersetzt keinen Test auf deinem konkreten ColorOS-Gerät.

## Übersetzung

Der erste deutsche Ressourcenentwurf wurde **lokal** mit dem Argos-Modell `en_de 1.3` erstellt und um manuelle Begriffs- und Ressourcenkorrekturen ergänzt. Die Ressourcenprüfung prüft Vollständigkeit, Platzhalter und XML-Struktur. Verbesserungsvorschläge bitte über die Issues dieses Forks melden.

Die Originalressourcen und die vollständige Projektgeschichte bleiben im Repository erhalten. Chinesische Kalenderzeichen als Funktionsdaten, technische Log-Kennungen und Namen der ursprünglichen Urheber sind keine zu übersetzenden UI-Bezeichnungen.

## Selbst bauen

Benötigt werden **JDK 21**, die im Projekt angegebene Android-SDK-Version und Netzwerkzugriff auf die deklarierten Maven-Repositories.

1. Repository klonen.
2. `local.properties` mit `sdk.dir=/pfad/zum/android-sdk` anlegen.
3. Eigenen Keystore außerhalb der Versionsverwaltung erstellen und `keystore/keystore.properties` anlegen:

```properties
storeFile=/absoluter/pfad/zum/eigenen.keystore
storePassword=DEIN_PASSWORT
keyAlias=DEIN_ALIAS
keyPassword=DEIN_PASSWORT
```

4. `bash gradlew clean testDebugUnitTest lintDebug assembleRelease` ausführen.

**Keystores und Passwörter gehören niemals ins Repository.** Die Original-Signatur ist nicht enthalten. Für fortlaufende Updates denselben eigenen Signierschlüssel wiederverwenden.

## Lizenz und Herkunft

Dieses Projekt und seine Änderungen werden unter **[GNU GPL v3](LICENSE.txt)** verbreitet. Lizenztexte, Original-Urheberhinweise, Commitgeschichte und Drittanbieterhinweise bleiben erhalten. Der entsprechende Quellcode zum jeweiligen Release steht im selben GitHub-Tag bereit.

- [Originalprojekt](https://github.com/luckyzyx/LuckyTool)
- [Originaldokumentation](https://luckyzyx.gitlab.io/LuckyTool_Doc/)
- [Original-README auf Englisch](README_EN.md)

Bitte Fehler der deutschen Version zuerst **hier im Fork** melden, nicht beim ursprünglichen Entwickler.

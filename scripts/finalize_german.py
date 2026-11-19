#!/usr/bin/env python3
"""Apply reviewed German overrides, maintain English reference, verify resource parity."""
from pathlib import Path
import copy,json,re,xml.etree.ElementTree as ET
R=Path(__file__).resolve().parents[1];res=R/'app/src/main/res'
def atomic_text(p,t):
 tmp=p.with_name(p.name+'.colorwerk-write')
 tmp.write_text(t);tmp.replace(p)
en=res/'values-en/strings.xml'
if not en.exists():
 en.parent.mkdir(exist_ok=True);en.write_bytes((res/'values/strings.xml').read_bytes())
base=ET.parse(en).getroot();german=ET.parse(res/'values-de/strings.xml').getroot()
lookup={e.get('name'):e for e in german}
overrides=json.loads((R/'scripts/german_overrides.json').read_text())
for k,v in overrides.items():
 assert k in lookup,k
 lookup[k].text=v.replace('\n',r'\n')
for e in german:
 if e.text:
  for old,new in [('Root-Rechteen','Root-Rechte'),('Root-Bewilligung','Root-Rechte'),('StatusBar','Statusleiste'),('Statusbar','Statusleiste'),('Fliesen','Kacheln'),('Polsterung','Innenabstand'),('Netzwerk-Geschwindigkeitsraum','Leerzeichen in der Geschwindigkeitsanzeige')]:e.text=e.text.replace(old,new)
  e.text=re.sub(r"(?<!\\)'",r"\\'",e.text)
  e.text=re.sub(r'(?<!\\)"',r'\\"',e.text)
# Additional legacy hard-coded screen labels.
extra={
 'memc_packages':'Pakete','memc_activities':'Aktivitäten','memc_add':'＋ Hinzufügen','memc_package_name':'Paketname','memc_activity_name':'Aktivitätsname','memc_rate':'Bildwiederholrate','memc_type':'Typ',
 'optimize_apps':'Apps optimieren','dialogs_and_sheets':'Untere Dialogfelder und Dialoge','sound_recorder_supported_apps':'WeCom, TIM, Feishu, Douyin',
}
for key,text in extra.items():
 if key not in lookup:
  element=ET.SubElement(german,'string',name=key);element.text=text;lookup[key]=element
ui=R/'app/src/main/java/at/gregor/colorwerk/ui'
for p in ui.rglob('*.kt'):
 t=p.read_text();old=t
 t=t.replace('ScopeScreen 未注入 Context','ScopeScreen: Context fehlt').replace('顶栏需要有界宽度','Die obere Leiste benötigt eine begrenzte Breite')
 t=t.replace('"LuckyTool_"','"ColorWerk_"').replace('"LuckyTool"','"ColorWerk"').replace('Download/LuckyTool','Download/ColorWerk')
 t=t.replace('https://github.com/luckyzyx/LuckyTool-Localization','https://github.com/2fast4youu/ColorWerk/issues')
 replacements={
 'title = "优化App"':'title = stringResource(R.string.optimize_apps)',
 'Text("Packages")':'Text(stringResource(R.string.memc_packages))',
 'Text("Activitys")':'Text(stringResource(R.string.memc_activities))',
 'title = "＋ Add"':'title = stringResource(R.string.memc_add)',
 'title = "PackageName"':'title = stringResource(R.string.memc_package_name)',
 'Text("PackageName")':'Text(stringResource(R.string.memc_package_name))',
 'Text("ActivityName")':'Text(stringResource(R.string.memc_activity_name))',
 'Text("ScreenRate")':'Text(stringResource(R.string.memc_rate))',
 'Text("Type")':'Text(stringResource(R.string.memc_type))',
 'Text("Rate: ${info.rate}")':'Text("${stringResource(R.string.memc_rate)}: ${info.rate}")',
 'Text("Type: ${info.type}")':'Text("${stringResource(R.string.memc_type)}: ${info.type}")',
 'summary = "BottomSheet + Dialog"':'summary = stringResource(R.string.dialogs_and_sheets)',
 'summary = "企业微信,TIM,飞书,抖音"':'summary = stringResource(R.string.sound_recorder_supported_apps)',
 }
 for a,b in replacements.items():t=t.replace(a,b)
 if t!=old:atomic_text(p,t)
for p in (R/'app/src/test').rglob('*.kt'):
 t=p.read_text();n=t.replace('com/luckyzyx/luckytool','at/gregor/colorwerk')
 if n!=t:p.write_text(n)
# Deutsche Standardressourcen verhindern englische Fallbacks bei anderer Systemsprache.
merged=ET.Element('resources')
for e in base:
 key=e.get('name');entry=copy.deepcopy(lookup.get(key,e))
 # Keep original non-translatable declarations on default resources.
 if e.get('translatable')=='false':entry.set('translatable','false')
 if key=='authorized':entry.text=r'Deutscher Fork von LuckyTool\nOriginalentwicklung: luckyzyx · Lizenz: GPL-3.0'
 merged.append(entry)
for key in extra:merged.append(copy.deepcopy(lookup[key]))
for path,tree in [(res/'values-de/strings.xml',german),(res/'values/strings.xml',merged)]:
 ET.indent(tree,space='    ');path.write_bytes(b'<?xml version="1.0" encoding="utf-8"?>\n'+ET.tostring(tree,encoding='utf-8')+b'\n')
print(f'Deutsche Ressourcen: {len(german)}; manuell überarbeitete Einträge: {len(overrides)}')

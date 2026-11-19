#!/usr/bin/env python3
"""Generate the German resource draft locally; review overrides before releasing.
Requires ctranslate2, sentencepiece, and the Argos en_de 1.3 model.
No app source is sent to a translation service.
"""
import argparse,copy,json,re,xml.etree.ElementTree as ET
from pathlib import Path
import ctranslate2,sentencepiece as sp
parser=argparse.ArgumentParser();parser.add_argument('--model',required=True);parser.add_argument('--cache',required=True);args=parser.parse_args()
root=Path(__file__).resolve().parents[1]
model=Path(args.model); cachefile=Path(args.cache)
cache=json.loads(cachefile.read_text()) if cachefile.exists() else {}
translator=ctranslate2.Translator(str(model/'model'),device='cpu',compute_type='int8',inter_threads=2,intra_threads=4)
tokenizer=sp.SentencePieceProcessor(model_file=str(model/'sentencepiece.model'))
english=root/'app/src/main/res/values-en/strings.xml'
source=ET.parse(english if english.exists() else root/'app/src/main/res/values/strings.xml').getroot()
# Preserve Android placeholders, escape sequences, resource references and HTML markup exactly.
protected=re.compile(r'(\\[ntr]|%\d*\$?[-+ #0,]*(?:\d+)?(?:\.\d+)?[a-zA-Z%]|<[^>]+>|https?://\S+|@[\w./]+|\n)')
overrides={
'app_name':'ColorWerk','sharedUserLabel':'ColorWerk-Benutzer',
'xposeddescription':'Deutscher Fork von LuckyTool für ColorOS. Kostenlos und quelloffen unter GPL-3.0.\nOriginalentwicklung: luckyzyx · Deutscher Fork: 2fast4youu',
'nav_function':'Funktionen','nav_home':'Startseite','nav_other':'Weitere','nav_search':'Suchen','nav_setting':'Einstellungen','fragment_source':'Quellcode',
'language':'Sprache','theme_title':'Design','dark_theme':'Dunkles Design','follow_system':'Systemeinstellung verwenden','dark_theme_off':'Immer aus','dark_theme_on':'Immer an','dark_theme_on_amoled':'Tiefschwarz (AMOLED)',
'use_dynamic_color':'Dynamische Farben','use_dynamic_color_summary':'Farben des Hintergrundbilds verwenden','theme_palette':'Farbpalette','theme_palette_summary':'Designfarbe, Palettenstil und Farbspezifikation',
'coui_follow_material_color':'Material-Farben verwenden','coui_follow_material_color_summary':'Wenn aktiviert, verwendet COUI die obigen Farbeinstellungen. Andernfalls verwendet COUI seine eigene Farbpalette.',
'settings_navigation_badge':'Aktualisierungshinweis','settings_navigation_badge_summary':'Bei einer neuen Version einen Hinweis in der Navigationsleiste anzeigen',
'settings_enable_predictive_back':'Vorausschauende Zurück-Geste','settings_enable_predictive_back_summary':'Vorausschauende Zurück-Gesten unterstützen.',
'settings_enable_swipe_dismiss':'Zurück per Wischgeste','settings_enable_swipe_dismiss_summary':'Vom Rand wischen, um zur vorherigen Seite zurückzukehren.',
'settings_pager_gesture_mode':'Wischgesten für Seiten','settings_pager_gesture_native':'Standard','settings_pager_gesture_cross_axis':'Achsenübergreifend','settings_pager_gesture_ios_like':'Wie bei iOS',
'settings_page_scale':'Darstellungsgröße','settings_page_scale_summary':'Die Darstellungsgröße der gesamten Oberfläche anpassen.',
'settings_ui_mode':'Oberflächenstil','settings_ui_mode_summary':'Den Stil der Benutzeroberfläche auswählen.','settings_ui_mode_coui_unavailable':'Erfordert Android 14 (API 34) oder neuer.',
'settings_theme':'Design','settings_theme_mode_system':'Wie System','settings_theme_mode_light':'Hell','settings_theme_mode_dark':'Dunkel',
'settings_monet':'Monet aktivieren','settings_key_color':'Grundfarbe','settings_key_color_default':'Standard','settings_color_style':'Farbstil','settings_color_spec':'Farbspezifikation',
'settings_enable_blur':'Unschärfe','settings_enable_blur_summary':'Den Hintergrund der unteren Leiste weichzeichnen.',
'settings_floating_bottom_bar':'Schwebende Navigationsleiste','settings_floating_bottom_bar_summary':'Eine schwebende untere Leiste nur mit Symbolen verwenden.',
'settings_category_badge_colors':'Farbige Kategoriesymbole','settings_category_badge_colors_summary':'Farbige Symbole für Kategorien wie Apps und Statusleiste verwenden. Deaktivieren für einfarbige Symbole passend zum Design.',
'color_red':'Rot','color_pink':'Rosa','color_purple':'Violett','color_deep_purple':'Dunkelviolett','color_indigo':'Indigo','color_blue':'Blau','color_cyan':'Cyan','color_teal':'Türkis','color_green':'Grün','color_yellow':'Gelb','color_amber':'Bernstein','color_orange':'Orange','color_brown':'Braun','color_blue_grey':'Blaugrau','color_sakura':'Kirschblüte',
'privacy_agreement':'Datenschutzerklärung','auto_check_update':'Automatisch nach Updates suchen','auto_check_update_summary':'Beim Öffnen der Startseite nach neuen Versionen suchen',
'other_settings':'Weitere Einstellungen','clear_all_data':'Alle Daten löschen','clear_all_data_summary':'Alle Funktionseinstellungen des Moduls löschen',
'clear_all_data_message':'Alle Funktionseinstellungen des Moduls wirklich löschen? Anschließend müssen die Funktionen neu eingerichtet werden.',
'check_app_userid':'Für geklonte Benutzerkonten nicht verfügbar','read_agreement':'Lies die folgenden Hinweise und stimme ihnen zu, bevor du dieses Modul verwendest.',
}
segments=set()
def chunks(text):
 result=[]
 for piece in protected.split(text or ''):
  if not piece:continue
  if protected.fullmatch(piece):result.append((piece,False));continue
  # Prevent truncation on long resource paragraphs.
  for bit in re.split(r'(?<=[.!?])(?=\s+[A-Z])',piece):
   if bit.strip():result.append((bit, bool(re.search(r'[A-Za-z]',bit))))
   else:result.append((bit,False))
 return result
for element in source:
 if element.get('translatable')=='false' or element.get('name') in overrides:continue
 for leaf in element.iter():
  if leaf.text:
   for txt,needed in chunks(leaf.text):
    if needed and txt.strip() not in cache:segments.add(txt.strip())
pending=sorted(segments)
for offset in range(0,len(pending),32):
 batch=pending[offset:offset+32]
 encoded=[tokenizer.encode(t,out_type=str) for t in batch]
 assert all(len(t)<450 for t in encoded),'Source segment too long'
 results=translator.translate_batch(encoded,beam_size=4,max_decoding_length=768,batch_type='tokens',max_batch_size=1024)
 for text,res in zip(batch,results):cache[text]=tokenizer.decode(res.hypotheses[0])
 cachefile.parent.mkdir(parents=True,exist_ok=True);cachefile.write_text(json.dumps(cache,ensure_ascii=False,indent=2))
 print(f'Translated {min(offset+32,len(pending))}/{len(pending)} new segments',flush=True)
def translate(text):
 output=''
 for bit,needed in chunks(text):
  if not needed:output+=bit;continue
  lead=bit[:len(bit)-len(bit.lstrip())];tail=bit[len(bit.rstrip()):]
  value=cache[bit.strip()]
  # Technical terminology cleanup; resource-specific review overrides win above.
  for before,after in [('Dark Theme','Dunkles Design'),('Dark theme','Dunkles Design'),('dunkles Thema','dunkles Design'),('dunkle Thema','dunkle Design'),('Themenfarbe','Designfarbe'),('Root-Berechtigung','Root-Rechte')]:value=value.replace(before,after)
  output+=lead+value+tail
 return output
out=ET.Element('resources');count=0
for element in source:
 if element.get('translatable')=='false' and element.get('name') not in overrides:continue
 e=copy.deepcopy(element)
 if e.get('name') in overrides:e.text=overrides[e.get('name')].replace('\n',r'\n');e.attrib.pop('translatable',None)
 else:
  for leaf in e.iter():
   if leaf.text:leaf.text=translate(leaf.text)
 for leaf in e.iter():
  if leaf.text:
   # AAPT requires escaped apostrophes/quotes in unquoted resource values.
   leaf.text=re.sub(r"(?<!\\)'",r"\\'",leaf.text)
   leaf.text=re.sub(r'(?<!\\)"',r'\\"',leaf.text)
 out.append(e);count+=1
ET.indent(out,space='    ')
target=root/'app/src/main/res/values-de/strings.xml';target.parent.mkdir(exist_ok=True)
target.write_bytes(b'<?xml version="1.0" encoding="utf-8"?>\n'+ET.tostring(out,encoding='utf-8')+b'\n')
print(f'Wrote {count} German resources to {target}',flush=True)

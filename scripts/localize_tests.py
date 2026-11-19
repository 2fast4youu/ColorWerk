#!/usr/bin/env python3
"""Run upstream UI assertions against the fork's German resources.
Only exact resource-valued literals and locale qualifiers are changed;
fixture data, keys and hook matching strings remain untouched.
"""
from pathlib import Path
import json,re,xml.etree.ElementTree as ET
R=Path(__file__).resolve().parents[1]
def text(s):return s.replace(r'\n','\n').replace(r"\'","'").replace(r'\"','"')
zh={e.get('name'):text(e.text or '') for e in ET.parse(R/'app/src/main/res/values-zh-rCN/strings.xml').getroot()}
de={e.get('name'):text(e.text or '') for e in ET.parse(R/'app/src/main/res/values-de/strings.xml').getroot()}
translation={v:de[k] for k,v in zh.items() if k in de and re.search(r'[\u4e00-\u9fff]',v)}
count=0
for p in (R/'app/src/test').rglob('*.kt'):
 original=p.read_text();t=original
 def replace(m):
  global count
  raw=m.group()[1:-1]
  if raw in translation:
   count+=1
   return json.dumps(translation[raw],ensure_ascii=False)
  return m.group()
 t=re.sub(r'"(?:\\.|[^"\\])*"',replace,t)
 t=t.replace('zh-rCN-w','de-rDE-w')
 if t!=original:
  temp=p.with_suffix('.kt.tmp');temp.write_text(t);temp.replace(p)
print('Angepasste ressourcenbasierte Test-Assertions:',count)

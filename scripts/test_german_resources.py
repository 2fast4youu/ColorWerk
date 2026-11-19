#!/usr/bin/env python3
"""Structural localization regression tests (stdlib only)."""
from pathlib import Path
import collections,json,re,unittest,xml.etree.ElementTree as ET
R=Path(__file__).resolve().parents[1];RES=R/'app/src/main/res'
def resources(locale):
 entries=list(ET.parse(RES/locale/'strings.xml').getroot())
 names=[e.attrib['name'] for e in entries]
 if len(names)!=len(set(names)):raise AssertionError('Duplicate resource names')
 return {e.attrib['name']:e for e in entries}
def text(e):return ''.join(e.itertext())
FORMAT=re.compile(r'%(?:\d+\$)?[-+#0,]*(?:\d+)?(?:\.\d+)?[bBhHsScCdoxXeEfgGaAtTn%]')
class GermanResourcesTest(unittest.TestCase):
 @classmethod
 def setUpClass(cls):cls.en=resources('values-en');cls.de=resources('values-de');cls.base=resources('values')
 def test_complete_translation_coverage(self):
  required={k for k,e in self.en.items() if e.get('translatable')!='false'}
  self.assertFalse(required-self.de.keys())
  self.assertGreater(len(required),1300)
 def test_nonempty(self):
  for k,e in self.de.items():
   with self.subTest(key=k):self.assertTrue(text(e).strip())
 def test_placeholders_unchanged(self):
  for k,e in self.en.items():
   if k in self.de:
    with self.subTest(key=k):self.assertEqual(collections.Counter(FORMAT.findall(text(e))),collections.Counter(FORMAT.findall(text(self.de[k]))))
 def test_default_resources_are_german(self):
  for k,e in self.de.items():
   with self.subTest(key=k):self.assertEqual(text(e),text(self.base[k]))
 def test_no_cjk_in_german_ui(self):
  for k,e in self.de.items():
   with self.subTest(key=k):self.assertIsNone(re.search(r'[\u4e00-\u9fff]',text(e)))
 def test_branding_and_navigation(self):
  self.assertEqual(text(self.base['app_name']),'ColorWerk')
  self.assertEqual(text(self.de['back']),'Zurück')
  self.assertEqual(text(self.de['nav_setting']),'Einstellungen')
 def test_fork_update_target(self):
  p=R/'app/src/main/java/at/gregor/colorwerk/utils/UpdateUtils.kt'
  self.assertIn('https://api.github.com/repos/2fast4youu/ColorWerk/releases/latest',p.read_text())
  self.assertNotIn('Xposed-Modules-Repo',p.read_text())
 def test_distinct_package(self):
  self.assertIn('project.groupName=at.gregor.colorwerk',(R/'gradle.properties').read_text())
  self.assertNotEqual('at.gregor.colorwerk','com.luckyzyx.luckytool')
if __name__=='__main__':unittest.main(verbosity=2)

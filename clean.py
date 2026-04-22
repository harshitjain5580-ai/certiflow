import os
import glob

def clean_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Replacements
    content = content.replace("((Object)((Object)this))", "this")
    content = content.replace("(Object)", "")
    content = content.replace("(Object[])", "")
    content = content.replace("(Node)", "")
    content = content.replace("(Parent)", "")
    content = content.replace("(Priority)", "")
    content = content.replace("(String[])", "")
    content = content.replace("(EventTarget)", "")

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for filepath in glob.glob("*.java"):
    clean_file(filepath)
    print(f"Cleaned {filepath}")

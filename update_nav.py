import os
import re

files = [
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\res\layout\activity_final_report.xml",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\res\layout\activity_export_share.xml",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\res\layout\activity_download_report.xml",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\res\layout\activity_download_complete.xml"
]

def add_nav_attributes(content, icon_name, nav_id):
    # Regex designed to match the LinearLayout block specifically associated with the icon
    pattern = r'(<LinearLayout\s+android:layout_width="0dp"\s+android:layout_height="match_parent"\s+android:layout_weight="1"\s+android:gravity="center"\s+android:orientation="vertical")([^>]*>\s*<ImageView[^>]*?android:src="@drawable/' + icon_name + r'")'
    
    replacement = r'\1\n            android:id="@+id/' + nav_id + r'"\n            android:clickable="true"\n            android:focusable="true"\n            android:background="?attr/selectableItemBackground"\2'
    
    return re.sub(pattern, replacement, content)

for f in files:
    try:
        with open(f, 'r', encoding='utf-8') as file:
            content = file.read()
        
        content = add_nav_attributes(content, 'ic_home', 'navHome')
        content = add_nav_attributes(content, 'ic_cases', 'navCases')
        # We also need to add IDs to reports and profile, even if reports is the current active one (so they have IDs for consistency and potential click listeners if we want to reset the view)
        content = add_nav_attributes(content, 'ic_reports', 'navReports')
        content = add_nav_attributes(content, 'ic_profile', 'navProfile')
        
        with open(f, 'w', encoding='utf-8') as file:
            file.write(content)
        print(f"Updated {f}")
    except Exception as e:
        print(f"Error processing {f}: {e}")

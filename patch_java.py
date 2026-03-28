import os
import re

files = [
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\java\com\example\healthpredict\FinalReportActivity.java",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\java\com\example\healthpredict\ExportShareActivity.java",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\java\com\example\healthpredict\DownloadReportActivity.java",
    r"c:\Users\Sugumar D\AndroidStudioProjects\healthpredict\app\src\main\java\com\example\healthpredict\DownloadCompleteActivity.java"
]

method_code = """
    private void setupBottomNavigation() {
        android.view.View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorHomeActivity.class);
                intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP | android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navCases = findViewById(R.id.navCases);
        if (navCases != null) {
            navCases.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorCasesActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navReports = findViewById(R.id.navReports);
        if (navReports != null) {
            navReports.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, ReportsActivity.class);
                startActivity(intent);
                finish();
            });
        }

        android.view.View navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, DoctorProfileActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }
"""

for f in files:
    try:
        with open(f, 'r', encoding='utf-8') as file:
            content = file.read()
            
        if "setupBottomNavigation" in content:
            print(f"Skipping {f}, already patched.")
            continue
            
        # 1. Insert method call immediately after setContentView
        content = re.sub(r'(setContentView\(R\.layout\.[^)]+\);)', r'\1\n        setupBottomNavigation();', content)
        
        # 2. Append the method before the last closing brace
        # Find the last closing brace
        last_brace_index = content.rfind('}')
        if last_brace_index != -1:
            content = content[:last_brace_index] + method_code + "\n}\n"
        
        with open(f, 'w', encoding='utf-8') as file:
            file.write(content)
        print(f"Updated {f}")
        
    except Exception as e:
        print(f"Error processing {f}: {e}")

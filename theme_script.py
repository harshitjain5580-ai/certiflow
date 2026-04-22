import re
import os

css_path = "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\style.css"

with open(css_path, "r", encoding="utf-8") as f:
    css = f.read()

# Make option and profile bg black
css = css.replace("-fx-background-color: #f8f9fa;", "-fx-background-color: #121212;")
css = css.replace("-fx-background-color: #ffffff;", "-fx-background-color: #1a1a1a;")

# Fix text colors for dark bg
css = css.replace("-fx-text-fill: #121212;", "-fx-text-fill: #ffffff;")
# Re-invert specific things that should be dark text on gold like login-button hover
css = css.replace(".login-button:hover {\n    -fx-background-color: #ffffff;", ".login-button:hover {\n    -fx-background-color: #121212;")

# Any other specific replacements
css = css.replace("-fx-border-color: #e2e8f0;", "-fx-border-color: #333333;")
css = css.replace("-fx-border-color: #cbd5e1;", "-fx-border-color: #333333;")

with open(css_path, "w", encoding="utf-8") as f:
    f.write(css)

print("CSS updated")

java_files = [
    "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\TeacherHistoryView.java",
    "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\TeacherProfileView.java",
    "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\TeacherDashboardView.java",
    "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\TeacherHomeView.java",
    "c:\\Users\\Chiraag Aggarwal\\Downloads\\certiflow-main\\certiflow-main\\TeacherStudentListView.java"
]

for file_path in java_files:
    if os.path.exists(file_path):
        with open(file_path, "r", encoding="utf-8") as f:
            content = f.read()
        
        # We need to change #f8f9fa to #121212, #ffffff to #1a1a1a for backgrounds
        content = content.replace("-fx-background-color: #f8f9fa;", "-fx-background-color: #121212;")
        content = content.replace("-fx-background-color: #ffffff;", "-fx-background-color: #1a1a1a;")
        
        # Borders
        content = content.replace("-fx-border-color: #e2e8f0;", "-fx-border-color: #333333;")
        
        # Text colors. But changing #121212 to #ffffff in string might be hard without breaking things if they are text vs bg.
        # Most of them are text:
        content = content.replace("-fx-text-fill: #121212;", "-fx-text-fill: #ffffff;")
        content = content.replace("-fx-text-fill: #4b5563;", "-fx-text-fill: #9ca3af;")
        content = content.replace("-fx-text-fill: #475569;", "-fx-text-fill: #9ca3af;")
        content = content.replace("-fx-text-fill: #1e293b;", "-fx-text-fill: #ffffff;")

        with open(file_path, "w", encoding="utf-8") as f:
            f.write(content)

print("Java files updated")

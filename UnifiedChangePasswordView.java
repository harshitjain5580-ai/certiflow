package com.certiflow;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class UnifiedChangePasswordView {
    private BorderPane view = new BorderPane();
    private String role;

    public UnifiedChangePasswordView(String role) {
        this.role = role;
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar (Adaptive) ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        
        VBox navItems = new VBox(5);
        if ("ADMIN".equalsIgnoreCase(role)) {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Overview", false, () -> App.showAdminDashboard()),
                createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
                createSidebarItem("\ud83d\udd14", "Notifications", false, () -> App.showAdminNotifications()),
                createSidebarItem("\ud83d\udc64", "Admin Profile", true, () -> App.showAdminProfile())
            );
        } else if ("TEACHER".equalsIgnoreCase(role)) {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Home Overview", false, () -> App.showTeacherHome()),
                createSidebarItem("\ud83d\udccb", "Pending Validations", false, () -> App.showTeacherDashboard()),
                createSidebarItem("\u23f2", "Action History", false, () -> App.showTeacherHistory()),
                createSidebarItem("\ud83d\udc65", "Student Roster", false, () -> App.showTeacherStudentList()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showTeacherMessaging()),
                createSidebarItem("\ud83d\udc64", "Teacher Profile", true, () -> App.showTeacherProfile())
            );
        } else { // STUDENT
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Student Portal", false, () -> App.showStudentDashboard()),
                createSidebarItem("\ud83d\udcc1", "Academic Portfolios", false, () -> App.showStudentPortfolio()),
                createSidebarItem("\ud83d\udccb", "NOC Applications", false, () -> App.showStudentNOC()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showStudentMessaging()),
                createSidebarItem("\ud83d\udc64", "My Profile", true, () -> App.showStudentProfile())
            );
        }

        Region sideSpacer = new Region(); VBox.setVgrow(sideSpacer, Priority.ALWAYS);
        Label logoutBtn = new Label("\u23fb Logout Session");
        logoutBtn.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10px;");
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());
        sidebar.getChildren().addAll(brand, navItems, sideSpacer, logoutBtn);
        this.view.setLeft(sidebar);

        // --- Main Content ---
        VBox mainContainer = new VBox(30);
        mainContainer.getStyleClass().add("main-stage");
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);
        Label title = new Label("Reset Access Password");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Update your secure security key for the CertiFlow network.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        header.getChildren().addAll(title, subtitle);

        VBox form = new VBox(25);
        form.setMaxWidth(450);
        form.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 40px; -fx-background-radius: 20px; -fx-border-color: #262626;");

        PasswordField oldF = createPassField(form, "OLD SECURITY PASSWORD");
        PasswordField newF = createPassField(form, "NEW SECURITY PASSWORD");
        PasswordField confF = createPassField(form, "CONFIRM NEW PASSWORD");

        HBox actions = new HBox(15);
        actions.setAlignment(Pos.CENTER);
        
        Label updateBtn = new Label("CONFIRM UPDATE");
        updateBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(updateBtn, Priority.ALWAYS);
        updateBtn.setAlignment(Pos.CENTER);
        updateBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand; -fx-font-size: 14px;");
        
        updateBtn.setOnMouseClicked(e -> {
            String op = oldF.getText();
            String np = newF.getText();
            String cp = confF.getText();
            
            if (np.isEmpty() || !np.equals(cp)) {
                App.addNotification("\u26a0\ufe0f", "Password Mismatch", "The new passwords do not match or are empty.");
                return;
            }

            // Simple verification and update
            boolean success = false;
            if ("ADMIN".equalsIgnoreCase(role)) {
                if (App.currentAdmin.password.equals(op)) {
                    App.currentAdmin.password = np;
                    success = true;
                }
            } else if ("TEACHER".equalsIgnoreCase(role)) {
                if (App.currentTeacher.password.equals(op)) {
                    App.currentTeacher.password = np;
                    success = true;
                }
            } else {
                if (App.currentStudent.password.equals(op)) {
                    App.currentStudent.password = np;
                    success = true;
                }
            }

            if (success) {
                App.saveDatabases();
                App.addNotification("\ud83d\udd12", "Security Updated", "Your authentication key has been refreshed successfully.");
                goBack();
            } else {
                App.addNotification("\u26a0\ufe0f", "Verification Failed", "The old password entered is incorrect.");
            }
        });

        Label cancelBtn = new Label("CANCEL");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        cancelBtn.setAlignment(Pos.CENTER);
        cancelBtn.setStyle("-fx-text-fill: #64748b; -fx-padding: 15px; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 14px;");
        cancelBtn.setOnMouseClicked(e -> goBack());

        form.getChildren().addAll(updateBtn, cancelBtn);

        mainContainer.getChildren().addAll(header, form);
        this.view.setCenter(mainContainer);
    }

    private void goBack() {
        if ("ADMIN".equalsIgnoreCase(role)) App.showAdminProfile();
        else if ("TEACHER".equalsIgnoreCase(role)) App.showTeacherProfile();
        else App.showStudentProfile();
    }

    private PasswordField createPassField(VBox container, String label) {
        VBox b = new VBox(8);
        Label l = new Label(label); l.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        PasswordField f = new PasswordField();
        f.setPromptText("\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022\u2022");
        f.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-border-color: #333; -fx-padding: 15px; -fx-background-radius: 8px; -fx-prompt-text-fill: #444;");
        b.getChildren().addAll(l, f);
        container.getChildren().add(b);
        return f;
    }

    private HBox createSidebarItem(String iconStr, String text, boolean active, Runnable action) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add(active ? "sidebar-nav-item-active" : "sidebar-nav-item");
        Label icon = new Label(iconStr); icon.getStyleClass().add("sidebar-icon");
        Label txt = new Label(text); txt.getStyleClass().add("sidebar-text");
        item.getChildren().addAll(icon, txt);
        item.setOnMouseClicked(e -> action.run());
        return item;
    }

    public BorderPane getView() { return this.view; }
}

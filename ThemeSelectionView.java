package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ThemeSelectionView {
    private BorderPane view = new BorderPane();
    private String accent = App.getAccentColor();

    public ThemeSelectionView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + accent + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        
        VBox navItems = new VBox(5);
        if (App.currentUserRole.equalsIgnoreCase("ADMIN")) {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Overview", false, () -> App.showAdminDashboard()),
                createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
                createSidebarItem("\ud83d\udd14", "Notifications", false, () -> App.showAdminNotifications()),
                createSidebarItem("\ud83d\udc64", "Admin Profile", true, () -> App.showAdminProfile())
            );
        } else {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Home Overview", false, () -> App.showTeacherHome()),
                createSidebarItem("\ud83d\udccb", "Pending Validations", false, () -> App.showTeacherDashboard()),
                createSidebarItem("\u23f2", "Action History", false, () -> App.showTeacherHistory()),
                createSidebarItem("\ud83d\udc65", "Student Roster", false, () -> App.showTeacherStudentList()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showTeacherMessaging()),
                createSidebarItem("\ud83d\udc64", "Teacher Profile", true, () -> App.showTeacherProfile())
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
        Label title = new Label("Personalize Workstation");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Select a professional color palette for your institutional interface.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        header.getChildren().addAll(title, subtitle);

        HBox themeGrid = new HBox(20);
        themeGrid.setAlignment(Pos.CENTER);
        
        themeGrid.getChildren().addAll(
            createThemeCard("Sovereign Gold", "#fac736", "The classic institutional look."),
            createThemeCard("Deep Ocean", "#3b82f6", "Calm and focused professional blue."),
            createThemeCard("Emerald Ghost", "#10b981", "Secure and efficient data green."),
            createThemeCard("Crimson Guard", "#f43f5e", "High-intensity administrative red.")
        );

        Label backBtn = new Label("BACK TO PROFILE");
        backBtn.setStyle("-fx-text-fill: #64748b; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 20px;");
        backBtn.setOnMouseClicked(e -> {
            if (App.currentUserRole.equalsIgnoreCase("ADMIN")) App.showAdminProfile();
            else App.showTeacherProfile();
        });

        mainContainer.getChildren().addAll(header, themeGrid, backBtn);
        this.view.setCenter(mainContainer);
    }

    private VBox createThemeCard(String name, String colorHex, String desc) {
        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(200);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: #262626; -fx-cursor: hand;");
        
        Circle preview = new Circle(25);
        preview.setStyle("-fx-fill: " + colorHex + ";");
        
        Label n = new Label(name);
        n.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        
        Label d = new Label(desc);
        d.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px; -fx-text-alignment: center;");
        d.setWrapText(true);
        d.setMaxWidth(150);

        card.getChildren().addAll(preview, n, d);
        
        card.setOnMouseClicked(e -> {
            if (App.currentUserRole.equalsIgnoreCase("ADMIN")) {
                App.currentAdmin.accentColor = colorHex;
                App.saveDatabases();
            } else {
                App.currentTeacher.accentColor = colorHex;
                App.saveDatabases();
            }
            App.addNotification("\ud83c\udfa8", "Theme Applied", "Workstation accent changed to " + name);
            App.showThemeSelection(); // Reload to show new accent
        });
        
        // Hover effect
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #262626; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: " + colorHex + "; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: #262626; -fx-cursor: hand;"));

        return card;
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

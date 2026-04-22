package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AdminEditProfileView {
    private BorderPane view = new BorderPane();

    public AdminEditProfileView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Overview", false, () -> App.showAdminDashboard()),
            createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
            createSidebarItem("\ud83d\udc64", "Admin Profile", true, () -> App.showAdminProfile())
        );
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
        Label title = new Label("Update Admin identity");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Modify your administrative profile details below.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        header.getChildren().addAll(title, subtitle);

        VBox form = new VBox(25);
        form.setMaxWidth(500);
        form.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 40px; -fx-background-radius: 20px; -fx-border-color: #262626;");

        TextField nameF = createField(form, "FULL NAME", App.currentAdmin.name);
        TextField roleF = createField(form, "ADMIN ROLE", App.currentAdmin.role);
        TextField emailF = createField(form, "OFFICIAL EMAIL", App.currentAdmin.email);
        TextField phoneF = createField(form, "CONTACT PHONE", App.currentAdmin.phone);
        TextField idF = createField(form, "SYSTEM ID", App.currentAdmin.systemId);

        Label saveBtn = new Label("SAVE PROFILE CHANGES");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setAlignment(Pos.CENTER);
        saveBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand; -fx-font-size: 14px;");
        
        saveBtn.setOnMouseClicked(e -> {
            App.currentAdmin.name = nameF.getText();
            App.currentAdmin.role = roleF.getText();
            App.currentAdmin.email = emailF.getText();
            App.currentAdmin.phone = phoneF.getText();
            App.currentAdmin.systemId = idF.getText();
            App.saveDatabases();
            App.addNotification("\ud83d\udc64", "Profile Updated", "Your administrative identity has been updated successfully.");
            App.showAdminProfile();
        });

        Label cancelBtn = new Label("DISCARD CHANGES");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setAlignment(Pos.CENTER);
        cancelBtn.setStyle("-fx-text-fill: #64748b; -fx-padding: 10px; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;");
        cancelBtn.setOnMouseClicked(e -> App.showAdminProfile());

        form.getChildren().addAll(saveBtn, cancelBtn);

        mainContainer.getChildren().addAll(header, form);
        this.view.setCenter(mainContainer);
    }

    private TextField createField(VBox container, String label, String value) {
        VBox b = new VBox(8);
        Label l = new Label(label); l.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        TextField f = new TextField(value);
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

package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import java.io.File;

public class AdminProfileView {
    private BorderPane view = new BorderPane();

    public AdminProfileView() {
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
        
        Label title = new Label("Admin Profile");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        HBox profileLayout = new HBox(40);
        
        // Left Column: Identity & Contact
        VBox leftCol = new VBox(25);
        leftCol.setMinWidth(400);
        
        // Avatar Block
        VBox avatarBlock = new VBox(15);
        avatarBlock.setAlignment(Pos.CENTER);
        avatarBlock.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 40px; -fx-background-radius: 15px;");
        
        Node avatarNode = App.getAvatarNode(App.currentAdmin.name, App.currentAdmin.profilePicPath, 120);
        
        Label cameraIcon = new Label("\ud83d\udcf7");
        cameraIcon.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-padding: 8px; -fx-background-radius: 20px; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 20px; -fx-cursor: hand;");
        cameraIcon.setOnMouseClicked(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select Profile Picture");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selected = fc.showOpenDialog(null);
            if (selected != null) {
                String newPath = App.saveProfilePicture(selected);
                if (newPath != null) {
                    App.currentAdmin.profilePicPath = newPath;
                    App.saveDatabases();
                    App.showAdminProfile();
                }
            }
        });
        
        StackPane avatarWrapper = new StackPane();
        avatarWrapper.getChildren().addAll(avatarNode, cameraIcon);
        StackPane.setAlignment(cameraIcon, Pos.BOTTOM_RIGHT);
        
        Label nameLbl = new Label(App.currentAdmin.name);
        nameLbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24px; -fx-font-weight: bold;");
        Label roleLbl = new Label(App.currentAdmin.role.toUpperCase());
        roleLbl.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 12px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        
        Label editBtn = new Label("EDIT ACCOUNT");
        editBtn.setStyle("-fx-border-color: " + App.getAccentColor() + "; -fx-border-radius: 8px; -fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 8px 15px; -fx-cursor: hand;");
        editBtn.setOnMouseClicked(e -> App.showAdminEditProfile());

        avatarBlock.getChildren().addAll(avatarWrapper, nameLbl, roleLbl, editBtn);
        
        VBox authSection = new VBox(10);
        Label authT = new Label("AUTHENTICATION DETAILS");
        authT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        HBox emailCard = createContactCard("\u2709", "Primary Email", App.currentAdmin.email);
        HBox phoneCard = createContactCard("\ud83d\udcde", "Contact Extension", App.currentAdmin.phone);
        HBox idCard = createContactCard("\ud83d\udcb3", "Access Node ID", App.currentAdmin.systemId);
        authSection.getChildren().addAll(authT, emailCard, phoneCard, idCard);
        
        leftCol.getChildren().addAll(avatarBlock, authSection);
        
        // Right Column: Settings & Integrity
        VBox rightCol = new VBox(25);
        HBox.setHgrow(rightCol, Priority.ALWAYS);
        
        VBox settingsSection = new VBox(10);
        Label settingsT = new Label("SYSTEM CONFIGURATION");
        settingsT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        
        HBox passCard = createSettingCard("\ud83d\udd12", "Change Access Password", () -> App.showAdminChangePassword());
        HBox interfaceCard = createSettingCard("\u2699", "App Interface Settings", () -> App.showThemeSelection());
        
        settingsSection.getChildren().addAll(settingsT, passCard, interfaceCard);
        
        VBox integrityBlock = new VBox(20);
        integrityBlock.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: " + App.getAccentColor() + "; -fx-border-width: 0 0 0 5px;");
        Label iT = new Label("ARCHIVE INTEGRITY PROTOCOL");
        iT.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label iD = new Label("This workstation is connected to the Sovereign Node Network. All sessions are monitored for post-quantum threat actors. Administrative actions are permanent and immutably recorded in the ledger.");
        iD.setWrapText(true);
        iD.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 13px; -fx-line-spacing: 5px;");
        integrityBlock.getChildren().addAll(iT, iD);
        
        rightCol.getChildren().addAll(settingsSection, integrityBlock);
        
        profileLayout.getChildren().addAll(leftCol, rightCol);
        mainContainer.getChildren().addAll(title, profileLayout);
        
        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
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

    private HBox createContactCard(String iconStr, String label, String value) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px;");
        Label icon = new Label(iconStr); icon.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 20px;");
        VBox t = new VBox(2);
        Label l = new Label(label); l.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        Label v = new Label(value); v.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: bold;");
        t.getChildren().addAll(l, v);
        card.getChildren().addAll(icon, t);
        return card;
    }

    private HBox createSettingCard(String iconStr, String label, Runnable action) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px; -fx-cursor: hand;");
        card.setOnMouseClicked(e -> action.run());
        Label icon = new Label(iconStr); icon.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 18px;");
        Label lbl = new Label(label); lbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: bold;");
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: #333;");
        card.getChildren().addAll(icon, lbl, s, arrow);
        return card;
    }

    public BorderPane getView() { return this.view; }
}

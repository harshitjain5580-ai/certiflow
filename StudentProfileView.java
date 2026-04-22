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

public class StudentProfileView {
    private BorderPane view = new BorderPane();

    public StudentProfileView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Student Portal", false, () -> App.showStudentDashboard()),
            createSidebarItem("\ud83d\udcc1", "Academic Portfolios", false, () -> App.showStudentPortfolio()),
            createSidebarItem("\ud83d\udccb", "NOC Applications", false, () -> App.showStudentNOC()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showStudentMessaging()),
            createSidebarItem("\ud83d\udc64", "My Profile", true, () -> App.showStudentProfile())
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
        
        Label title = new Label("Digital Student ID & Profile");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");

        HBox profileLayout = new HBox(40);
        
        // Left Column: Identity & Education
        VBox leftCol = new VBox(25);
        leftCol.setMinWidth(420);
        
        // Avatar Block
        VBox avatarBlock = new VBox(15);
        avatarBlock.setAlignment(Pos.CENTER);
        avatarBlock.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 40px; -fx-background-radius: 15px; -fx-border-color: #262626;");
        
        Node avatarPane = App.getAvatarNode(App.currentStudent.name, App.currentStudent.profilePicPath, 130);
        
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
                    App.currentStudent.profilePicPath = newPath;
                    App.saveDatabases();
                    App.showStudentProfile();
                }
            }
        });
        
        StackPane avatarWrapper = new StackPane();
        avatarWrapper.getChildren().addAll(avatarPane, cameraIcon);
        StackPane.setAlignment(cameraIcon, Pos.BOTTOM_RIGHT);
        
        Label nameLbl = new Label(App.currentStudent.name);
        nameLbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 26px; -fx-font-weight: bold;");
        Label idLbl = new Label("ID: " + App.currentStudent.studentId);
        idLbl.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 12px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        
        VBox eduInfo = new VBox(5);
        eduInfo.setAlignment(Pos.CENTER);
        Label majorLbl = new Label(App.currentStudent.major);
        majorLbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;");
        Label collegeLbl = new Label(App.currentStudent.college);
        collegeLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        eduInfo.getChildren().addAll(majorLbl, collegeLbl);

        Label editBtn = new Label("\u270f EDIT");
        editBtn.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px; -fx-cursor: hand; -fx-padding: 10px 20px; -fx-border-color: " + App.getAccentColor() + "44; -fx-border-radius: 20px;");
        editBtn.setOnMouseClicked(e -> App.showStudentEditProfile());

        avatarBlock.getChildren().addAll(avatarWrapper, nameLbl, idLbl, eduInfo, editBtn);
        
        VBox detailsSection = new VBox(10);
        Label detailsT = new Label("ACADEMIC STANDING");
        detailsT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        HBox yearCard = createCard("\ud83d\udcc5", "Current Progression", App.currentStudent.yearOfStudy);
        HBox semCard = createCard("\ud83d\udcd6", "Active Semester", App.currentStudent.semester);
        detailsSection.getChildren().addAll(detailsT, yearCard, semCard);
        
        leftCol.getChildren().addAll(avatarBlock, detailsSection);
        
        // Right Column: Contact & Security
        VBox rightCol = new VBox(25);
        HBox.setHgrow(rightCol, Priority.ALWAYS);
        
        VBox contactSection = new VBox(10);
        Label contactT = new Label("CONTACT INFORMATION");
        contactT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        HBox emailCard = createCard("\u2709", "Official Email", App.currentStudent.email);
        HBox phoneCard = createCard("\ud83d\udcde", "Registered Phone", App.currentStudent.phone);
        contactSection.getChildren().addAll(contactT, emailCard, phoneCard);

        VBox portfolioSection = new VBox(10);
        Label portT = new Label("PORTFOLIO HIGHLIGHTS");
        portT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        portfolioSection.getChildren().add(portT);
        
        int count = 0;
        for (App.Portfolio p : App.currentStudent.portfolioList) {
            if (count >= 2) break;
            String label = p.title;
            if (p.documentName != null) label += " (\ud83d\udcc4)";
            HBox pCard = createCard("\ud83d\udcc1", label, p.status);
            portfolioSection.getChildren().add(pCard);
            count++;
        }
        if (App.currentStudent.portfolioList.isEmpty()) {
            Label empty = new Label("No portfolios submitted yet.");
            empty.setStyle("-fx-text-fill: #444; -fx-font-size: 13px; -fx-padding: 10px;");
            portfolioSection.getChildren().add(empty);
        }

        VBox settingsSection = new VBox(10);
        Label settingsT = new Label("ACCOUNT SETTINGS");
        settingsT.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        HBox passCard = createSettingRow("\ud83d\udd12", "Change Access Password", () -> App.showStudentChangePassword());
        settingsSection.getChildren().addAll(settingsT, passCard);
        
        rightCol.getChildren().addAll(contactSection, portfolioSection, settingsSection);
        
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

    private HBox createCard(String iconStr, String label, String value) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px; -fx-border-color: #262626;");
        Label icon = new Label(iconStr); icon.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 20px;");
        VBox t = new VBox(2);
        Label l = new Label(label); l.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        Label v = new Label(value); v.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: bold;");
        t.getChildren().addAll(l, v);
        card.getChildren().addAll(icon, t);
        return card;
    }

    private HBox createSettingRow(String iconStr, String label, Runnable action) {
        HBox card = new HBox(15);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px; -fx-cursor: hand; -fx-border-color: #262626;");
        Label icon = new Label(iconStr); icon.setStyle("-fx-text-fill: #64748b; -fx-font-size: 18px;");
        Label lbl = new Label(label); lbl.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-font-weight: bold;");
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: #333;");
        card.getChildren().addAll(icon, lbl, s, arrow);
        card.setOnMouseClicked(e -> action.run());
        return card;
    }

    public BorderPane getView() { return this.view; }
}

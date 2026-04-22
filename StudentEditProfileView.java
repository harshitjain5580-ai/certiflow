package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class StudentEditProfileView {
    private BorderPane view = new BorderPane();

    public StudentEditProfileView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
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
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);
        Label title = new Label("Update student identity");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Modify your personal and academic profile details below.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        header.getChildren().addAll(title, subtitle);

        VBox form = new VBox(15);
        form.setMaxWidth(500);
        form.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 20px; -fx-border-color: #262626;");

        TextField nameF = createField(form, "FULL NAME", App.currentStudent.name);
        TextField majorF = createField(form, "MAJOR / COURSE", App.currentStudent.major);
        TextField collegeF = createField(form, "COLLEGE / INSTITUTION", App.currentStudent.college);
        TextField emailF = createField(form, "OFFICIAL EMAIL", App.currentStudent.email);
        TextField phoneF = createField(form, "REGISTERED PHONE", App.currentStudent.phone);
        TextField yearF = createField(form, "YEAR OF STUDY", App.currentStudent.yearOfStudy);
        TextField semF = createField(form, "ACTIVE SEMESTER", App.currentStudent.semester);

        Label saveBtn = new Label("SAVE PROFILE CHANGES");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setAlignment(Pos.CENTER);
        saveBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px; -fx-font-weight: bold; -fx-background-radius: 10px; -fx-cursor: hand; -fx-font-size: 14px;");
        
        saveBtn.setOnMouseClicked(e -> {
            App.currentStudent.name = nameF.getText();
            App.currentStudent.major = majorF.getText();
            App.currentStudent.college = collegeF.getText();
            App.currentStudent.email = emailF.getText();
            App.currentStudent.phone = phoneF.getText();
            App.currentStudent.yearOfStudy = yearF.getText();
            App.currentStudent.semester = semF.getText();
            App.saveDatabases();
            App.addNotification("\ud83d\udc64", "Profile Updated", "Your student identity has been updated successfully.");
            App.showStudentProfile();
        });

        Label cancelBtn = new Label("DISCARD CHANGES");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setAlignment(Pos.CENTER);
        cancelBtn.setStyle("-fx-text-fill: #64748b; -fx-padding: 10px; -fx-font-weight: bold; -fx-cursor: hand; -fx-font-size: 13px;");
        cancelBtn.setOnMouseClicked(e -> App.showStudentProfile());

        form.getChildren().addAll(saveBtn, cancelBtn);

        mainContainer.getChildren().addAll(header, form);
        
        VBox scrollWrapper = new VBox(mainContainer);
        scrollWrapper.setAlignment(Pos.TOP_CENTER);
        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(scrollWrapper);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        this.view.setCenter(scroll);
    }

    private TextField createField(VBox container, String label, String value) {
        VBox b = new VBox(8);
        Label l = new Label(label); l.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        TextField f = new TextField(value);
        f.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-border-color: #333; -fx-padding: 12px; -fx-background-radius: 8px; -fx-prompt-text-fill: #444;");
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

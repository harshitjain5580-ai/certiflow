package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class TeacherStudentProfileView {
    private BorderPane view = new BorderPane();
    private String studentName;
    private String studentRole;
    private String studentId;

    public TeacherStudentProfileView(String name, String role, String id) {
        this.studentName = name;
        this.studentRole = role;
        this.studentId = id;

        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Home Overview", false, () -> App.showTeacherHome()),
            createSidebarItem("\ud83d\udccb", "Pending Validations", false, () -> App.showTeacherDashboard()),
            createSidebarItem("\u23f2", "Action History", false, () -> App.showTeacherHistory()),
            createSidebarItem("\ud83d\udc65", "Student Roster", true, () -> App.showTeacherStudentList()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showTeacherMessaging()),
            createSidebarItem("\ud83d\udc64", "Teacher Profile", false, () -> App.showTeacherProfile())
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

        HBox topBar = new HBox();
        Label backBtn = new Label("\u276e Back to Archive");
        backBtn.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        backBtn.setOnMouseClicked(e -> App.showTeacherStudentList());
        topBar.getChildren().add(backBtn);

        HBox profileLayout = new HBox(40);
        
        // Left: Identity card
        VBox identityCard = new VBox(20);
        identityCard.setMinWidth(350);
        identityCard.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 40px; -fx-background-radius: 20px; -fx-border-color: #262626;");
        identityCard.setAlignment(Pos.CENTER);

        StackPane avatarBox = new StackPane();
        String picPath = null;
        App.StudentProfile sp = App.findStudentById(studentId);
        if (sp != null) picPath = sp.profilePicPath;
        Node avatarNode = App.getAvatarNode(studentName, picPath, 120);
        avatarBox.getChildren().add(avatarNode);

        VBox nameGroup = new VBox(5);
        nameGroup.setAlignment(Pos.CENTER);
        Label n = new Label(studentName); n.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        Label r = new Label(studentRole.toUpperCase()); r.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 11px; -fx-font-weight: bold;");
        Label idL = new Label("ID: " + studentId); idL.setStyle("-fx-text-fill: #444; -fx-font-size: 11px; -fx-font-weight: bold;");
        nameGroup.getChildren().addAll(n, r, idL);

        identityCard.getChildren().addAll(avatarBox, nameGroup);

        // Right: Academic Metrics & History
        VBox rightColumn = new VBox(25);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);

        // Metrics Row
        HBox metrics = new HBox(20);
        metrics.getChildren().addAll(
            createMetricCard("GPA", "3.84", "#2dd4bf"),
            createMetricCard("ATTENDANCE", "92%", App.getAccentColor()),
            createMetricCard("CREDITS", "104/120", "#6366f1")
        );
        for(Node m : metrics.getChildren()) HBox.setHgrow(m, Priority.ALWAYS);

        VBox historyBox = new VBox(15);
        historyBox.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px;");
        Label histTitle = new Label("NOC CLEARANCE HISTORY");
        histTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        
        VBox records = new VBox(12);
        String currentStatus = App.validationStatuses.getOrDefault(studentId, "PENDING");
        
        // Active Request
        records.getChildren().add(createHistoryRow("Institutional NOC Request", currentStatus, "Status: Active"));
        
        // Historical Records (Mock)
        records.getChildren().addAll(
            createHistoryRow("Library Clearance", "APPROVED", "Oct 24, 2023"),
            createHistoryRow("Laboratory Asset Release", "APPROVED", "Sep 15, 2023")
        );
        historyBox.getChildren().addAll(histTitle, records);

        Button msgBtn = new Button("\ud83d\udcac MESSAGE STUDENT");
        msgBtn.setMaxWidth(Double.MAX_VALUE);
        msgBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px; -fx-background-radius: 10px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        msgBtn.setOnAction(e -> App.showTeacherChatDetail(studentName));

        rightColumn.getChildren().addAll(metrics, historyBox, msgBtn);

        profileLayout.getChildren().addAll(identityCard, rightColumn);
        mainContainer.getChildren().addAll(topBar, profileLayout);

        this.view.setCenter(mainContainer);
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

    private VBox createMetricCard(String label, String val, String color) {
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: rgba(255,255,255,0.02);");
        Label l = new Label(label); l.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 10px; -fx-font-weight: bold;");
        Label v = new Label(val); v.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 24px; -fx-font-weight: bold;");
        card.getChildren().addAll(l, v);
        return card;
    }

    private HBox createHistoryRow(String title, String status, String date) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #121212; -fx-padding: 15px; -fx-background-radius: 8px;");
        VBox t = new VBox(2);
        Label tl = new Label(title); tl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");
        Label dl = new Label(date); dl.setStyle("-fx-text-fill: #444; -fx-font-size: 11px;");
        t.getChildren().addAll(tl, dl);
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        HBox actions = new HBox(10);
        if (status.equalsIgnoreCase("PENDING")) {
            Label appBtn = new Label("APPROVE");
            appBtn.setStyle("-fx-text-fill: #fac736; -fx-font-size: 10px; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #fac736; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
            appBtn.setOnMouseClicked(e -> {
                App.validationStatuses.put(studentId, "APPROVED");
                App.saveValidations();
                App.showTeacherStudentProfile(studentName, studentRole, studentId);
            });

            Label rejBtn = new Label("REJECT");
            rejBtn.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 10px; -fx-font-weight: bold; -fx-cursor: hand; -fx-border-color: #ef4444; -fx-padding: 5px 10px; -fx-border-radius: 5px;");
            rejBtn.setOnMouseClicked(e -> {
                App.validationStatuses.put(studentId, "REJECTED");
                App.saveValidations();
                App.showTeacherStudentProfile(studentName, studentRole, studentId);
            });
            actions.getChildren().addAll(rejBtn, appBtn);
        } else {
            Label v = new Label(status);
            v.setStyle("-fx-text-fill: " + (status.equals("APPROVED") ? "#2dd4bf" : "#ef4444") + "; -fx-font-size: 10px; -fx-font-weight: bold;");
            actions.getChildren().add(v);
        }
        
        row.getChildren().addAll(t, s, actions);
        return row;
    }

    public BorderPane getView() { return this.view; }
}

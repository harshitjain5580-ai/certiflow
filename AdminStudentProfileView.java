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

public class AdminStudentProfileView {
    private BorderPane view = new BorderPane();
    private String studentName;
    private String studentRole;
    private String studentId;

    public AdminStudentProfileView(String name, String role, String id) {
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
            createSidebarItem("\ud83c\udfe0", "Overview", true, () -> App.showAdminDashboard()),
            createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
            createSidebarItem("\ud83d\udc64", "Admin Profile", false, () -> App.showAdminProfile())
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
        backBtn.setOnMouseClicked(e -> App.showAdminDashboard());
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
        Label r = new Label(studentRole.toUpperCase()); r.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        Label idL = new Label("SYSTEM ID: " + studentId); idL.setStyle("-fx-text-fill: #444; -fx-font-size: 11px; -fx-font-weight: bold;");
        nameGroup.getChildren().addAll(n, r, idL);

        identityCard.getChildren().addAll(avatarBox, nameGroup);

        // Right: Records & Access
        VBox rightColumn = new VBox(25);
        HBox.setHgrow(rightColumn, Priority.ALWAYS);

        VBox credentialsBox = new VBox(15);
        credentialsBox.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px;");
        Label credTitle = new Label("VERIFIED SYSTEM CREDENTIALS");
        credTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1.5px;");
        
        VBox records = new VBox(12);
        records.getChildren().addAll(
            createDesktopRecordRow("\ud83d\udcc4", "Foundation Excellence", "ISSUED JAN 2024", true),
            createDesktopRecordRow("\ud83d\udd12", "Blockchain Ethics", "ISSUED MAR 2024", true),
            createDesktopRecordRow("\ud83d\udcca", "Network Security", "ISSUED JUN 2023", true)
        );
        credentialsBox.getChildren().addAll(credTitle, records);

        VBox lockBox = new VBox(10);
        lockBox.setStyle("-fx-background-color: rgba(239, 68, 68, 0.05); -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: rgba(239, 68, 68, 0.2);");
        Label lockT = new Label("\ud83d\uddb2 READ-ONLY ARCHIVE");
        lockT.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-font-size: 12px;");
        Label lockD = new Label("As an Administrator, you are viewing a sealed archival record. Direct modification of student data is prohibited under Article 4 of the Security Protocol.");
        lockD.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 12px;");
        lockD.setWrapText(true);
        lockBox.getChildren().addAll(lockT, lockD);

        Label commBtn = new Label("\ud83d\udcac MESSAGE");
        commBtn.setMaxWidth(Double.MAX_VALUE);
        commBtn.setAlignment(Pos.CENTER);
        commBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px; -fx-background-radius: 10px; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        commBtn.setOnMouseClicked(e -> App.showAdminChatDetail(studentName));

        rightColumn.getChildren().addAll(credentialsBox, lockBox, commBtn);

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

    private HBox createDesktopRecordRow(String icon, String title, String sub, boolean verified) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #121212; -fx-padding: 15px; -fx-background-radius: 8px;");
        Label i = new Label(icon); i.setStyle("-fx-text-fill: #eab308; -fx-font-size: 18px;");
        VBox t = new VBox(1);
        Label tl = new Label(title); tl.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label sl = new Label(sub); sl.setStyle("-fx-text-fill: #444; -fx-font-size: 11px;");
        t.getChildren().addAll(tl, sl);
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label v = new Label(verified ? "\u2714 VERIFIED" : "PENDING");
        v.setStyle("-fx-text-fill: " + (verified ? "#2dd4bf" : "#eab308") + "; -fx-font-size: 10px; -fx-font-weight: bold;");
        row.getChildren().addAll(i, t, s, v);
        return row;
    }

    public BorderPane getView() { return this.view; }
}

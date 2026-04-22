package com.certiflow;

import javafx.geometry.Pos;
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

public class PublicProfileView {
    private BorderPane view = new BorderPane();
    private String name;
    private String role;
    private String department;

    public PublicProfileView(String name, String role, String department) {
        this.name = name;
        this.role = role;
        this.department = department;

        this.view.getStyleClass().add("desktop-root");

        // --- Sidebar (Matches Current User's Role) ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        
        VBox navItems = new VBox(5);
        setupSidebar(navItems);
        
        Region sideSpacer = new Region(); VBox.setVgrow(sideSpacer, Priority.ALWAYS);
        Label logoutBtn = new Label("\u23fb Logout Session");
        logoutBtn.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10px;");
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());
        sidebar.getChildren().addAll(brand, navItems, sideSpacer, logoutBtn);
        this.view.setLeft(sidebar);

        // --- Main Content ---
        VBox mainContainer = new VBox(30);
        mainContainer.getStyleClass().add("main-stage");
        
        Label backBtn = new Label("\u276e Back to Dashboard");
        backBtn.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 14px; -fx-cursor: hand;");
        backBtn.setOnMouseClicked(e -> App.showHome());

        VBox profileCard = new VBox(30);
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 60px; -fx-background-radius: 20px; -fx-border-color: #262626;");
        
        StackPane avatar = new StackPane();
        avatar.setMaxSize(120, 120);
        
        String picPath = null;
        if (role.toLowerCase().contains("teacher") || role.toLowerCase().contains("faculty")) {
            App.TeacherProfile tp = App.findTeacherByName(name);
            if (tp != null) picPath = tp.profilePicPath;
        } else if (role.toLowerCase().contains("admin")) {
            App.AdminProfile ap = App.findAdminByName(name);
            if (ap != null) picPath = ap.profilePicPath;
        }
        
        Node avatarPane = App.getAvatarNode(name, picPath, 120);
        avatar.getChildren().add(avatarPane);

        VBox identity = new VBox(5);
        identity.setAlignment(Pos.CENTER);
        Label nm = new Label(name); nm.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label rl = new Label(role.toUpperCase()); rl.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        Label dp = new Label(department); dp.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        identity.getChildren().addAll(nm, rl, dp);

        Region s1 = new Region(); s1.setMinHeight(20);
        
        VBox infoBox = new VBox(15);
        infoBox.setMaxWidth(400);
        Label status = createInfoRow("\ud83d\udee1 Account Status", "VERIFIED INSTITUTIONAL MEMBER");
        Label visibility = createInfoRow("\ud83c\udf10 Visibility", "PUBLIC (INTRA-NETWORK)");
        infoBox.getChildren().addAll(status, visibility);

        VBox securityNote = new VBox(10);
        securityNote.setStyle("-fx-background-color: rgba(250,199,54,0.05); -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: rgba(250,199,54,0.1);");
        Label snT = new Label("\ud83d\uddb2 ARCHIVAL PROTECTION");
        snT.setStyle("-fx-text-fill: #fac736; -fx-font-weight: bold; -fx-font-size: 11px;");
        Label snD = new Label("You are viewing a professional institutional profile. Private contact information and security settings are restricted based on your access level.");
        snD.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
        snD.setWrapText(true);
        securityNote.getChildren().addAll(snT, snD);

        profileCard.getChildren().addAll(avatar, identity, s1, infoBox, securityNote);
        mainContainer.getChildren().addAll(backBtn, profileCard);

        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
    }

    private void setupSidebar(VBox navItems) {
        String currRole = App.currentUserRole;
        if ("Admin".equalsIgnoreCase(currRole)) {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Overview", false, () -> App.showAdminDashboard()),
                createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
                createSidebarItem("\ud83d\udd14", "Notifications", false, () -> App.showAdminNotifications()),
                createSidebarItem("\ud83d\udc64", "Admin Profile", false, () -> App.showAdminProfile())
            );
        } else if ("Teacher".equalsIgnoreCase(currRole)) {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Home Dashboard", false, () -> App.showTeacherHome()),
                createSidebarItem("\ud83d\udcbb", "Validation Hub", false, () -> App.showTeacherDashboard()),
                createSidebarItem("\ud83d\udc65", "Students", false, () -> App.showTeacherStudentList()),
                createSidebarItem("\ud83d\udcac", "Messages", false, () -> App.showTeacherMessaging()),
                createSidebarItem("\ud83d\udc64", "My Profile", false, () -> App.showTeacherProfile())
            );
        } else {
            navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Student Portal", false, () -> App.showStudentDashboard()),
                createSidebarItem("\ud83d\udcc1", "Academic Portfolios", false, () -> App.showStudentPortfolio()),
                createSidebarItem("\ud83d\udccb", "NOC Applications", false, () -> App.showStudentNOC()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showStudentMessaging()),
                createSidebarItem("\ud83d\udc64", "My Profile", false, () -> App.showStudentProfile())
            );
        }
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

    private Label createInfoRow(String label, String val) {
        Label l = new Label(label + ": " + val);
        l.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 13px;");
        return l;
    }

    public BorderPane getView() { return this.view; }
}

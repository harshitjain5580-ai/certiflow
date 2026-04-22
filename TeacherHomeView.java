package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TeacherHomeView {
    private BorderPane view = new BorderPane();

    public TeacherHomeView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Home Overview", true, () -> App.showTeacherHome()),
            createSidebarItem("\ud83d\udccb", "Pending Validations", false, () -> App.showTeacherDashboard()),
            createSidebarItem("\u23f2", "Action History", false, () -> App.showTeacherHistory()),
            createSidebarItem("\ud83d\udc65", "Student Roster", false, () -> App.showTeacherStudentList()),
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
        
        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        VBox welcomeBox = new VBox(2);
        Label welcome = new Label("Welcome back, Professor Sarah");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        Label subT = new Label("Manage your student certifications and pending validations.");
        subT.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        welcomeBox.getChildren().addAll(welcome, subT);
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        javafx.scene.Node topAvatar = App.getCurrentUserAvatar(32);
        topAvatar.setStyle("-fx-cursor: hand;");
        topAvatar.setOnMouseClicked(e -> App.showTeacherProfile());
        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d"); sIcon.setStyle("-fx-text-fill: #6b7280;");
        javafx.scene.control.TextField sInput = new javafx.scene.control.TextField();
        sInput.setPromptText("Search Students...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        desktopSearch.getChildren().addAll(sIcon, sInput);

        Label notifIcon = new Label("\ud83d\udd14");
        notifIcon.setStyle("-fx-text-fill: #fac736; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 15px 0 0;");
        notifIcon.setOnMouseClicked(e -> App.showNotificationPopup(notifIcon));

        header.getChildren().addAll(welcomeBox, headerSpacer, notifIcon, desktopSearch, topAvatar);

        // Summary Grid
        int pendingCount = App.getAllPendingTasks().size();
        int actionedCount = App.actionHistory.size();

        HBox summaryRow = new HBox(25);
        summaryRow.getChildren().addAll(
            createSummaryCard("\ud83d\udccb", "Pending Tasks", String.valueOf(pendingCount), "Currently Active", true),
            createSummaryCard("\u2611", "Actioned Total", String.valueOf(actionedCount), "Verified records", true)
        );
        for(Node n : summaryRow.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        // Grid Content
        GridPane grid = new GridPane();
        grid.getStyleClass().add("desktop-dashboard-grid");
        
        // Left: Quick Actions
        VBox leftCol = new VBox(20);
        GridPane.setHgrow(leftCol, Priority.ALWAYS);
        Label qaTitle = new Label("QUICK ACTIONS");
        qaTitle.setStyle("-fx-text-fill: #fac736; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");
        
        HBox action1 = createDesktopAction("\ud83d\udcbb", "Validate New Request", true, () -> App.showTeacherDashboard());
        HBox action2 = createDesktopAction("\ud83d\udc65", "View Student List", false, () -> App.showTeacherStudentList());
        HBox action3 = createDesktopAction("\ud83d\udcca", "Export Weekly Report", false, () -> App.showTeacherReportExport());
        leftCol.getChildren().addAll(qaTitle, action1, action2, action3);
        
        // Right: Recent Activity / Search
        VBox rightCol = new VBox(20);
        rightCol.setMinWidth(450);
        Label raTitle = new Label("ACTIVITY & DISCOVERY");
        raTitle.setStyle("-fx-text-fill: #fac736; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");
        
        VBox activities = new VBox(12);
        
        javafx.scene.control.ContextMenu suggestions = new javafx.scene.control.ContextMenu();
        
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            renderActiveDiscovery(activities, newV.trim().toLowerCase());
            
            suggestions.getItems().clear();
            if (newV.trim().length() > 0) {
                String q = newV.trim().toLowerCase();
                for (App.StudentProfile s : App.studentDB.values()) {
                    if (s.name.toLowerCase().contains(q) || s.studentId.toLowerCase().contains(q)) {
                        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem("\ud83c\udf93 " + s.name + " (" + s.major + ")");
                        item.setOnAction(e -> App.showForeignProfile(s.name));
                        suggestions.getItems().add(item);
                    }
                }
                if (!suggestions.getItems().isEmpty()) {
                    if (!suggestions.isShowing()) suggestions.show(sInput, javafx.geometry.Side.BOTTOM, 0, 0);
                } else {
                    suggestions.hide();
                }
            } else {
                suggestions.hide();
            }
        });

        renderActiveDiscovery(activities, "");
        
        rightCol.getChildren().addAll(raTitle, activities);

        grid.add(leftCol, 0, 0);
        grid.add(rightCol, 1, 0);

        mainContainer.getChildren().addAll(header, summaryRow, grid);
        
        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        this.view.setCenter(scrollPane);
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

    private VBox createSummaryCard(String iconStr, String title, String val, String trend, boolean pos) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; -fx-border-color: rgba(255,255,255,0.05);");
        
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);
        Label i = new Label(iconStr); i.setStyle("-fx-text-fill: #fac736; -fx-font-size: 16px;");
        Label t = new Label(title); t.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px; -fx-font-weight: bold;");
        top.getChildren().addAll(i, t);
        
        Label v = new Label(val); v.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: 800;");
        
        Label tr = new Label((pos ? "\u2197 " : "\u2198 ") + trend);
        tr.setStyle("-fx-text-fill: " + (pos ? "#2dd4bf" : "#ef4444") + "; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        card.getChildren().addAll(top, v, tr);
        return card;
    }

    private HBox createDesktopAction(String iconStr, String text, boolean primary, Runnable action) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + (primary ? "#fac736" : "#1a1a1a") + "; -fx-padding: 20px; -fx-background-radius: 12px; -fx-cursor: hand;");
        
        Label icon = new Label(iconStr);
        icon.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#fac736") + "; -fx-font-size: 20px;");
        
        Label txt = new Label(text);
        txt.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#ffffff") + "; -fx-font-weight: bold; -fx-font-size: 15px;");
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#333") + ";");
        
        row.getChildren().addAll(icon, txt, s, arrow);
        row.setOnMouseClicked(e -> action.run());
        return row;
    }

    private void renderActiveDiscovery(VBox container, String query) {
        container.getChildren().clear();
        
        if (query.isEmpty()) {
            // Default "Recent Activity" for teachers can be hardcoded or from action history
            container.getChildren().addAll(
                createDesktopActivityRow("MJ", "Marcus Johnson", "B.Sc Computer Science \u2022 Level 3", "PENDING", "2m ago", () -> App.showForeignProfile("Marcus Johnson")),
                createDesktopActivityRow("ER", "Elena Rodriguez", "M.A. Digital Arts \u2022 Level 1", "APPROVED", "1h ago", () -> App.showForeignProfile("Elena Rodriguez"))
            );
            return;
        }

        // Active Discovery (Search Students)
        boolean found = false;
        for (App.StudentProfile s : App.studentDB.values()) {
            if (s.name.toLowerCase().contains(query) || s.studentId.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopActivityRow(App.getInitials(s.name), s.name, s.major, "STUDENT", "Record", () -> App.showForeignProfile(s.name)));
                found = true;
            }
        }

        if (!found) {
            Label noRes = new Label("No student profiles match '" + query + "'");
            noRes.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-padding: 10px;");
            container.getChildren().add(noRes);
        }
    }

    private HBox createDesktopActivityRow(String initials, String name, String details, String status, String time, Runnable action) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
        row.setOnMouseClicked(e -> action.run());
        
        App.StudentProfile sp = App.studentDB.get(name);
        String path = (sp != null) ? sp.profilePicPath : null;
        javafx.scene.Node av = App.getAvatarNode(name, path, 45);
        
        VBox info = new VBox(2);
        Label nm = new Label(name); nm.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label dt = new Label(details); dt.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        info.getChildren().addAll(nm, dt);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        VBox statusBox = new VBox(2);
        statusBox.setAlignment(Pos.CENTER_RIGHT);
        Label st = new Label(status);
        st.setStyle("-fx-background-color: " + (status.equals("APPROVED") ? "rgba(45,212,191,0.1)" : "rgba(250,199,54,0.1)") + "; -fx-text-fill: " + (status.equals("APPROVED") ? "#2dd4bf" : "#fac736") + "; -fx-padding: 4px 10px; -fx-background-radius: 10px; -fx-font-size: 10px; -fx-font-weight: bold;");
        Label tm = new Label(time); tm.setStyle("-fx-text-fill: #444; -fx-font-size: 10px;");
        statusBox.getChildren().addAll(st, tm);
        
        row.getChildren().addAll(av, info, s, statusBox);
        return row;
    }

    public BorderPane getView() { return this.view; }
}

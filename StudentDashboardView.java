package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class StudentDashboardView {
    private BorderPane view = new BorderPane();

    public StudentDashboardView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Student Portal", true, () -> App.showStudentDashboard()),
            createSidebarItem("\ud83d\udcc1", "Academic Portfolios", false, () -> App.showStudentPortfolio()),
            createSidebarItem("\ud83d\udccb", "NOC Applications", false, () -> App.showStudentNOC()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showStudentMessaging()),
            createSidebarItem("\ud83d\udc64", "My Profile", false, () -> App.showStudentProfile())
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
        Label welcome = new Label("Student Command Center");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        Label subT = new Label("Track your academic credentials and clearance status.");
        subT.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        welcomeBox.getChildren().addAll(welcome, subT);
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d"); sIcon.setStyle("-fx-text-fill: #6b7280;");
        TextField sInput = new TextField();
        sInput.setPromptText("Search Faculty & Advisors...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        
        Label fIcon = new Label("\u2699"); 
        fIcon.setStyle("-fx-text-fill: #6b7280; -fx-cursor: hand;");
        fIcon.setOnMouseClicked(e -> App.showFilterView(() -> App.showStudentDashboard()));
        
        desktopSearch.getChildren().addAll(sIcon, sInput, fIcon);
        Label notifIcon = new Label("\ud83d\udd14");
        notifIcon.setStyle("-fx-text-fill: #fac736; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 15px 0 0;");
        notifIcon.setOnMouseClicked(e -> App.showNotificationPopup(notifIcon));

        javafx.scene.Node topAvatar = App.getCurrentUserAvatar(32);
        topAvatar.setStyle("-fx-cursor: hand;");
        topAvatar.setOnMouseClicked(e -> App.showStudentProfile());

        header.getChildren().addAll(welcomeBox, headerSpacer, notifIcon, desktopSearch, topAvatar);

        // Hero Info
        VBox hero = new VBox(15);
        hero.getStyleClass().add("desktop-hero-card");
        Label heroT = new Label("SECURE CREDENTIALS");
        heroT.setStyle("-fx-text-fill: #eab308; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        Label heroM = new Label("Your academic identity is verified and stored on the sovereign network.");
        heroM.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: 800; -fx-max-width: 700px;");
        hero.getChildren().addAll(heroT, heroM);

        // Calculate Stats
        long accepted = (App.currentStudent.nocRequests.stream().filter(r -> "Accepted".equalsIgnoreCase(r.status)).count())
                      + (App.currentStudent.portfolioList.stream().filter(p -> "Approved".equalsIgnoreCase(p.status) || "Accepted".equalsIgnoreCase(p.status)).count());
        long pending = (App.currentStudent.nocRequests.stream().filter(r -> "Pending".equalsIgnoreCase(r.status)).count())
                     + (App.currentStudent.portfolioList.stream().filter(p -> "Pending".equalsIgnoreCase(p.status)).count());
        long review = (App.currentStudent.nocRequests.stream().filter(r -> "Rejected".equalsIgnoreCase(r.status)).count())
                    + (App.currentStudent.portfolioList.stream().filter(p -> "Rejected".equalsIgnoreCase(p.status)).count());

        // Stats Row
        HBox statsRow = new HBox(25);
        statsRow.getChildren().addAll(
            createDesktopStatusCard(String.format("%02d", accepted), "ACCEPTED", "#2dd4bf"),
            createDesktopStatusCard(String.format("%02d", pending), "PENDING", "#eab308"),
            createDesktopStatusCard(String.format("%02d", review), "REVIEW", "#64748b")
        );
        for(Node n : statsRow.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        // Grid Content
        GridPane grid = new GridPane();
        grid.getStyleClass().add("desktop-dashboard-grid");
        
        // Left: Quick Actions
        VBox leftCol = new VBox(20);
        GridPane.setHgrow(leftCol, Priority.ALWAYS);
        Label qaTitle = new Label("QUICK ACTIONS");
        qaTitle.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5px;");
        
        HBox action1 = createDesktopAction("\ud83d\udcc1", "Submit New Portfolio", true, () -> App.showStudentPortfolio());
        HBox action2 = createDesktopAction("\ud83d\udccb", "Request NOC Validation", false, () -> App.showStudentNOC());
        leftCol.getChildren().addAll(qaTitle, action1, action2);
        
        // Right: Recent Applications / Search
        VBox rightCol = new VBox(20);
        rightCol.setMinWidth(450);
        Label raTitle = new Label("RECENT NOC & FACULTY");
        raTitle.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5px;");
        
        VBox raList = new VBox(15);
        
        javafx.scene.control.ContextMenu suggestions = new javafx.scene.control.ContextMenu();
        
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            renderDynamicDiscovery(raList, newV.trim().toLowerCase());
            
            suggestions.getItems().clear();
            if (newV.trim().length() > 0) {
                String q = newV.trim().toLowerCase();
                for (App.TeacherProfile t : App.teacherDB.values()) {
                    if (t.name.toLowerCase().contains(q)) {
                        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem("\ud83d\udc68\u200d\ud83c\udfeb " + t.name + " (" + t.department + ")");
                        item.setOnAction(e -> App.showForeignProfile(t.name));
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
        
        renderDynamicDiscovery(raList, "");
        
        rightCol.getChildren().addAll(raTitle, raList);

        grid.add(leftCol, 0, 0);
        grid.add(rightCol, 1, 0);

        mainContainer.getChildren().addAll(header, hero, statsRow, grid);
        
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

    private VBox createDesktopStatusCard(String number, String label, String color) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; -fx-border-color: rgba(255,255,255,0.05);");
        
        Label n = new Label(number);
        n.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 32px; -fx-font-weight: 800;");
        
        Label l = new Label(label);
        l.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1px;");
        
        card.getChildren().addAll(n, l);
        return card;
    }

    private HBox createDesktopAction(String iconStr, String text, boolean primary, Runnable action) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + (primary ? "#eab308" : "#1a1a1a") + "; -fx-padding: 20px; -fx-background-radius: 12px; -fx-cursor: hand;");
        
        Label icon = new Label(iconStr);
        icon.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#eab308") + "; -fx-font-size: 20px;");
        
        Label txt = new Label(text);
        txt.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#ffffff") + "; -fx-font-weight: bold; -fx-font-size: 15px;");
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: " + (primary ? "#121212" : "#333") + ";");
        
        row.getChildren().addAll(icon, txt, s, arrow);
        row.setOnMouseClicked(e -> action.run());
        return row;
    }

    public BorderPane getView() { return this.view; }

    private void renderDynamicDiscovery(VBox container, String query) {
        container.getChildren().clear();
        
        if (query.isEmpty()) {
            boolean foundApps = false;
            if (App.currentStudent.nocRequests != null) {
                for (App.NOCRequest r : App.currentStudent.nocRequests) {
                    container.getChildren().add(createRecentAppRow(r.type, r.department, r.status, r.date));
                    foundApps = true;
                }
            }
            if (!foundApps) {
                Label empty = new Label("No recent NOC applications.");
                empty.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;");
                container.getChildren().add(empty);
            }
            return;
        }

        // Discovery Mode: Search Teachers
        boolean foundFaculty = false;
        for (App.TeacherProfile t : App.teacherDB.values()) {
            if (t.name.toLowerCase().contains(query) || t.department.toLowerCase().contains(query)) {
                container.getChildren().add(createFacultySearchRow(t.name, t.department));
                foundFaculty = true;
            }
        }

        if (!foundFaculty) {
            Label noRes = new Label("No faculty profiles match '" + query + "'");
            noRes.setStyle("-fx-text-fill: #555; -fx-font-size: 13px; -fx-padding: 10px;");
            container.getChildren().add(noRes);
        }
    }

    private HBox createFacultySearchRow(String name, String dept) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 15px 20px; -fx-background-radius: 10px; -fx-border-color: #262626; -fx-cursor: hand;");
        
        // Find teacher for their avatar
        App.TeacherProfile tp = App.teacherDB.get(name);
        javafx.scene.Node avatar = App.getAvatarNode(name, (tp != null ? tp.profilePicPath : null), 40);
        
        VBox texts = new VBox(2);
        Label t = new Label(name); t.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label d = new Label(dept + " \u2022 Faculty Member"); d.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        texts.getChildren().addAll(t, d);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u203a"); arrow.setStyle("-fx-text-fill: #eab308; -fx-font-size: 20px;");
        
        row.getChildren().addAll(avatar, texts, s, arrow);
        row.setOnMouseClicked(e -> App.showForeignProfile(name));
        return row;
    }

    private HBox createRecentAppRow(String type, String dept, String status, String date) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 15px 20px; -fx-background-radius: 10px; -fx-border-color: #262626;");
        
        VBox texts = new VBox(2);
        Label t = new Label(type); t.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label d = new Label(dept + " \u2022 " + date); d.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        texts.getChildren().addAll(t, d);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        Label st = new Label(status.toUpperCase());
        String color = status.equalsIgnoreCase("Accepted") ? "#2dd4bf" : (status.equalsIgnoreCase("Pending") ? "#eab308" : "#ef4444");
        st.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-color: " + color + "11; -fx-background-radius: 5px;");
        
        row.getChildren().addAll(texts, s, st);
        return row;
    }
}

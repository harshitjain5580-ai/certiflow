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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AdminDashboardView {
    private BorderPane view = new BorderPane();

    public AdminDashboardView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");

        Node brand = App.getSidebarBranding();

        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Overview", true, () -> App.showAdminDashboard()),
                createSidebarItem("\u2605", "Shortlisted Vault", false, () -> App.showAdminShortlist()),
                createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showAdminMessaging()),
                createSidebarItem("\ud83d\udc64", "Admin Profile", false, () -> App.showAdminProfile()));

        Region sideSpacer = new Region();
        VBox.setVgrow(sideSpacer, Priority.ALWAYS);

        Label logoutBtn = new Label("\u23fb Logout Session");
        logoutBtn.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10px;");
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());

        sidebar.getChildren().addAll(brand, navItems, sideSpacer, logoutBtn);
        this.view.setLeft(sidebar);

        // --- Main Content ---
        VBox mainContainer = new VBox(30);
        mainContainer.getStyleClass().add("main-stage");

        // Top Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label welcome = new Label("Welcome back, Administrator");
        welcome.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24px; -fx-font-weight: bold;");
        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle(
                "-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d");
        sIcon.setStyle("-fx-text-fill: #6b7280;");
        TextField sInput = new TextField();
        sInput.setPromptText("Search Users (Students/Faculty)...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");

        Label fIcon = new Label("\u2699");
        fIcon.setStyle("-fx-text-fill: #6b7280; -fx-cursor: hand;");
        fIcon.setOnMouseClicked(e -> App.showFilterView(() -> App.showAdminDashboard()));

        desktopSearch.getChildren().addAll(sIcon, sInput, fIcon);

        sInput.setText(App.globalSearchQuery);

        Label notifIcon = new Label("\ud83d\udd14");
        notifIcon.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 15px 0 0;");
        notifIcon.setOnMouseClicked(e -> App.showNotificationPopup(notifIcon));

        javafx.scene.Node topAvatar = App.getCurrentUserAvatar(32);
        topAvatar.setStyle("-fx-cursor: hand;");
        topAvatar.setOnMouseClicked(e -> App.showAdminProfile());

        header.getChildren().addAll(welcome, headerSpacer, notifIcon, desktopSearch, topAvatar);

        // Hero Section (Wide)
        VBox hero = new VBox(15);
        hero.getStyleClass().add("desktop-hero-card");
        Label heroT = new Label("Secure Global Student Archive");
        heroT.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-size: 14px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        Label heroM = new Label("Unified access to the world's most verified academic credentials.");
        heroM.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: 800; -fx-max-width: 600px;");
        hero.getChildren().addAll(heroT, heroM);

        // Grid Content
        GridPane grid = new GridPane();
        grid.getStyleClass().add("desktop-dashboard-grid");

        // Left Col: Stats & Active Recents
        VBox leftCol = new VBox(25);
        GridPane.setHgrow(leftCol, Priority.ALWAYS);

        HBox statsRow = new HBox(20);
        statsRow.getChildren().addAll(
                createStatBlock("ACTIVE USERS", String.valueOf(App.studentDB.size() + App.teacherDB.size()),
                        "\ud83d\udcc4", true));

        HBox recentHeader = new HBox();
        Label rhText = new Label("USER DISCOVERY");
        rhText.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");
        recentHeader.getChildren().add(rhText);

        VBox performers = new VBox(12);

        javafx.scene.control.ContextMenu suggestions = new javafx.scene.control.ContextMenu();
        suggestions.getStyleClass().add("search-suggestions"); // We can add this to CSS later

        sInput.textProperty().addListener((obs, oldV, newV) -> {
            App.globalSearchQuery = newV.trim();
            renderPerformers(performers);

            suggestions.getItems().clear();
            if (newV.trim().length() > 0) {
                String q = newV.trim().toLowerCase();
                // Add Teachers
                for (App.TeacherProfile t : App.teacherDB.values()) {
                    if (t.name.toLowerCase().contains(q)) {
                        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(
                                "\ud83d\udc68\u200d\ud83c\udfeb " + t.name + " (" + t.department + ")");
                        item.setOnAction(e -> App.showForeignProfile(t.name));
                        suggestions.getItems().add(item);
                    }
                }
                // Add Students
                for (App.StudentProfile s : App.studentDB.values()) {
                    if (s.name.toLowerCase().contains(q)) {
                        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(
                                "\ud83c\udf93 " + s.name + " (ID: " + s.studentId + ")");
                        item.setOnAction(e -> App.showForeignProfile(s.name));
                        suggestions.getItems().add(item);
                    }
                }

                if (!suggestions.getItems().isEmpty()) {
                    if (!suggestions.isShowing()) {
                        suggestions.show(sInput, javafx.geometry.Side.BOTTOM, 0, 0);
                    }
                } else {
                    suggestions.hide();
                }
            } else {
                suggestions.hide();
            }
        });

        renderPerformers(performers);

        leftCol.getChildren().addAll(statsRow, recentHeader, performers);

        // Right Col: Quick Actions / Logs
        VBox rightCol = new VBox(25);
        rightCol.setMinWidth(300);
        rightCol.setMaxWidth(300);

        VBox interviewCard = new VBox(15);
        interviewCard.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: "
                        + App.getAccentColor() + "33; -fx-cursor: hand;");
        Label icT = new Label("INTERVIEW READINESS");
        icT.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");
        Label icM = new Label("Review the shortlisted students ready for the interview phase.");
        icM.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 13px; -fx-font-weight: bold;");
        icM.setWrapText(true);
        Label icA = new Label("Go to Shortlist \u2192");
        icA.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 11px; -fx-font-weight: bold;");

        interviewCard.getChildren().addAll(icT, icM, icA);
        interviewCard.setOnMouseClicked(e -> App.showAdminShortlist());
        interviewCard.setOnMouseEntered(e -> interviewCard.setStyle(
                "-fx-background-color: #222; -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: "
                        + App.getAccentColor() + "66; -fx-cursor: hand;"));
        interviewCard.setOnMouseExited(e -> interviewCard.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 20px; -fx-background-radius: 12px; -fx-border-color: "
                        + App.getAccentColor() + "33; -fx-cursor: hand;"));

        rightCol.getChildren().add(interviewCard);

        grid.add(leftCol, 0, 0);
        grid.add(rightCol, 1, 0);

        mainContainer.getChildren().addAll(header, hero, grid);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        this.view.setCenter(scrollPane);
    }

    private void renderPerformers(VBox performers) {
        performers.getChildren().clear();
        String q = App.globalSearchQuery.toLowerCase();

        // Search Teachers
        for (App.TeacherProfile t : App.teacherDB.values()) {
            if (q.isEmpty() || t.name.toLowerCase().contains(q) || t.department.toLowerCase().contains(q)) {
                performers.getChildren().add(createDesktopUserRow(t.name, t.department + " Faculty", "Verified", true));
            }
        }

        // Search Students
        for (App.StudentProfile s : App.studentDB.values()) {
            boolean matchesSearch = q.isEmpty() ||
                    (s.name != null && s.name.toLowerCase().contains(q)) ||
                    (s.studentId != null && s.studentId.toLowerCase().contains(q));

            if (matchesSearch) {
                String role = (s.major != null) ? s.major : "Academic Record";
                performers.getChildren().add(createDesktopUserRow(s.name, role, s.studentId, false));
            }
        }

        if (performers.getChildren().isEmpty()) {
            Label empty = new Label("No matching user records found.");
            empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-padding: 10px;");
            performers.getChildren().add(empty);
        }
    }

    private HBox createSidebarItem(String iconStr, String text, boolean active, Runnable action) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add(active ? "sidebar-nav-item-active" : "sidebar-nav-item");

        Label icon = new Label(iconStr);
        icon.getStyleClass().add("sidebar-icon");
        Label txt = new Label(text);
        txt.getStyleClass().add("sidebar-text");

        item.getChildren().addAll(icon, txt);
        item.setOnMouseClicked(e -> action.run());
        return item;
    }

    private VBox createStatBlock(String title, String val, String iconStr, boolean gold) {
        VBox block = new VBox(10);
        HBox.setHgrow(block, Priority.ALWAYS);
        block.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20px; -fx-background-radius: 12px;");

        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 10px; -fx-font-weight: bold; -fx-letter-spacing: 1px;");
        Label v = new Label(val);
        v.setStyle("-fx-text-fill: " + (gold ? App.getAccentColor() : "#ffffff")
                + "; -fx-font-size: 28px; -fx-font-weight: 900;");
        block.getChildren().addAll(t, v);
        return block;
    }

    private HBox createDesktopUserRow(String name, String role, String id, boolean isTeacher) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");

        String path = null;
        if (isTeacher) {
            App.TeacherProfile tp = App.teacherDB.get(name);
            if (tp != null)
                path = tp.profilePicPath;
        } else {
            App.StudentProfile sp = App.studentDB.get(name);
            if (sp != null)
                path = sp.profilePicPath;
        }
        javafx.scene.Node avatar = App.getAvatarNode(name, path, 40);

        VBox texts = new VBox(2);
        Label nm = new Label(name);
        nm.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label rl = new Label(role + (isTeacher ? "" : " \u2022 ID: " + id));
        rl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        texts.getChildren().addAll(nm, rl);

        Region s = new Region();
        HBox.setHgrow(s, Priority.ALWAYS);

        if (!isTeacher) {
            boolean isShort = App.isShortlisted(id);
            Label star = new Label(isShort ? "\u2605" : "\u2606");
            star.setStyle("-fx-text-fill: " + App.getAccentColor()
                    + "; -fx-font-size: 20px; -fx-cursor: hand; -fx-padding: 0 5px 0 0;");
            star.setOnMouseClicked(e -> {
                App.toggleShortlist(id);
                App.showAdminDashboard();
                e.consume();
            });
            row.getChildren().add(star);
        }

        Label msgIcon = new Label("\ud83d\udcac");
        msgIcon.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 10px 0 0;");
        msgIcon.setOnMouseClicked(e -> {
            App.showAdminChatDetail(name);
            e.consume();
        });

        Label arrow = new Label("\u203a");
        arrow.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 20px;");

        row.getChildren().addAll(avatar, texts, s, msgIcon, arrow);
        row.setOnMouseEntered(e -> row.setStyle(
                "-fx-background-color: #222; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseClicked(e -> App.showForeignProfile(name));
        return row;
    }

    public BorderPane getView() {
        return this.view;
    }
}

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AdminShortlistView {
    private BorderPane view = new BorderPane();

    public AdminShortlistView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
                createSidebarItem("\ud83c\udfe0", "Overview", false, () -> App.showAdminDashboard()),
                createSidebarItem("\u2605", "Shortlisted Vault", true, () -> App.showAdminShortlist()),
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

        VBox header = new VBox(5);
        Label subT = new Label("CURATED LIST");
        subT.setStyle("-fx-text-fill: " + App.getAccentColor()
                + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 2px;");
        Label title = new Label("Shortlisted Vault");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 32px; -fx-font-weight: bold;");
        header.getChildren().addAll(subT, title);

        // Search capability integration
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle(
                "-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d");
        sIcon.setStyle("-fx-text-fill: #6b7280;");
        javafx.scene.control.TextField sInput = new javafx.scene.control.TextField();
        sInput.setPromptText("Search Vault...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");

        Label fIcon = new Label("\u2699");
        fIcon.setStyle("-fx-text-fill: #6b7280; -fx-cursor: hand;");
        fIcon.setOnMouseClicked(e -> App.showFilterView(() -> App.showAdminShortlist()));

        desktopSearch.getChildren().addAll(sIcon, sInput, fIcon);

        Region hSpacer = new Region();
        HBox.setHgrow(hSpacer, Priority.ALWAYS);
        HBox headerBox = new HBox(header, hSpacer, desktopSearch);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        VBox list = new VBox(15);

        sInput.setText(App.globalSearchQuery);
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            App.globalSearchQuery = newV.trim();
            renderVault(list);
        });

        renderVault(list);

        mainContainer.getChildren().addAll(headerBox, list);

        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
    }

    private void renderVault(VBox list) {
        list.getChildren().clear();
        String q = App.globalSearchQuery.toLowerCase();
        boolean hasContent = false;

        for (App.StudentProfile s : App.studentDB.values()) {
            if (App.isShortlisted(s.studentId)) {
                boolean matches = q.isEmpty() ||
                        (s.name != null && s.name.toLowerCase().contains(q)) ||
                        (s.studentId != null && s.studentId.toLowerCase().contains(q));

                if (matches) {
                    String role = (s.email != null) ? s.email : "Student";
                    list.getChildren().add(createDesktopShortlistRow(s.name, role, s.studentId));
                    hasContent = true;
                }
            }
        }

        if (!hasContent) {
            VBox emptyState = new VBox(20);
            emptyState.setAlignment(Pos.CENTER);
            emptyState.setPadding(new Insets(100, 0, 0, 0));
            Label emptyIcon = new Label("\u2606");
            emptyIcon.setStyle("-fx-text-fill: #222; -fx-font-size: 80px;");
            Label emptyTxt = new Label(
                    q.isEmpty() ? "The vault is currently empty." : "No shortlisted students match your search.");
            emptyTxt.setStyle("-fx-text-fill: #333; -fx-font-size: 16px; -fx-font-weight: bold;");
            emptyState.getChildren().addAll(emptyIcon, emptyTxt);
            list.getChildren().add(emptyState);
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

    private HBox createDesktopShortlistRow(String name, String role, String id) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 15px 25px; -fx-background-radius: 12px; -fx-cursor: hand;");

        VBox texts = new VBox(2);
        Label n = new Label(name);
        n.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 16px;");
        Label b = new Label(role + " \u2022 ID: " + id);
        b.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
        texts.getChildren().addAll(n, b);

        Region s = new Region();
        HBox.setHgrow(s, Priority.ALWAYS);

        Label cloudIcon = new Label("\u2601");
        cloudIcon.setStyle("-fx-text-fill: #3ba6ff; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 10px 0 0;");
        cloudIcon.setOnMouseClicked(e -> {
            App.addNotification("\u2601", "Asset Push", name + " transmitted a verified certification asset.");
            e.consume();
        });

        Label star = new Label("\u2605");
        star.setStyle("-fx-text-fill: #eab308; -fx-font-size: 20px; -fx-cursor: hand; -fx-padding: 0 10px 0 0;");
        star.setOnMouseClicked(e -> {
            App.toggleShortlist(id);
            App.showAdminShortlist();
            e.consume();
        });

        Label msgIcon = new Label("\ud83d\udcac");
        msgIcon.setStyle("-fx-text-fill: #eab308; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 15px 0 0;");
        msgIcon.setOnMouseClicked(e -> {
            App.showAdminChatDetail(name);
            e.consume();
        });

        Label arrow = new Label("\u203a");
        arrow.setStyle("-fx-text-fill: #eab308; -fx-font-size: 24px;");

        row.getChildren().addAll(texts, s, cloudIcon, star, msgIcon, arrow);
        row.setOnMouseEntered(e -> row.setStyle(
                "-fx-background-color: #222; -fx-padding: 15px 25px; -fx-background-radius: 12px; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle(
                "-fx-background-color: #1a1a1a; -fx-padding: 15px 25px; -fx-background-radius: 12px; -fx-cursor: hand;"));
        row.setOnMouseClicked(e -> App.showAdminStudentProfile(name, role, id));
        return row;
    }

    public BorderPane getView() {
        return this.view;
    }
}

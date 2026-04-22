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

public class AdminNotificationsView {
    private BorderPane view = new BorderPane();

    public AdminNotificationsView() {
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
            createSidebarItem("\ud83d\udd14", "Notifications", true, () -> App.showAdminNotifications()),
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
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("System Notifications");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        Label clearBtn = new Label("Clear All Logs");
        clearBtn.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: rgba(239, 68, 68, 0.3); -fx-border-radius: 5px; -fx-padding: 5px 12px;");
        clearBtn.setOnMouseClicked(e -> {
            App.notificationHistory.clear();
            App.saveNotifications();
            App.showAdminNotifications();
        });
        header.getChildren().addAll(title, headerSpacer, clearBtn);

        VBox list = new VBox(15);
        if (App.notificationHistory.isEmpty()) {
            VBox empty = new VBox(20);
            empty.setAlignment(Pos.CENTER);
            empty.setPadding(new Insets(100, 0, 0, 0));
            Label emptyLbl = new Label("Secure log is empty.");
            emptyLbl.setStyle("-fx-text-fill: #333; -fx-font-size: 18px; -fx-font-weight: bold;");
            empty.getChildren().add(emptyLbl);
            list.getChildren().add(empty);
        } else {
            for (App.Notification n : App.notificationHistory) {
                list.getChildren().add(createDesktopNotifCard(n.icon, n.title, n.desc, n.time, n.urgent));
            }
        }

        mainContainer.getChildren().addAll(header, list);
        
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

    private VBox createDesktopNotifCard(String icon, String title, String desc, String time, boolean urgent) {
        VBox card = new VBox(12);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; " + 
                     (urgent ? "-fx-border-color: #ef4444; -fx-border-width: 1px;" : "-fx-border-color: #262626; -fx-border-width: 1px;"));
        
        HBox top = new HBox(15);
        top.setAlignment(Pos.CENTER_LEFT);
        Label i = new Label(icon); i.setStyle("-fx-text-fill: " + (urgent ? "#ef4444" : App.getAccentColor()) + "; -fx-font-size: 22px;");
        Label tSelection = new Label(title); tSelection.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 16px;");
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label tm = new Label(time); tm.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 11px;");
        top.getChildren().addAll(i, tSelection, s, tm);
        
        Label d = new Label(desc);
        d.setWrapText(true);
        d.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 13px; -fx-line-spacing: 4px;");
        
        card.getChildren().addAll(top, d);
        return card;
    }

    public BorderPane getView() { return this.view; }
}

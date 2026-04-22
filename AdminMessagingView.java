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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import java.util.List;

public class AdminMessagingView {
    private BorderPane view = new BorderPane();

    public AdminMessagingView() {
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
            createSidebarItem("\ud83d\udcac", "Direct Messaging", true, () -> App.showAdminMessaging()),
            createSidebarItem("\ud83d\udc64", "Admin Profile", false, () -> App.showAdminProfile())
        );
        Region sideSpacer = new Region(); VBox.setVgrow(sideSpacer, Priority.ALWAYS);
        Label logoutBtn = new Label("\u23fb Logout Session");
        logoutBtn.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10px;");
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());
        sidebar.getChildren().addAll(brand, navItems, sideSpacer, logoutBtn);
        this.view.setLeft(sidebar);

        // --- Main Content: 2-Pane Chat ---
        HBox chatSplit = new HBox();
        chatSplit.getStyleClass().add("main-stage");
        chatSplit.setPadding(new Insets(0)); // Remove padding for internal split
        
        VBox chatListSide = new VBox(20);
        chatListSide.setMinWidth(350);
        chatListSide.setMaxWidth(350);
        chatListSide.setStyle("-fx-background-color: #121212; -fx-border-color: #262626; -fx-border-width: 0 1px 0 0; -fx-padding: 30px;");
        
        Label listTitle = new Label("Secure Channels");
        listTitle.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Search Bar Implementation
        VBox searchArea = new VBox(8);
        HBox searchField = new HBox(10);
        searchField.setAlignment(Pos.CENTER_LEFT);
        searchField.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 10px 15px;");
        javafx.scene.control.TextField sInput = new javafx.scene.control.TextField();
        sInput.setPromptText("Search Directory...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        HBox.setHgrow(sInput, Priority.ALWAYS);
        searchField.getChildren().addAll(new Label("\ud83d\udd0d"), sInput);
        searchArea.getChildren().add(searchField);
        
        VBox chatItems = new VBox(10);

        sInput.textProperty().addListener((obs, oldV, newV) -> {
            renderChatList(chatItems, newV.trim().toLowerCase());
        });
        
        renderChatList(chatItems, "");
        
        chatListSide.getChildren().addAll(listTitle, searchArea, chatItems);
        
        // Chat Placeholder (Right Column)
        StackPane placeholder = new StackPane();
        HBox.setHgrow(placeholder, Priority.ALWAYS);
        VBox pCont = new VBox(15);
        pCont.setAlignment(Pos.CENTER);
        Label pIcon = new Label("\ud83d\udcac");
        pIcon.setStyle("-fx-text-fill: #1a1a1a; -fx-font-size: 80px; -fx-border-color: #333; -fx-border-radius: 50px; -fx-padding: 20px;");
        Label pTxt = new Label("Secure Communication Protocol");
        pTxt.setStyle("-fx-text-fill: #333; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label pSub = new Label("Select a conversation to begin decryption.");
        pSub.setStyle("-fx-text-fill: #222; -fx-font-size: 13px;");
        pCont.getChildren().addAll(pIcon, pTxt, pSub);
        placeholder.getChildren().add(pCont);
        
        chatSplit.getChildren().addAll(chatListSide, placeholder);
        this.view.setCenter(chatSplit);
    }

    private HBox createSidebarItem(String iconStr, String text, boolean active, Runnable action) {
        HBox item = new HBox(12);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add(active ? "sidebar-nav-item-active" : "sidebar-nav-item");
        Label icon = new Label(iconStr); icon.getStyleClass().add("sidebar-icon");
        icon.setStyle("-fx-text-fill: " + App.getAccentColor() + ";");
        Label txt = new Label(text); txt.getStyleClass().add("sidebar-text");
        item.getChildren().addAll(icon, txt);
        item.setOnMouseClicked(e -> action.run());
        return item;
    }

    private HBox createDesktopChatRow(String name, String lastMsg, String time) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: transparent; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
        
        StackPane avatar = new StackPane();
        avatar.setStyle("-fx-background-color: #1a1a1a; -fx-min-width: 45px; -fx-min-height: 45px; -fx-background-radius: 23px; -fx-border-color: #333; -fx-border-radius: 23px;");
        Label avatarInit = new Label(App.getInitials(name));
        avatarInit.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        avatar.getChildren().add(avatarInit);
        avatar.setOnMouseClicked(e -> { App.showForeignProfile(name); e.consume(); });
        
        VBox texts = new VBox(2);
        Label n = new Label(name); n.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label l = new Label(lastMsg);
        l.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        l.setMaxWidth(180);
        texts.getChildren().addAll(n, l);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label t = new Label(time); t.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px;");
        
        row.getChildren().addAll(avatar, texts, s, t);
        
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: rgba(255,255,255,0.03); -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: transparent; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseClicked(e -> App.showAdminChatDetail(name));

        return row;
    }

    public BorderPane getView() { return this.view; }

    private void renderChatList(VBox container, String query) {
        container.getChildren().clear();
        
        // Show active chats first if no query
        if (query.isEmpty()) {
            boolean found = false;
            for (String key : App.adminChatHistory.keySet()) {
                if (key.endsWith(":" + App.currentAdmin.name)) {
                    String studentName = key.split(":")[0];
                    List<App.Message> msgs = App.adminChatHistory.get(key);
                    if (msgs != null && !msgs.isEmpty()) {
                        App.Message last = msgs.get(msgs.size() - 1);
                        container.getChildren().add(createDesktopChatRow(studentName, last.content, last.timestamp));
                        found = true;
                    }
                }
            }
            if (!found) {
                Label empty = new Label("No active links.");
                empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px;");
                container.getChildren().add(empty);
            }
            return;
        }

        // Search in all users (Global Directory)
        boolean found = false;
        
        // Admins
        for (App.AdminProfile a : App.adminDB.values()) {
            if (a.name.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopChatRow(a.name, "Administrator", "Active"));
                found = true;
            }
        }
        // Teachers
        for (App.TeacherProfile t : App.teacherDB.values()) {
            if (t.name.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopChatRow(t.name, "Faculty Member", "Verified"));
                found = true;
            }
        }
        // Students
        for (App.StudentProfile s : App.studentDB.values()) {
            if (s.name.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopChatRow(s.name, "Student Profile", "Records"));
                found = true;
            }
        }

        if (!found) {
            Label noRes = new Label("No records match '" + query + "'");
            noRes.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 12px;");
            container.getChildren().add(noRes);
        }
    }
}

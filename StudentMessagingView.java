package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.List;

public class StudentMessagingView {
    private BorderPane view = new BorderPane();

    public StudentMessagingView() {
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
            createSidebarItem("\ud83d\udcac", "Direct Messaging", true, () -> App.showStudentMessaging()),
            createSidebarItem("\ud83d\udc64", "My Profile", false, () -> App.showStudentProfile())
        );
        Region sideSpacer = new Region(); VBox.setVgrow(sideSpacer, Priority.ALWAYS);
        Label logoutBtn = new Label("\u23fb Logout Session");
        logoutBtn.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10px;");
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());
        sidebar.getChildren().addAll(brand, navItems, sideSpacer, logoutBtn);
        this.view.setLeft(sidebar);

        // --- Main Content: Channels ---
        HBox split = new HBox();
        split.getStyleClass().add("main-stage");
        split.setPadding(new Insets(0));

        VBox channelSide = new VBox(20);
        channelSide.setMinWidth(380);
        channelSide.setMaxWidth(380);
        channelSide.setStyle("-fx-background-color: #121212; -fx-border-color: #262626; -fx-border-width: 0 1px 0 0; -fx-padding: 30px;");
        
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
        
        VBox channels = new VBox(15);
        
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            renderChatList(channels, newV.trim().toLowerCase());
        });
        
        renderChatList(channels, "");

        channelSide.getChildren().addAll(listTitle, searchArea, channels);

        // Content Area Placeholder
        StackPane placeholder = new StackPane();
        HBox.setHgrow(placeholder, Priority.ALWAYS);
        VBox pCont = new VBox(15);
        pCont.setAlignment(Pos.CENTER);
        Label pIcon = new Label("\ud83d\udcac");
        pIcon.setStyle("-fx-text-fill: #1a1a1a; -fx-font-size: 80px; -fx-border-color: #333; -fx-border-radius: 50px; -fx-padding: 20px;");
        Label pTxt = new Label("Secure Communication Center");
        pTxt.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label pSub = new Label("Select a verified department or faculty member to begin.");
        pSub.setStyle("-fx-text-fill: #444; -fx-font-size: 13px;");
        pCont.getChildren().addAll(pIcon, pTxt, pSub);
        placeholder.getChildren().add(pCont);

        split.getChildren().addAll(channelSide, placeholder);
        this.view.setCenter(split);
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

    private HBox createChannelRow(String name, String sub, String time, Runnable action) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: transparent; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
        
        StackPane avatar = new StackPane();
        avatar.setStyle("-fx-background-color: #1a1a1a; -fx-min-width: 45px; -fx-min-height: 45px; -fx-background-radius: 23px; -fx-border-color: #333; -fx-border-radius: 23px;");
        Label i = new Label(App.getInitials(name)); 
        i.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        avatar.getChildren().add(i);
        avatar.setOnMouseClicked(e -> { App.showForeignProfile(name); e.consume(); });
        
        VBox texts = new VBox(2);
        Label n = new Label(name); n.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label l = new Label(sub);
        l.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        l.setMaxWidth(180);
        l.setWrapText(false); // keep it to one line
        texts.getChildren().addAll(n, l);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label t = new Label(time); t.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 10px;");
        
        row.getChildren().addAll(avatar, texts, s, t);
        
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: rgba(234,179,8,0.05); -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: transparent; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseClicked(e -> action.run());

        return row;
    }

    public BorderPane getView() { return this.view; }

    private void renderChatList(VBox container, String query) {
        container.getChildren().clear();
        
        if (query.isEmpty()) {
            boolean foundAny = false;
            
            // 1. Discover all Admin threads for this student
            for (String key : App.adminChatHistory.keySet()) {
                if (key.startsWith(App.currentStudent.name + ":")) {
                    String adminName = key.split(":")[1];
                    List<App.Message> msgs = App.adminChatHistory.get(key);
                    if (msgs != null && !msgs.isEmpty()) {
                        App.Message last = msgs.get(msgs.size() - 1);
                        container.getChildren().add(createChannelRow(adminName, last.content, last.timestamp, () -> App.showStudentChatDetail(adminName, true)));
                        foundAny = true;
                    }
                }
            }
            
            // 2. Discover all Teacher threads for this student
            for (String key : App.teacherChatHistory.keySet()) {
                if (key.startsWith(App.currentStudent.name + ":")) {
                    String teacherName = key.split(":")[1];
                    List<App.Message> msgs = App.teacherChatHistory.get(key);
                    if (msgs != null && !msgs.isEmpty()) {
                        App.Message last = msgs.get(msgs.size() - 1);
                        container.getChildren().add(createChannelRow(teacherName, last.content, last.timestamp, () -> App.showStudentChatDetail(teacherName, false)));
                        foundAny = true;
                    }
                }
            }
            
            if (!foundAny) {
                Label empty = new Label("No active sequences.");
                empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-padding: 10px;");
                container.getChildren().add(empty);

                // Add default starting points
                container.getChildren().add(createChannelRow("System Admin", "Tap to start support request", "IDLE", () -> App.showStudentChatDetail("System Admin", true)));
            }
            return;
        }

        // Search Directory (Discovery Mode)
        boolean found = false;
        
        // Search Admins
        for (App.AdminProfile a : App.adminDB.values()) {
            if (a.name.toLowerCase().contains(query)) {
                container.getChildren().add(createChannelRow(a.name, a.role, "Direct", () -> App.showStudentChatDetail(a.name, true)));
                found = true;
            }
        }
        // Search Teachers
        for (App.TeacherProfile t : App.teacherDB.values()) {
            if (t.name.toLowerCase().contains(query)) {
                container.getChildren().add(createChannelRow(t.name, t.department, "Faculty", () -> App.showStudentChatDetail(t.name, false)));
                found = true;
            }
        }

        if (!found) {
            Label noRes = new Label("No institutional users match '" + query + "'");
            noRes.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 12px; -fx-padding: 10px;");
            container.getChildren().add(noRes);
        }
    }
}

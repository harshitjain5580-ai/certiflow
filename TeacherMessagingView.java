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
import java.util.List;

public class TeacherMessagingView {
    private BorderPane view = new BorderPane();

    public TeacherMessagingView() {
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
            createSidebarItem("\ud83d\udc65", "Student Roster", false, () -> App.showTeacherStudentList()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", true, () -> App.showTeacherMessaging()),
            createSidebarItem("\ud83d\udc64", "Teacher Profile", false, () -> App.showTeacherProfile())
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
        chatSplit.setPadding(new Insets(0));
        
        VBox chatListSide = new VBox(20);
        chatListSide.setMinWidth(350);
        chatListSide.setMaxWidth(350);
        chatListSide.setStyle("-fx-background-color: #121212; -fx-border-color: #262626; -fx-border-width: 0 1px 0 0; -fx-padding: 30px;");
        
        Label listTitle = new Label("Institutional Threads");
        listTitle.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Search Bar Implementation
        VBox searchArea = new VBox(8);
        HBox searchField = new HBox(10);
        searchField.setAlignment(Pos.CENTER_LEFT);
        searchField.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 10px 15px;");
        javafx.scene.control.TextField sInput = new javafx.scene.control.TextField();
        sInput.setPromptText("Search users...");
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
        Label pTxt = new Label("Student Communication Hub");
        pTxt.setStyle("-fx-text-fill: #fac736; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label pSub = new Label("Select a student to view academic messages.");
        pSub.setStyle("-fx-text-fill: #444; -fx-font-size: 13px;");
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
        Label i = new Label(App.getInitials(name)); i.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 14px; -fx-font-weight: bold;");
        avatar.getChildren().add(i);
        avatar.setOnMouseClicked(e -> { App.showForeignProfile(name); e.consume(); });
        
        VBox info = new VBox(2);
        Label n = new Label(name); n.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        
        Label l = new Label(lastMsg);
        l.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 11px;");
        l.setMaxWidth(180);
        info.getChildren().addAll(n, l);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label t = new Label(time); t.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 10px;");
        
        row.getChildren().addAll(avatar, info, s, t);
        
        row.setOnMouseEntered(e -> row.setStyle("-fx-background-color: rgba(250,199,54,0.05); -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseExited(e -> row.setStyle("-fx-background-color: transparent; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;"));
        row.setOnMouseClicked(e -> App.showTeacherChatDetail(name)); // Reuse admin chat detail for now, or create teacher specific one

        return row;
    }

    public BorderPane getView() { return this.view; }

    private void renderChatList(VBox container, String query) {
        container.getChildren().clear();
        
        if (query.isEmpty()) {
            boolean found = false;
            for (String key : App.teacherChatHistory.keySet()) {
                if (key.endsWith(":" + App.currentTeacher.name)) {
                    String studentName = key.split(":")[0];
                    List<App.Message> msgs = App.teacherChatHistory.get(key);
                    if (msgs != null && !msgs.isEmpty()) {
                        App.Message last = msgs.get(msgs.size() - 1);
                        container.getChildren().add(createDesktopChatRow(studentName, last.content, last.timestamp));
                        found = true;
                    }
                }
            }
            if (!found) {
                Label empty = new Label("No established links.");
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
                container.getChildren().add(createDesktopChatRow(a.name, "Administrator", "Direct"));
                found = true;
            }
        }
        // Teachers
        for (App.TeacherProfile t : App.teacherDB.values()) {
            if (t.name.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopChatRow(t.name, "Faculty Member", "Direct"));
                found = true;
            }
        }
        // Students
        for (App.StudentProfile s : App.studentDB.values()) {
            if (s.name.toLowerCase().contains(query)) {
                container.getChildren().add(createDesktopChatRow(s.name, "Student Profile", "Vault"));
                found = true;
            }
        }

        if (!found) {
            Label noRes = new Label("No users match '" + query + "'");
            noRes.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 12px;");
            container.getChildren().add(noRes);
        }
    }
}

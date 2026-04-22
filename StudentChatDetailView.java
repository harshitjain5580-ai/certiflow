package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class StudentChatDetailView {
    private BorderPane view = new BorderPane();
    private String partnerName;
    private boolean toAdmin;

    public StudentChatDetailView(String partnerName, boolean toAdmin) {
        this.partnerName = partnerName;
        this.toAdmin = toAdmin;
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

        // --- Main Content: Chat Detail ---
        VBox mainChat = new VBox(0);
        mainChat.getStyleClass().add("main-stage");
        mainChat.setPadding(new Insets(0));
        
        // Header
        HBox chatHeader = new HBox(15);
        chatHeader.setAlignment(Pos.CENTER_LEFT);
        chatHeader.setStyle("-fx-background-color: #121212; -fx-padding: 30px 40px; -fx-border-color: #262626; -fx-border-width: 0 0 1px 0;");
        
        Label backBtn = new Label("\u276e");
        backBtn.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 20px; -fx-cursor: hand; -fx-padding: 0 15px 0 0;");
        backBtn.setOnMouseClicked(e -> App.showStudentMessaging());
        
        VBox tBox = new VBox(2);
        Label n = new Label(partnerName.toUpperCase());
        n.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px; -fx-letter-spacing: 1px;");
        Label status = new Label("\ud83d\udd12 ENCRYPTED SESSION ACTIVE");
        status.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold;");
        tBox.getChildren().addAll(n, status);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label infoIcon = new Label("\u24d8"); infoIcon.setStyle("-fx-text-fill: #444; -fx-font-size: 20px;");
        
        chatHeader.getChildren().addAll(backBtn, tBox, s, infoIcon);

        // Messages
        VBox messageList = new VBox(20);
        messageList.setPadding(new Insets(40));
        renderMessages(messageList);
        
        ScrollPane scroll = new ScrollPane(messageList);
        scroll.setFitToWidth(true);
        VBox.setVgrow(scroll, Priority.ALWAYS);

        // Input
        HBox inputBar = new HBox(15);
        inputBar.setAlignment(Pos.CENTER_LEFT);
        inputBar.setStyle("-fx-background-color: #121212; -fx-padding: 25px 40px; -fx-border-color: #262626; -fx-border-width: 1px 0 0 0;");
        
        TextField inputField = new TextField();
        inputField.setPromptText("Type your message...");
        inputField.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-prompt-text-fill: #444; -fx-padding: 15px 25px; -fx-background-radius: 30px; -fx-border-color: #333; -fx-border-radius: 30px;");
        HBox.setHgrow(inputField, Priority.ALWAYS);
        
        Label sendIcon = new Label("\u27a4");
        sendIcon.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 24px; -fx-cursor: hand;");
        
        Runnable sendMessage = () -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                App.Message msg = new App.Message(App.currentStudent.name, partnerName, text, "Just now", false);
                App.addStudentMessage(partnerName, msg, toAdmin);
                inputField.clear();
                renderMessages(messageList);
                scroll.setVvalue(1.0);
            }
        };
        sendIcon.setOnMouseClicked(e -> sendMessage.run());
        inputField.setOnAction(e -> sendMessage.run());

        inputBar.getChildren().addAll(inputField, sendIcon);

        mainChat.getChildren().addAll(chatHeader, scroll, inputBar);
        this.view.setCenter(mainChat);
    }

    private void renderMessages(VBox chatArea) {
        chatArea.getChildren().clear();
        String key = App.currentStudent.name + ":" + partnerName;
        List<App.Message> msgs = toAdmin ? App.adminChatHistory.get(key) : App.teacherChatHistory.get(key);
        if (msgs != null) {
            for (App.Message m : msgs) {
                chatArea.getChildren().add(createMessageBubble(m, chatArea));
            }
        }
    }

    private HBox createMessageBubble(App.Message msg, VBox chatArea) {
        HBox row = new HBox();
        boolean isMine = msg.sender.equals(App.currentStudent.name);
        row.setAlignment(isMine ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        
        Label bubble = new Label(msg.content);
        bubble.setWrapText(true);
        bubble.setMaxWidth(500);
        
        if (isMine) {
            bubble.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 15px 20px; -fx-background-radius: 20px 20px 4px 20px; -fx-font-size: 14px; -fx-font-weight: 500;");
        } else {
            bubble.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: #ffffff; -fx-padding: 15px 20px; -fx-background-radius: 20px 20px 20px 4px; -fx-font-size: 14px; -fx-border-color: #333; -fx-border-radius: 20px 20px 20px 4px;");
        }
        
        row.getChildren().add(bubble);

        // Context Menu
        ContextMenu menu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("\ud83d\uddd1 Delete");
        deleteItem.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
        deleteItem.setOnAction(e -> {
            App.deleteStudentMessage(partnerName, msg.id, toAdmin);
            renderMessages(chatArea);
        });
        menu.getItems().add(deleteItem);
        bubble.setContextMenu(menu);

        return row;
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

    public BorderPane getView() { return this.view; }
}

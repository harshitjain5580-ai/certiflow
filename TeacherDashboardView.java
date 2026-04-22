package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.util.List;

public class TeacherDashboardView {
    private BorderPane view = new BorderPane();

    public TeacherDashboardView() {
        this.view.getStyleClass().add("desktop-root");

        // --- Left Sidebar ---
        VBox sidebar = new VBox(10);
        sidebar.getStyleClass().add("sidebar");
        Node brand = App.getSidebarBranding();
        brand.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 20px; -fx-padding: 0 0 30px 10px;");
        
        VBox navItems = new VBox(5);
        navItems.getChildren().addAll(
            createSidebarItem("\ud83c\udfe0", "Home Overview", false, () -> App.showTeacherHome()),
            createSidebarItem("\ud83d\udccb", "Pending Validations", true, () -> App.showTeacherDashboard()),
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
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(2);
        Label title = new Label("NOC Validation Queue");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Securely process student requests for No Objection Certificates.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(title, subtitle);
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d"); sIcon.setStyle("-fx-text-fill: #6b7280;");
        TextField sInput = new TextField();
        sInput.setPromptText("Search Requests...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        
        Label fIcon = new Label("\u2699"); 
        fIcon.setStyle("-fx-text-fill: #6b7280; -fx-cursor: hand;");
        fIcon.setOnMouseClicked(e -> App.showFilterView(() -> App.showTeacherDashboard()));
        
        desktopSearch.getChildren().addAll(sIcon, sInput, fIcon);
        
        header.getChildren().addAll(titleBox, headerSpacer, desktopSearch);

        VBox contentSection = new VBox(20);
        HBox sectionHeader = new HBox();
        sectionHeader.setAlignment(Pos.CENTER_LEFT);
        Label rhText = new Label("FOR YOUR VALIDATION");
        rhText.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 2px;");
        Region spacer2 = new Region(); HBox.setHgrow(spacer2, Priority.ALWAYS);
        
        Label newBadge = new Label("0 PENDING REQUESTS");
        newBadge.setStyle("-fx-border-color: " + App.getAccentColor() + "; -fx-border-radius: 12px; -fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 3px 12px; -fx-background-color: rgba(250,199,54,0.05);");
        sectionHeader.getChildren().addAll(rhText, spacer2, newBadge);

        sInput.setText(App.globalSearchQuery);
        
        VBox requestsList = new VBox(15);
        
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            App.globalSearchQuery = newV.trim();
            renderPendingTasks(requestsList, newBadge);
        });
        
        renderPendingTasks(requestsList, newBadge);
        
        contentSection.getChildren().addAll(sectionHeader, requestsList);

        mainContainer.getChildren().addAll(header, contentSection);
        
        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
    }

    private void renderPendingTasks(VBox requestsList, Label newBadge) {
        requestsList.getChildren().clear();
        String q = App.globalSearchQuery.toLowerCase();
        List<App.PendingTask> allTasks = App.getAllPendingTasks();
        List<App.PendingTask> filtered = new java.util.ArrayList<>();
        
        for (App.PendingTask t : allTasks) {
            boolean matches = q.isEmpty() || 
                             t.title.toLowerCase().contains(q) || 
                             t.studentName.toLowerCase().contains(q) ||
                             t.category.toLowerCase().contains(q);
            if (matches) filtered.add(t);
        }
        
        newBadge.setText(filtered.size() + " PENDING REQUESTS");
        for (App.PendingTask t : filtered) {
            requestsList.getChildren().add(createRequestCard(t.category, t.title, t.desc, t.studentName, t.id, t.isPortfolio));
        }
        
        if (filtered.isEmpty()) {
            Label empty = new Label(q.isEmpty() ? "All work has been validated. Good job!" : "No requests match your search.");
            empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px; -fx-padding: 40px;");
            requestsList.getChildren().add(empty);
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

    private VBox createRequestCard(String category, String workTitle, String workDesc, String studentName, String id, boolean isPortfolio) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; -fx-border-color: #262626;");
        
        HBox top = new HBox(15);
        top.setAlignment(Pos.CENTER_LEFT);
        
        Label catLabel = new Label(category);
        catLabel.setStyle("-fx-background-color: " + App.getAccentColor() + "22; -fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4px 10px; -fx-background-radius: 4px;");
        
        Label sName = new Label(studentName.toUpperCase());
        sName.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px; -fx-font-weight: bold; -fx-letter-spacing: 1px;");
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label date = new Label("JUST NOW");
        date.setStyle("-fx-text-fill: #444; -fx-font-size: 10px;");
        top.getChildren().addAll(catLabel, sName, s, date);
        
        VBox context = new VBox(8);
        Label title = new Label(workTitle);
        title.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label desc = new Label(workDesc);
        desc.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px; -fx-line-spacing: 3px;");
        desc.setWrapText(true);
        context.getChildren().addAll(title, desc);

        HBox footer = new HBox(15);
        footer.setAlignment(Pos.CENTER_LEFT);
        Button viewBtn = new Button("\ud83d\udcc1 Evidence Attachment");
        viewBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #333; -fx-text-fill: #94a3b8; -fx-font-size: 12px; -fx-padding: 8px 15px; -fx-background-radius: 6px; -fx-cursor: hand;");
        
        Region fs = new Region(); HBox.setHgrow(fs, Priority.ALWAYS);
        Button rejBtn = new Button("REJECT");
        rejBtn.setStyle("-fx-background-color: transparent; -fx-border-color: #ef4444; -fx-text-fill: #ef4444; -fx-font-weight: bold; -fx-padding: 8px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        Button appBtn = new Button("APPROVE");
        appBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-font-weight: bold; -fx-padding: 8px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        
        Runnable refresh = () -> {
            App.saveActionHistory();
            App.showTeacherDashboard();
            App.addNotification("\u231b", "Validation Sync", "Validation records updated for " + studentName);
        };

        appBtn.setOnAction(e -> {
            if (isPortfolio) App.updatePortfolioStatus(id, "Accepted");
            else App.updateNOCStatus(id, "Accepted");
            
            String init = studentName.length() >= 1 ? studentName.substring(0, 1).toUpperCase() : "S";
            String d = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy")).toUpperCase();
            String truncatedDesc = workDesc.length() > 25 ? workDesc.substring(0, 25) + "..." : workDesc;
            App.actionHistory.add(0, new App.HistoryRecord(init, studentName, category + " \u2022 " + workTitle, "APPROVED", truncatedDesc, d));
            refresh.run();
        });

        rejBtn.setOnAction(e -> {
            if (isPortfolio) App.updatePortfolioStatus(id, "Rejected");
            else App.updateNOCStatus(id, "Rejected");
            
            String init = studentName.length() >= 1 ? studentName.substring(0, 1).toUpperCase() : "S";
            String d = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy")).toUpperCase();
            String truncatedDesc = workDesc.length() > 25 ? workDesc.substring(0, 25) + "..." : workDesc;
            App.actionHistory.add(0, new App.HistoryRecord(init, studentName, category + " \u2022 " + workTitle, "REJECTED", truncatedDesc, d));
            refresh.run();
        });

        footer.getChildren().addAll(viewBtn, fs, rejBtn, appBtn);
        card.getChildren().addAll(top, context, footer);
        return card;
    }

    public BorderPane getView() { return this.view; }
}

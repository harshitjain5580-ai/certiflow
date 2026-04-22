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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TeacherHistoryView {
    private BorderPane view = new BorderPane();

    public TeacherHistoryView() {
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
            createSidebarItem("\u23f2", "Action History", true, () -> App.showTeacherHistory()),
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
        Label title = new Label("Action History");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subtitle = new Label("Review past request decisions and audit logs.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(title, subtitle);
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d"); sIcon.setStyle("-fx-text-fill: #6b7280;");
        TextField sInput = new TextField();
        sInput.setPromptText("Search Logs...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            lastQuery = newV.trim().toLowerCase();
            refreshHistory(activeFilter, lastQuery);
        });
        
        desktopSearch.getChildren().addAll(sIcon, sInput);
        
        header.getChildren().addAll(titleBox, headerSpacer, desktopSearch);

        // Filters Row
        HBox filtersRow = new HBox(12);
        filtersRow.getChildren().addAll(
            createFilterChip("All Decisions"),
            createFilterChip("Approved"),
            createFilterChip("Rejected")
        );

        // Dashboard Content
        this.tableContainer = new VBox(20);
        refreshHistory("All Decisions");

        mainContainer.getChildren().addAll(header, filtersRow, tableContainer);
        
        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
    }

    private VBox tableContainer;
    private String activeFilter = "All Decisions";

    private void refreshHistory(String filter, String query) {
        this.activeFilter = filter;
        tableContainer.getChildren().clear();
        
        // Group by Date
        java.util.LinkedHashMap<String, java.util.List<App.HistoryRecord>> grouped = new java.util.LinkedHashMap<>();
        for (App.HistoryRecord r : App.actionHistory) {
            boolean matchesFilter = filter.equals("All Decisions") || filter.equalsIgnoreCase(r.status);
            boolean matchesSearch = query.isEmpty() || 
                                    (r.name != null && r.name.toLowerCase().contains(query)) || 
                                    (r.details != null && r.details.toLowerCase().contains(query));
            
            if (matchesFilter && matchesSearch) {
                grouped.computeIfAbsent(r.date, k -> new java.util.ArrayList<>()).add(r);
            }
        }

        for (String date : grouped.keySet()) {
            Label dateL = new Label(date);
            dateL.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px; -fx-padding: " + (tableContainer.getChildren().isEmpty() ? "0" : "20px") + " 0 0 0;");
            
            VBox list = new VBox(12);
            for (App.HistoryRecord r : grouped.get(date)) {
                String course = r.name.equals(App.currentStudent.name) ? App.currentStudent.email : (r.details.split("\u2022").length > 1 ? r.details.split("\u2022")[1].trim() : "Student");
                String stId = r.name.equals(App.currentStudent.name) ? App.currentStudent.studentId : "STU-" + Math.abs(r.name.hashCode() % 10000);
                list.getChildren().add(createDesktopDecisionRow(r.initials, r.name, r.details, r.status, r.reason, () -> App.showTeacherStudentProfile(r.name, course, stId)));
            }
            tableContainer.getChildren().addAll(dateL, list);
        }

        if (tableContainer.getChildren().isEmpty()) {
            Label empty = new Label("No records found.");
            empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px; -fx-padding: 40px;");
            tableContainer.getChildren().add(empty);
        }
    }

    private String lastQuery = "";

    private void refreshHistory(String filter) {
        refreshHistory(filter, lastQuery);
    }

    private Label createFilterChip(String text) {
        boolean active = text.equals(activeFilter);
        Label l = new Label(text);
        l.setStyle("-fx-background-color: " + (active ? App.getAccentColor() : "#1a1a1a") + "; -fx-text-fill: " + (active ? "#121212" : "#ffffff") + "; -fx-padding: 8px 18px; -fx-background-radius: 20px; -fx-font-weight: bold; -fx-font-size: 12px; -fx-cursor: hand;");
        
        l.setOnMouseClicked(e -> {
            // Update all chips in parent
            HBox parent = (HBox) l.getParent();
            for (javafx.scene.Node node : parent.getChildren()) {
                if (node instanceof Label) {
                    Label other = (Label) node;
                    boolean nowActive = other.getText().equals(text);
                    other.setStyle("-fx-background-color: " + (nowActive ? App.getAccentColor() : "#1a1a1a") + "; -fx-text-fill: " + (nowActive ? "#121212" : "#ffffff") + "; -fx-padding: 8px 18px; -fx-background-radius: 20px; -fx-font-weight: bold; -fx-font-size: 12px; -fx-cursor: hand;");
                }
            }
            refreshHistory(text);
        });
        return l;
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

    private Label createChip(String text, boolean active) {
        Label l = new Label(text);
        l.setStyle("-fx-background-color: " + (active ? App.getAccentColor() : "#1a1a1a") + "; -fx-text-fill: " + (active ? "#121212" : "#ffffff") + "; -fx-padding: 8px 18px; -fx-background-radius: 20px; -fx-font-weight: bold; -fx-font-size: 12px; -fx-cursor: hand;");
        return l;
    }

    private HBox createDesktopDecisionRow(String initials, String name, String details, String status, String reason, Runnable action) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20px; -fx-background-radius: 12px; -fx-cursor: hand;");
        row.setOnMouseClicked(e -> action.run());
        
        Label avatar = new Label(initials);
        avatar.setStyle("-fx-background-color: #262626; -fx-text-fill: white; -fx-min-width: 50px; -fx-min-height: 50px; -fx-alignment: center; -fx-background-radius: 25px; -fx-font-weight: bold;");
        
        VBox info = new VBox(2);
        Label nm = new Label(name); nm.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;");
        Label dt = new Label(details); dt.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        info.getChildren().addAll(nm, dt);
        
        Region s1 = new Region(); HBox.setHgrow(s1, Priority.ALWAYS);
        
        VBox reasonBox = new VBox(2);
        Label rsT = new Label("DECISION REASON"); rsT.setStyle("-fx-text-fill: #333; -fx-font-size: 9px; -fx-font-weight: bold;");
        Label rsV = new Label(reason); rsV.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        reasonBox.getChildren().addAll(rsT, rsV);
        
        Region s2 = new Region(); HBox.setHgrow(s2, Priority.ALWAYS);
        
        Label st = new Label(status);
        st.setStyle("-fx-background-color: " + (status.equals("APPROVED") ? "rgba(45,212,191,0.1)" : "rgba(239,68,68,0.1)") + "; -fx-text-fill: " + (status.equals("APPROVED") ? "#2dd4bf" : "#ef4444") + "; -fx-padding: 6px 12px; -fx-background-radius: 10px; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        row.getChildren().addAll(avatar, info, s1, reasonBox, s2, st);
        return row;
    }

    public BorderPane getView() { return this.view; }
}

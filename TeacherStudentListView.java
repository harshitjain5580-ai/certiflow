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

public class TeacherStudentListView {
    private BorderPane view = new BorderPane();

    public TeacherStudentListView() {
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
            createSidebarItem("\ud83d\udcc5", "Student Roster", true, () -> App.showTeacherStudentList()),
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
        Label title = new Label("Student Roster");
        title.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-size: 20px; -fx-font-weight: bold;");
        Label subtitle = new Label("View and manage student academic profiles and credentials.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(title, subtitle);
        Region headerSpacer = new Region(); HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        // Search
        HBox desktopSearch = new HBox(10);
        desktopSearch.setAlignment(Pos.CENTER_LEFT);
        desktopSearch.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 8px; -fx-padding: 8px 15px; -fx-min-width: 300px;");
        Label sIcon = new Label("\ud83d\udd0d"); sIcon.setStyle("-fx-text-fill: #6b7280;");
        TextField sInput = new TextField();
        sInput.setPromptText("Search Roster...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #444;");
        
        Label fIcon = new Label("\u2699"); 
        fIcon.setStyle("-fx-text-fill: #6b7280; -fx-cursor: hand;");
        fIcon.setOnMouseClicked(e -> App.showFilterView(() -> App.showTeacherStudentList()));
        
        desktopSearch.getChildren().addAll(sIcon, sInput, fIcon);
        
        header.getChildren().addAll(titleBox, headerSpacer, desktopSearch);

        VBox rosterSection = new VBox(20);
        Label listHeader = new Label("ENROLLED STUDENTS");
        listHeader.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5px;");
        
        VBox rosterList = new VBox(12);
        
        sInput.setText(App.globalSearchQuery);
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            App.globalSearchQuery = newV.trim();
            renderRoster(rosterList);
        });
        
        renderRoster(rosterList);
        
        rosterSection.getChildren().addAll(listHeader, rosterList);

        mainContainer.getChildren().addAll(header, rosterSection);
        
        ScrollPane scroll = new ScrollPane(mainContainer);
        scroll.setFitToWidth(true);
        this.view.setCenter(scroll);
    }

    private void renderRoster(VBox rosterList) {
        rosterList.getChildren().clear();
        String q = App.globalSearchQuery.toLowerCase();
        
        for (App.StudentProfile s : App.studentDB.values()) {
            boolean matchesSearch = q.isEmpty() || 
                                    (s.name != null && s.name.toLowerCase().contains(q)) || 
                                    (s.studentId != null && s.studentId.toLowerCase().contains(q));
            
            // Basic Filter Matcher 
            boolean matchesFilters = true;
            if (!App.globalFilters.isEmpty()) {
                if (App.globalFilters.containsKey("Student ID") && s.studentId != null && !s.studentId.equalsIgnoreCase(App.globalFilters.get("Student ID"))) matchesFilters = false;
            }
            
            if (matchesSearch && matchesFilters) {
                String course = (s.email != null) ? s.email : "Unregistered"; 
                rosterList.getChildren().add(createDesktopStudentItem(s.name, course, s.studentId, () -> App.showTeacherStudentProfile(s.name, course, s.studentId)));
            }
        }
        
        if (rosterList.getChildren().isEmpty()) {
            Label empty = new Label("No students match the current search parameters.");
            empty.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px; -fx-padding: 30px;");
            rosterList.getChildren().add(empty);
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

    private HBox createDesktopStudentItem(String name, String course, String id, Runnable action) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20px 25px; -fx-background-radius: 12px; -fx-border-color: #262626; -fx-cursor: hand;");
        card.setOnMouseClicked(e -> action.run());
        
        StackPane avatar = new StackPane();
        avatar.setStyle("-fx-background-color: #262626; -fx-min-width: 45px; -fx-min-height: 45px; -fx-background-radius: 23px;");
        Label avT = new Label(name.substring(0, 1).toUpperCase());
        avT.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold;");
        avatar.getChildren().add(avT);

        VBox info = new VBox(2);
        Label nm = new Label(name); nm.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 15px;");
        Label cr = new Label(course); cr.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        info.getChildren().addAll(nm, cr);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        Label idLbl = new Label("ID: " + id);
        idLbl.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 12px; -fx-padding: 5px 12px; -fx-background-color: rgba(250,199,54,0.05); -fx-background-radius: 8px;");
        
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: #333;");
        
        card.getChildren().addAll(avatar, info, s, idLbl, arrow);
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #222; -fx-padding: 20px 25px; -fx-background-radius: 12px; -fx-border-color: #fac736; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20px 25px; -fx-background-radius: 12px; -fx-border-color: #262626; -fx-cursor: hand;"));
        
        return card;
    }

    public BorderPane getView() { return this.view; }
}

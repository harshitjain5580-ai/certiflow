package com.certiflow;

import javafx.scene.Node;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;

public class StudentNOCView {
    private BorderPane view = new BorderPane();
    private StackPane rootPane = new StackPane();

    public StudentNOCView() {
        rootPane.getChildren().add(view);
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
            createSidebarItem("\ud83d\udccb", "NOC Applications", true, () -> App.showStudentNOC()),
            createSidebarItem("\ud83d\udcac", "Direct Messaging", false, () -> App.showStudentMessaging()),
            createSidebarItem("\ud83d\udc64", "My Profile", false, () -> App.showStudentProfile())
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
        
        VBox headerTitle = new VBox(5);
        Label title = new Label("No Objection Certificates");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subT = new Label("Request clearance and track validation status across departments.");
        subT.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        headerTitle.getChildren().addAll(title, subT);

        HBox nocGrid = new HBox(30);
        
        // Active Requests
        VBox requestsCol = new VBox(20);
        HBox.setHgrow(requestsCol, Priority.ALWAYS);
        Label reqTitle = new Label("ACTIVE REQUESTS");
        reqTitle.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5px;");
        
        VBox requestList = new VBox(15);
        for (App.NOCRequest r : App.currentStudent.nocRequests) {
            requestList.getChildren().add(createNOCRow(r.department, r.type, r.status, r.date));
        }
        requestsCol.getChildren().addAll(reqTitle, requestList);

        // New Request Sidebar
        VBox actionCol = new VBox(20);
        actionCol.setMinWidth(350);
        Label actTitle = new Label("NEW VALIDATION");
        actTitle.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1.5px;");
        
        VBox actionCard = new VBox(20);
        actionCard.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; -fx-border-color: #262626;");
        Label acT = new Label("Request Department Clearance");
        acT.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-weight: bold;");
        Label acD = new Label("Select a department to initiate a digital NOC request. Ensure all dues are cleared.");
        acD.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        acD.setWrapText(true);
        
        Button reqBtn = new Button("Initiate Request \u2192");
        reqBtn.setMaxWidth(Double.MAX_VALUE);
        reqBtn.setStyle("-fx-background-color: #eab308; -fx-text-fill: #121212; -fx-font-weight: bold; -fx-padding: 12px; -fx-background-radius: 8px; -fx-cursor: hand;");
        reqBtn.setOnAction(e -> showInitiateDialog());
        
        actionCard.getChildren().addAll(acT, acD, reqBtn);
        actionCol.getChildren().addAll(actTitle, actionCard);

        nocGrid.getChildren().addAll(requestsCol, actionCol);

        mainContainer.getChildren().addAll(headerTitle, nocGrid);
        
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

    private HBox createNOCRow(String dept, String type, String status, String date) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 18px; -fx-background-radius: 10px; -fx-border-color: #262626;");
        
        VBox info = new VBox(2);
        Label dT = new Label(dept);
        dT.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label tT = new Label(type + " \u2022 Filed on " + date);
        tT.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        info.getChildren().addAll(dT, tT);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        Label statusLabel = new Label(status.toUpperCase());
        String statusColor = status.equalsIgnoreCase("Accepted") ? "#2dd4bf" : (status.equalsIgnoreCase("Pending") ? "#eab308" : "#64748b");
        statusLabel.setStyle("-fx-text-fill: " + statusColor + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 12px; -fx-background-radius: 20px; -fx-border-color: " + statusColor + "44;");
        
        row.getChildren().addAll(info, s, statusLabel);
        return row;
    }

    public BorderPane getView() { return this.view; }

    public StackPane getRoot() { return this.rootPane; }

    private void showInitiateDialog() {
        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);");
        
        VBox dialog = new VBox(20);
        dialog.setMaxWidth(450);
        dialog.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: #333;");
        
        Label title = new Label("INITIATE NOC REQUEST");
        title.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 12px; -fx-letter-spacing: 1.5px;");
        
        VBox field1 = new VBox(8);
        Label l1 = new Label("Target Department"); l1.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        ChoiceBox<String> deptChoice = new ChoiceBox<>();
        deptChoice.getItems().addAll(
            "Central Library", 
            "Finance Office", 
            "Hostel Warden", 
            "Bennett University Admin", 
            "School of Engineering (SET)", 
            "School of Management (SOM)", 
            "School of Law (SOL)", 
            "Biotech Lab Facility",
            "Sports & Wellness Center"
        );
        deptChoice.setValue("Bennett University Admin");
        deptChoice.setMaxWidth(Double.MAX_VALUE);
        deptChoice.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-padding: 5px; -fx-border-color: #262626;");
        field1.getChildren().addAll(l1, deptChoice);

        VBox field2 = new VBox(8);
        Label l2 = new Label("Clearance Type"); l2.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll(
            "Semester Course Registration", 
            "Subject Elective Choice", 
            "Final Year NOC", 
            "Internship Approval", 
            "Equipment Return", 
            "Industrial Visit Permission",
            "Fee Clearance Certificate"
        );
        typeChoice.setValue("Semester Course Registration");
        typeChoice.setMaxWidth(Double.MAX_VALUE);
        typeChoice.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-padding: 5px; -fx-border-color: #262626;");
        field2.getChildren().addAll(l2, typeChoice);
        
        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER_RIGHT);
        
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-cursor: hand;");
        cancel.setOnAction(e -> rootPane.getChildren().remove(overlay));
        
        Button submit = new Button("File Request");
        submit.setStyle("-fx-background-color: #eab308; -fx-text-fill: #121212; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        submit.setOnAction(e -> {
            App.addNOCRequest(new App.NOCRequest(deptChoice.getValue(), typeChoice.getValue()));
            rootPane.getChildren().remove(overlay);
            App.showStudentNOC();
        });
        
        btns.getChildren().addAll(cancel, submit);
        dialog.getChildren().addAll(title, field1, field2, btns);
        overlay.getChildren().add(dialog);
        rootPane.getChildren().add(overlay);
    }
}

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
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import java.io.File;

public class StudentPortfolioView {
    private BorderPane view = new BorderPane();
    private StackPane rootPane = new StackPane();

    public StudentPortfolioView() {
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
            createSidebarItem("\ud83d\udcc1", "Academic Portfolios", true, () -> App.showStudentPortfolio()),
            createSidebarItem("\ud83d\udccb", "NOC Applications", false, () -> App.showStudentNOC()),
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
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        VBox titleBox = new VBox(5);
        Label title = new Label("Academic Portfolios");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subT = new Label("Showcase your verified projects and research work.");
        subT.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(title, subT);
        Region hs = new Region(); HBox.setHgrow(hs, Priority.ALWAYS);
        
        Button submitBtn = new Button("+ Submit Portfolio");
        submitBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        submitBtn.setOnAction(e -> showSubmitDialog());
        
        header.getChildren().addAll(titleBox, hs, submitBtn);

        VBox portfolioList = new VBox(20);
        for (App.Portfolio p : App.currentStudent.portfolioList) {
            portfolioList.getChildren().add(createPortfolioCard(p.title, p.description, p.status, p.submissionDate));
        }

        mainContainer.getChildren().addAll(header, portfolioList);
        
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

    private VBox createPortfolioCard(String title, String desc, String status, String meta) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 25px; -fx-background-radius: 12px; -fx-border-color: #262626;");
        
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        Label statusLabel = new Label(status.toUpperCase());
        String statusColor = status.equalsIgnoreCase("Accepted") ? "#2dd4bf" : (status.equalsIgnoreCase("Pending") ? "#eab308" : "#64748b");
        statusLabel.setStyle("-fx-background-color: " + statusColor + "22; -fx-text-fill: " + statusColor + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        
        top.getChildren().addAll(t, s, statusLabel);
        
        Label d = new Label(desc);
        d.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");
        d.setWrapText(true);
        
        Label m = new Label(meta);
        m.setStyle("-fx-text-fill: #444; -fx-font-size: 11px; -fx-font-style: italic;");
        
        card.getChildren().addAll(top, d, m);
        return card;
    }

    public BorderPane getView() { return this.view; }

    public StackPane getRoot() { return this.rootPane; }

    private File selectedDocument = null;

    private void showSubmitDialog() {
        selectedDocument = null;
        VBox overlay = new VBox();
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.85);");
        
        VBox dialog = new VBox(20);
        dialog.setMaxWidth(500);
        dialog.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 30px; -fx-background-radius: 15px; -fx-border-color: #333;");
        
        Label title = new Label("SUBMIT NEW PORTFOLIO");
        title.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 12px; -fx-letter-spacing: 1.5px;");
        
        javafx.scene.control.TextField titleIn = new javafx.scene.control.TextField();
        titleIn.setPromptText("Project Title");
        titleIn.setStyle("-fx-background-color: #121212; -fx-text-fill: white; -fx-padding: 12px; -fx-background-radius: 8px; -fx-border-color: #262626;");
        
        TextArea descIn = new TextArea();
        descIn.setPromptText("Project Description and Key Contributions...");
        descIn.setPrefHeight(120);
        descIn.setWrapText(true);
        descIn.setStyle("-fx-control-inner-background: #121212; -fx-text-fill: white; -fx-background-radius: 8px;");

        VBox fileBox = new VBox(10);
        Label fileStatus = new Label("No document attached");
        fileStatus.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        
        Button uploadBtn = new Button("\ud83d\udcc2 Attach Document (PDF/DOCX/JPG)");
        uploadBtn.setStyle("-fx-background-color: #262626; -fx-text-fill: #eab308; -fx-font-size: 12px; -fx-padding: 8px 15px; -fx-background-radius: 5px; -fx-cursor: hand;");
        uploadBtn.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select Portfolio Document");
            fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Support Files", "*.pdf", "*.docx", "*.jpg", "*.png", "*.zip")
            );
            File selected = fc.showOpenDialog(null);
            if (selected != null) {
                selectedDocument = selected;
                fileStatus.setText("Attached: " + selected.getName());
                fileStatus.setStyle("-fx-text-fill: #2dd4bf; -fx-font-size: 11px;");
            }
        });
        fileBox.getChildren().addAll(uploadBtn, fileStatus);
        
        HBox btns = new HBox(15);
        btns.setAlignment(Pos.CENTER_RIGHT);
        
        Button cancel = new Button("Cancel");
        cancel.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-cursor: hand;");
        cancel.setOnAction(e -> rootPane.getChildren().remove(overlay));
        
        Button submit = new Button("Submit for Validation");
        submit.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand;");
        submit.setOnAction(e -> {
            if (!titleIn.getText().isEmpty()) {
                App.Portfolio p = new App.Portfolio(titleIn.getText(), descIn.getText());
                if (selectedDocument != null) {
                    p.documentName = selectedDocument.getName();
                    p.documentPath = App.saveDocument(selectedDocument);
                }
                App.addPortfolio(p);
                rootPane.getChildren().remove(overlay);
                App.showStudentPortfolio();
            }
        });
        
        btns.getChildren().addAll(cancel, submit);
        dialog.getChildren().addAll(title, titleIn, descIn, fileBox, btns);
        overlay.getChildren().add(dialog);
        rootPane.getChildren().add(overlay);
    }
}

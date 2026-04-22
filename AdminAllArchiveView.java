package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminAllArchiveView {
    private VBox view = new VBox();

    public AdminAllArchiveView() {
        this.view.setStyle("-fx-background-color: #121212; -fx-font-family: 'Segoe UI', sans-serif;");
        this.view.setAlignment(Pos.TOP_CENTER);

        VBox container = new VBox(25);
        container.setPadding(new Insets(30));
        container.setMaxWidth(480);

        Label backBtn = new Label("\u2190 Back to Vault");
        backBtn.setStyle("-fx-text-fill: #eab308; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        makeClickable(backBtn, () -> App.showAdminDashboard());

        Label title = new Label("Sovereign Archive");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 24px; -fx-font-weight: 900;");

        HBox searchBar = new HBox(10);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 12px; -fx-background-radius: 8px;");
        TextField sInput = new TextField();
        sInput.setPromptText("Filter by name, ID or batch...");
        sInput.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-prompt-text-fill: #555;");
        HBox.setHgrow(sInput, Priority.ALWAYS);
        searchBar.getChildren().addAll(new Label("\ud83d\udd0d"), sInput);
        
        Label filterBtn = new Label("\u2699");
        filterBtn.setStyle("-fx-text-fill: #eab308; -fx-font-size: 18px; -fx-cursor: hand;");
        filterBtn.setOnMouseClicked(e -> App.showAdminFilter());
        searchBar.getChildren().add(filterBtn);

        VBox list = new VBox(12);
        
        sInput.textProperty().addListener((obs, oldV, newV) -> {
            renderArchiveList(list, newV.trim().toLowerCase());
        });
        
        renderArchiveList(list, "");

        container.getChildren().addAll(backBtn, title, searchBar, list);

        ScrollPane scroll = new ScrollPane(container);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background: #121212; -fx-background-insets: 0;");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        StackPane centeredWrapper = new StackPane(scroll);
        centeredWrapper.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(centeredWrapper, Priority.ALWAYS);
        
        this.view.getChildren().add(centeredWrapper);
    }

    private HBox createArchiveRow(String name, String batch, String id) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 15px; -fx-background-radius: 10px; -fx-cursor: hand;");
        
        VBox texts = new VBox(2);
        Label n = new Label(name); n.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label b = new Label(batch + " \u2022 ID: " + id); b.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 11px;");
        texts.getChildren().addAll(n, b);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        
        Label star = new Label(App.isShortlisted(id) ? "\u2605" : "\u2606");
        star.setStyle("-fx-text-fill: #eab308; -fx-font-size: 18px; -fx-cursor: hand; -fx-padding: 0 5px 0 0;");
        star.setOnMouseClicked(e -> {
            App.toggleShortlist(id);
            star.setText(App.isShortlisted(id) ? "\u2605" : "\u2606");
            e.consume();
        });
        
        Label msgIcon = new Label("\ud83d\udcac");
        msgIcon.setStyle("-fx-text-fill: #eab308; -fx-font-size: 16px; -fx-cursor: hand; -fx-padding: 0 10px 0 0;");
        msgIcon.setOnMouseClicked(e -> {
            App.showAdminChatDetail(name);
            e.consume();
        });
        
        Label arrow = new Label("\u203a"); arrow.setStyle("-fx-text-fill: #eab308; -fx-font-size: 20px;");
        
        row.getChildren().addAll(texts, s, star, msgIcon, arrow);
        makeClickable(row, () -> App.showAdminStudentProfile(name, "Archive Student", id));
        return row;
    }

    private void makeClickable(Node node, Runnable action) {
        node.setCursor(Cursor.HAND);
        node.setOnMouseClicked(e -> action.run());
    }

    public VBox getView() { return this.view; }

    private void renderArchiveList(VBox container, String query) {
        container.getChildren().clear();
        boolean found = false;
        
        // Search in student directory
        for (App.StudentProfile s : App.studentDB.values()) {
            boolean matches = query.isEmpty() || 
                             (s.name != null && s.name.toLowerCase().contains(query)) || 
                             (s.studentId != null && s.studentId.toLowerCase().contains(query));
            
            if (matches) {
                String batch = (s.yearOfStudy != null ? "Batch: " + s.yearOfStudy : "Verified Student");
                container.getChildren().add(createArchiveRow(s.name, batch, s.studentId));
                found = true;
            }
        }

        if (!found) {
            Label noRes = new Label("No archive records found for '" + query + "'");
            noRes.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px; -fx-padding: 20px;");
            container.getChildren().add(noRes);
        }
    }
}

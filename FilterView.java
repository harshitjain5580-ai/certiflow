
package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FilterView {
    private VBox view = new VBox();
    private Runnable backAction;
    private java.util.Map<String, TextField> fieldMap = new java.util.HashMap<>();

    public FilterView(Runnable backAction) {
        this.backAction = backAction;
        this.view.setStyle("-fx-background-color: #121212; -fx-font-family: 'Se
        goe UI', sans-serif;");
        this.view.setAlignment(Pos.TOP_CENTER);

        VBox container = new VBox(25);
        container.setPadding(new Insets(30, 20, 60, 20));
        container.setMaxWidth(600);

        // --- Top Bar ---
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        Label backBtn = new Label("\u2190 Return to Viewer");
        backBtn.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        makeClickable(backBtn, backAction);
        topBar.getChildren().add(backBtn);

        // --- Header ---
        VBox header = new VBox(5);
        Label subT = new Label("UNIVERSAL FILTER CONSOLE");
        subT.setStyle("-fx-text-fill: " + App.getAccentColor() + "; -fx-font-weight: bold; -fx-font-size: 10px; -fx-letter-spacing: 2px;");
        Label title = new Label("Search Parameters");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 28px; -fx-font-weight: 900;");
        header.getChildren().addAll(subT, title);

        // --- Categories ---
        VBox fieldsBox = new VBox(30);
        
        // Category 1: Academic Excellence (5 Fields)
        fieldsBox.getChildren().add(createCategory("Academic Excellence", new String[]{
            "Minimum GPA", "Department", "Credits Completed", "Research Interest", "Faculty Advisor"
        }));

        // Category 2: Performance Metrics (6 Fields)
        fieldsBox.getChildren().add(createCategory("Performance Metrics", new String[]{
            "Internship Tier", "Technical Skill", "Innovation Score", "Certification Level", "Soft Skills", "Hackathon Wins"
        }));

        // Category 3: Project & Code (6 Fields)
        fieldsBox.getChildren().add(createCategory("Project & Code Architecture", new String[]{
            "Github ID", "Project Complexity", "Tech Stack used", "Deployment State", "System Design", "Audit Reports"
        }));

        // Category 4: Demographics (5 Fields)
        fieldsBox.getChildren().add(createCategory("Record Identity", new String[]{
            "Student ID", "Nationality", "Enrollment Batch", "Current Residency", "Housing Node"
        }));

        // --- Apply Button ---
        Label applyBtn = new Label("APPLY SEARCH PARAMETERS");
        applyBtn.setMaxWidth(Double.MAX_VALUE);
        applyBtn.setAlignment(Pos.CENTER);
        applyBtn.setStyle("-fx-background-color: " + App.getAccentColor() + "; -fx-text-fill: #121212; -fx-padding: 16px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-font-size: 14px;");
        makeClickable(applyBtn, () -> {
            App.globalFilters.clear();
            for (String key : fieldMap.keySet()) {
                String val = fieldMap.get(key).getText().trim();
                if (!val.isEmpty()) App.globalFilters.put(key, val);
            }
            backAction.run();
        });

        Label resetBtn = new Label("Reset All Console Fields");
        resetBtn.setPadding(new Insets(10, 0, 0, 0));
        resetBtn.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 11px; -fx-cursor: hand; -fx-underline: true;");
        resetBtn.setAlignment(Pos.CENTER);
        makeClickable(resetBtn, () -> {
            App.globalSearchQuery = "";
            App.globalFilters.clear();
            App.showFilterView(backAction);
        });

        container.getChildren().addAll(topBar, header, fieldsBox, applyBtn, resetBtn);

        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-background: #121212;");
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        StackPane centeredWrapper = new StackPane(sp);
        centeredWrapper.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(centeredWrapper, Priority.ALWAYS);
        
        this.view.getChildren().add(centeredWrapper);
    }

    private VBox createCategory(String name, String[] fields) {
        VBox cat = new VBox(15);
        Label title = new Label(name.toUpperCase());
        title.setStyle("-fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);

        for (int i = 0; i < fields.length; i++) {
            VBox fieldBox = new VBox(5);
            Label l = new Label(fields[i]);
            l.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 10px;");
            TextField tf = new TextField();
            tf.setPromptText("Enter Value...");
            if (App.globalFilters.containsKey(fields[i]))
                tf.setText(App.globalFilters.get(fields[i]));
            tf.setStyle(
                    "-fx-background-color: #1a1a1a; -fx-text-fill: white; -fx-padding: 8px; -fx-background-radius: 5px; -fx-border-color: #333; -fx-border-radius: 5px;");
            fieldBox.getChildren().addAll(l, tf);
            fieldMap.put(fields[i], tf);

            grid.add(fieldBox, i % 2, i / 2);
            GridPane.setHgrow(fieldBox, Priority.ALWAYS);
        }

        cat.getChildren().addAll(title, grid);
        return cat;
    }

    private void makeClickable(Node node, Runnable action) {
        node.setCursor(Cursor.HAND);
        node.setOnMouseClicked(e -> action.run());
    }

    public VBox getView() {
        return this.view;
    }
}

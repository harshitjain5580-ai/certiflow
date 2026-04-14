/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Cursor
 *  javafx.scene.Node
 *  javafx.scene.control.Label
 *  javafx.scene.control.ScrollPane
 *  javafx.scene.control.ScrollPane$ScrollBarPolicy
 *  javafx.scene.control.TextField
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.VBox
 */
package com.certiflow;

import com.certiflow.App;
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
import javafx.scene.layout.VBox;

public class TeacherStudentListView {
    private VBox view = new VBox();

    public TeacherStudentListView() {
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"dashboard-bg");
        VBox container = new VBox(20.0);
        container.getStyleClass().add((Object)"dashboard-container");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(20.0));
        HBox header = new HBox(12.0);
        header.setAlignment(Pos.CENTER_LEFT);
        Label backIcon = new Label("\u2190");
        backIcon.getStyleClass().add((Object)"teacher-back-btn");
        backIcon.setCursor(Cursor.HAND);
        backIcon.setOnMouseClicked(e -> App.showTeacherHome());
        Label title = new Label("Student Roster");
        title.setStyle("-fx-text-fill: #fac736; -fx-font-size: 20px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        header.getChildren().addAll((Object[])new Node[]{backIcon, title, spacer});
        HBox searchContainer = new HBox(10.0);
        searchContainer.getStyleClass().add((Object)"teacher-search-bar");
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        Label searchIcon = new Label("\ud83d\udd0d");
        searchIcon.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 16px;");
        TextField searchField = new TextField();
        searchField.setPromptText("Search students by name or ID...");
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff; -fx-prompt-text-fill: #666666;");
        HBox.setHgrow((Node)searchField, (Priority)Priority.ALWAYS);
        searchContainer.getChildren().addAll((Object[])new Node[]{searchIcon, searchField});
        VBox rosterContainer = new VBox(10.0);
        rosterContainer.getStyleClass().add((Object)"student-list-container");
        Label listHeader = new Label("All students");
        listHeader.getStyleClass().add((Object)"student-list-header");
        rosterContainer.getChildren().add((Object)listHeader);
        rosterContainer.getChildren().addAll((Object[])new Node[]{this.createStudentItem("Marcus Johnson", "B.Sc Computer Science", "#STU-9821"), this.createStudentItem("Elena Rodriguez", "M.A. Digital Arts", "#STU-9854"), this.createStudentItem("Jameson Miller", "B.A. Graphic Design", "#STU-9877"), this.createStudentItem("David Chen", "B.Sc Economics", "#STU-9882"), this.createStudentItem("Sarah Jenkins", "Ph.D Literature", "#STU-9901")});
        ScrollPane scrollPane = new ScrollPane((Node)rosterContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add((Object)"dashboard-scroll-pane");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox scrollContentContainer = new VBox(new Node[]{header, searchContainer, scrollPane});
        scrollContentContainer.setSpacing(20.0);
        VBox.setVgrow((Node)scrollContentContainer, (Priority)Priority.ALWAYS);
        container.getChildren().add((Object)scrollContentContainer);
        this.view.getChildren().add((Object)container);
    }

    private VBox createStudentItem(String name, String course, String id) {
        VBox itemBox = new VBox(5.0);
        itemBox.getStyleClass().add((Object)"student-list-item");
        HBox contentBox = new HBox(10.0);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        VBox infoBox = new VBox(2.0);
        Label nameLbl = new Label(name);
        nameLbl.getStyleClass().add((Object)"student-name");
        Label courseLbl = new Label(course);
        courseLbl.getStyleClass().add((Object)"student-course");
        infoBox.getChildren().addAll((Object[])new Node[]{nameLbl, courseLbl});
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label idLbl = new Label(id);
        idLbl.getStyleClass().add((Object)"student-id");
        contentBox.getChildren().addAll((Object[])new Node[]{infoBox, spacer, idLbl});
        itemBox.getChildren().add((Object)contentBox);
        return itemBox;
    }

    public VBox getView() {
        return this.view;
    }
}

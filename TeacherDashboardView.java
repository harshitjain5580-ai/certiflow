/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Cursor
 *  javafx.scene.Node
 *  javafx.scene.control.Button
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TeacherDashboardView {
    private VBox view = new VBox();

    public TeacherDashboardView() {
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
        backIcon.setOnMouseClicked(e -> App.showOptionView());
        Label title = new Label("Student NOC Requests");
        title.getStyleClass().add((Object)"dashboard-title");
        title.setStyle("-fx-text-fill: #ffffff;");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label bellIcon = new Label("\ud83d\udd14");
        bellIcon.getStyleClass().add((Object)"bell-icon");
        bellIcon.setStyle("-fx-text-fill: #d4af37;");
        header.getChildren().addAll((Object[])new Node[]{backIcon, title, spacer, bellIcon});
        HBox searchContainer = new HBox(10.0);
        searchContainer.getStyleClass().add((Object)"teacher-search-bar");
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        Label searchIcon = new Label("\ud83d\udd0d");
        searchIcon.getStyleClass().add((Object)"search-icon");
        searchIcon.setStyle("-fx-text-fill: #d4af37;");
        TextField searchField = new TextField();
        searchField.setPromptText("Search by student name or ID...");
        searchField.getStyleClass().add((Object)"search-input");
        HBox.setHgrow((Node)searchField, (Priority)Priority.ALWAYS);
        searchContainer.getChildren().addAll((Object[])new Node[]{searchIcon, searchField});
        HBox sectionHeader = new HBox(10.0);
        sectionHeader.setAlignment(Pos.CENTER_LEFT);
        sectionHeader.setPadding(new Insets(10.0, 0.0, 5.0, 0.0));
        Label pendingLbl = new Label("PENDING APPROVALS");
        pendingLbl.getStyleClass().add((Object)"pending-approvals-title");
        Region spacer2 = new Region();
        HBox.setHgrow((Node)spacer2, (Priority)Priority.ALWAYS);
        Label newBadge = new Label("4 NEW");
        newBadge.getStyleClass().add((Object)"new-badge");
        sectionHeader.getChildren().addAll((Object[])new Node[]{pendingLbl, spacer2, newBadge});
        VBox requestsList = new VBox(15.0);
        requestsList.getChildren().addAll((Object[])new Node[]{this.createRequestCard("SJ", "Library Clearance", "Sarah Jenkins", "Requested on Oct 24, 2023", "#NOC-8821"), this.createRequestCard("MT", "Laboratory Access", "Mark Thompson", "Requested on Oct 25, 2023", "#NOC-8845"), this.createRequestCard("DM", "Sports Equipment", "David Miller", "Requested on Oct 26, 2023", "#NOC-8849")});
        ScrollPane scrollPane = new ScrollPane((Node)requestsList);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add((Object)"dashboard-scroll-pane");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox scrollContent = new VBox(20.0);
        scrollContent.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
        scrollContent.getChildren().addAll((Object[])new Node[]{header, searchContainer, sectionHeader, scrollPane});
        VBox scrollContentContainer = new VBox(new Node[]{scrollContent});
        VBox.setVgrow((Node)scrollContentContainer, (Priority)Priority.ALWAYS);
        HBox bottomNav = new HBox();
        bottomNav.getStyleClass().add((Object)"bottom-nav");
        bottomNav.setAlignment(Pos.CENTER);
        VBox homeNav = this.createNavItem("\ud83c\udfe0", "Home", false);
        homeNav.setOnMouseClicked(e -> App.showTeacherHome());
        VBox reqNav = this.createNavItem("\ud83d\udccb", "Requests", true);
        VBox histNav = this.createNavItem("\u23f2", "History", false);
        histNav.setOnMouseClicked(e -> App.showTeacherHistory());
        VBox profNav = this.createNavItem("\ud83d\udc64", "Profile", false);
        profNav.setOnMouseClicked(e -> App.showTeacherProfile());
        bottomNav.getChildren().addAll((Object[])new Node[]{homeNav, this.createSpacer(), reqNav, this.createSpacer(), histNav, this.createSpacer(), profNav});
        container.getChildren().addAll((Object[])new Node[]{scrollContentContainer, bottomNav});
        this.view.getChildren().add((Object)container);
    }

    private Region createSpacer() {
        Region r = new Region();
        HBox.setHgrow((Node)r, (Priority)Priority.ALWAYS);
        return r;
    }

    private VBox createRequestCard(String initials, String type, String name, String date, String id) {
        VBox card = new VBox(15.0);
        card.getStyleClass().add((Object)"noc-card");
        HBox topRow = new HBox(15.0);
        topRow.setAlignment(Pos.CENTER_LEFT);
        Label avatar = new Label(initials);
        avatar.getStyleClass().add((Object)"noc-avatar");
        VBox infoBox = new VBox(4.0);
        Label typeLbl = new Label(type);
        typeLbl.getStyleClass().add((Object)"noc-type");
        Label nameLbl = new Label(name);
        nameLbl.getStyleClass().add((Object)"noc-name");
        Label dateLbl = new Label(date);
        dateLbl.getStyleClass().add((Object)"noc-date");
        infoBox.getChildren().addAll((Object[])new Node[]{typeLbl, nameLbl, dateLbl});
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label idBadge = new Label(id);
        idBadge.getStyleClass().add((Object)"noc-id-badge");
        VBox rightBox = new VBox(new Node[]{idBadge});
        rightBox.setAlignment(Pos.TOP_RIGHT);
        topRow.getChildren().addAll((Object[])new Node[]{avatar, infoBox, spacer, rightBox});
        HBox btnRow = new HBox(15.0);
        btnRow.setAlignment(Pos.CENTER);
        Button approveBtn = new Button("APPROVE");
        approveBtn.getStyleClass().add((Object)"noc-btn-approve");
        approveBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow((Node)approveBtn, (Priority)Priority.ALWAYS);
        Button rejectBtn = new Button("REJECT");
        rejectBtn.getStyleClass().add((Object)"noc-btn-reject");
        rejectBtn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow((Node)rejectBtn, (Priority)Priority.ALWAYS);
        btnRow.getChildren().addAll((Object[])new Node[]{approveBtn, rejectBtn});
        card.getChildren().addAll((Object[])new Node[]{topRow, btnRow});
        return card;
    }

    private VBox createNavItem(String emoji, String text, boolean active) {
        VBox box = new VBox(5.0);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);
        Label icon = new Label(emoji);
        icon.getStyleClass().add((Object)(active ? "nav-icon-active" : "nav-icon"));
        Label lbl = new Label(text);
        lbl.getStyleClass().add((Object)(active ? "nav-text-active" : "nav-text"));
        box.getChildren().addAll((Object[])new Node[]{icon, lbl});
        return box;
    }

    public VBox getView() {
        return this.view;
    }
}

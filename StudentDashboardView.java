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
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.VBox
 */
package com.certiflow;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class StudentDashboardView {
    private VBox view = new VBox();

    public StudentDashboardView() {
        ImageView titleNode;
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"dashboard-bg");
        VBox container = new VBox(20.0);
        container.getStyleClass().add((Object)"dashboard-container");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(20.0));
        HBox header = new HBox(12.0);
        header.setAlignment(Pos.CENTER_LEFT);
        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitHeight(35.0);
            logoView.setPreserveRatio(true);
            titleNode = logoView;
        } else {
            HBox fallbackBox = new HBox(8.0);
            fallbackBox.setAlignment(Pos.CENTER_LEFT);
            Label logoIcon = new Label("\u2714");
            logoIcon.getStyleClass().add((Object)"logo-icon");
            Label title = new Label("Certiflow");
            title.getStyleClass().add((Object)"dashboard-title");
            fallbackBox.getChildren().addAll((Object[])new Node[]{logoIcon, title});
            titleNode = fallbackBox;
        }
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label bellIcon = new Label("\ud83d\udd14");
        bellIcon.getStyleClass().add((Object)"bell-icon");
        header.getChildren().addAll((Object[])new Node[]{titleNode, spacer, bellIcon});
        HBox searchContainer = new HBox(10.0);
        searchContainer.getStyleClass().add((Object)"search-bar");
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        Label searchIcon = new Label("\ud83d\udd0d");
        searchIcon.getStyleClass().add((Object)"search-icon");
        TextField searchField = new TextField();
        searchField.setPromptText("Search documents or portfolios");
        searchField.getStyleClass().add((Object)"search-input");
        HBox.setHgrow((Node)searchField, (Priority)Priority.ALWAYS);
        searchContainer.getChildren().addAll((Object[])new Node[]{searchIcon, searchField});
        Label quickActionsLbl = new Label("Quick Actions");
        quickActionsLbl.getStyleClass().add((Object)"section-title");
        HBox quickActions = new HBox(15.0);
        Button portfolioBtn = this.createQuickAction("\ud83d\udcc1", "Portfolios");
        Button nocBtn = this.createQuickAction("\ud83d\udcc4", "NOC Form");
        quickActions.getChildren().addAll((Object[])new Node[]{portfolioBtn, nocBtn});
        Label statusLbl = new Label("Status Overview");
        statusLbl.getStyleClass().add((Object)"section-title");
        HBox statusOverview = new HBox(12.0);
        statusOverview.getChildren().addAll((Object[])new Node[]{this.createStatusCard("24", "ACCEPTED", "status-accepted-text"), this.createStatusCard("12", "PENDING", "status-pending-text"), this.createStatusCard("08", "REVIEW", "status-review-text")});
        HBox recentHeader = new HBox();
        recentHeader.setAlignment(Pos.BASELINE_LEFT);
        Label recentLbl = new Label("Recent NOC Applications");
        recentLbl.getStyleClass().add((Object)"section-title");
        Region spacer2 = new Region();
        HBox.setHgrow((Node)spacer2, (Priority)Priority.ALWAYS);
        Label viewAllLbl = new Label("View All");
        viewAllLbl.getStyleClass().add((Object)"view-all-text");
        recentHeader.getChildren().addAll((Object[])new Node[]{recentLbl, spacer2, viewAllLbl});
        VBox recentList = new VBox(15.0);
        recentList.setAlignment(Pos.CENTER);
        recentList.setPadding(new Insets(20.0));
        Label emptyStateLbl = new Label("No recent applications found");
        emptyStateLbl.getStyleClass().add((Object)"recent-card-id");
        recentList.getChildren().add((Object)emptyStateLbl);
        VBox scrollContent = new VBox(25.0);
        scrollContent.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
        scrollContent.getChildren().addAll((Object[])new Node[]{header, searchContainer, quickActionsLbl, quickActions, statusLbl, statusOverview, recentHeader, recentList});
        ScrollPane scrollPane = new ScrollPane((Node)scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add((Object)"dashboard-scroll-pane");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        HBox bottomNav = new HBox();
        bottomNav.getStyleClass().add((Object)"bottom-nav");
        bottomNav.setAlignment(Pos.CENTER);
        VBox homeNav = this.createNavItem("\ud83c\udfe0", "Home", true);
        VBox portNav = this.createNavItem("\ud83d\udcc1", "Portfolios", false);
        VBox nocNav = this.createNavItem("\ud83d\udccb", "NOC", false);
        VBox profNav = this.createNavItem("\ud83d\udc64", "Profile", false);
        bottomNav.getChildren().addAll((Object[])new Node[]{homeNav, this.createSpacer(), portNav, this.createSpacer(), nocNav, this.createSpacer(), profNav});
        container.getChildren().addAll((Object[])new Node[]{scrollPane, bottomNav});
        VBox.setVgrow((Node)scrollPane, (Priority)Priority.ALWAYS);
        this.view.getChildren().add((Object)container);
    }

    private Region createSpacer() {
        Region r = new Region();
        HBox.setHgrow((Node)r, (Priority)Priority.ALWAYS);
        return r;
    }

    private Button createQuickAction(String emoji, String text) {
        Button btn = new Button();
        btn.getStyleClass().add((Object)"quick-action-btn");
        HBox box = new HBox(12.0);
        box.setAlignment(Pos.CENTER_LEFT);
        Label icon = new Label(emoji);
        icon.getStyleClass().add((Object)"quick-action-icon");
        Label lbl = new Label(text);
        lbl.getStyleClass().add((Object)"quick-action-text");
        box.getChildren().addAll((Object[])new Node[]{icon, lbl});
        btn.setGraphic((Node)box);
        btn.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow((Node)btn, (Priority)Priority.ALWAYS);
        return btn;
    }

    private VBox createStatusCard(String number, String label, String numberClass) {
        VBox card = new VBox(8.0);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add((Object)"status-card");
        Label numLbl = new Label(number);
        numLbl.getStyleClass().addAll((Object[])new String[]{"status-card-number", numberClass});
        Label textLbl = new Label(label);
        textLbl.getStyleClass().add((Object)"status-card-text");
        card.getChildren().addAll((Object[])new Node[]{numLbl, textLbl});
        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow((Node)card, (Priority)Priority.ALWAYS);
        return card;
    }

    private HBox createRecentCard(String emoji, String title, String id, String status, String time, String statusClass) {
        HBox card = new HBox(15.0);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add((Object)"recent-card");
        Label icon = new Label(emoji);
        icon.getStyleClass().add((Object)"recent-card-icon");
        VBox details = new VBox(6.0);
        Label titleLbl = new Label(title);
        titleLbl.getStyleClass().add((Object)"recent-card-title");
        Label idLbl = new Label("ID: " + id);
        idLbl.getStyleClass().add((Object)"recent-card-id");
        details.getChildren().addAll((Object[])new Node[]{titleLbl, idLbl});
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        VBox statusBox = new VBox(8.0);
        statusBox.setAlignment(Pos.CENTER_RIGHT);
        Label statusLbl = new Label(status);
        statusLbl.getStyleClass().addAll((Object[])new String[]{"status-pill", statusClass});
        Label timeLbl = new Label(time);
        timeLbl.getStyleClass().add((Object)"recent-card-time");
        statusBox.getChildren().addAll((Object[])new Node[]{statusLbl, timeLbl});
        card.getChildren().addAll((Object[])new Node[]{icon, details, spacer, statusBox});
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

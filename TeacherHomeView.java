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
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.VBox
 *  javafx.scene.shape.Circle
 */
package com.certiflow;

import com.certiflow.App;
import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class TeacherHomeView {
    private VBox view = new VBox();

    public TeacherHomeView() {
        ImageView titleNode;
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"dashboard-bg");
        VBox container = new VBox(20.0);
        container.getStyleClass().add((Object)"dashboard-container");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(20.0));
        HBox topHeader = new HBox(15.0);
        topHeader.setAlignment(Pos.CENTER_LEFT);
        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitHeight(35.0);
            logoView.setPreserveRatio(true);
            titleNode = logoView;
        } else {
            HBox fallbackBox = new HBox(8.0);
            fallbackBox.setAlignment(Pos.CENTER_LEFT);
            Label logoIconBox = new Label("\u2611");
            logoIconBox.getStyleClass().add((Object)"logo-icon");
            Label appName = new Label("Certiflow");
            appName.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
            fallbackBox.getChildren().addAll((Object[])new Node[]{logoIconBox, appName});
            titleNode = fallbackBox;
        }
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label bellIcon = new Label("\ud83d\udd14");
        bellIcon.getStyleClass().add((Object)"bell-icon");
        Circle profilePic = new Circle(15.0);
        profilePic.setStyle("-fx-fill: #fac736;");
        topHeader.getChildren().addAll((Object[])new Node[]{titleNode, spacer, bellIcon, profilePic});
        VBox welcomeSection = new VBox(5.0);
        welcomeSection.setPadding(new Insets(20.0, 0.0, 10.0, 0.0));
        Label welcomeText = new Label("Welcome back,");
        welcomeText.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
        Label nameText = new Label("Professor Sarah");
        nameText.setStyle("-fx-text-fill: #fac736; -fx-font-size: 28px; -fx-font-weight: bold;");
        Label subText = new Label("Manage your student certifications and\npending validations.");
        subText.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");
        welcomeSection.getChildren().addAll((Object[])new Node[]{welcomeText, nameText, subText});
        HBox summaryCards = new HBox(15.0);
        VBox pendingCard = this.createSummaryCard("\ud83d\udccb", "Pending Today", "12", "+5%", true);
        VBox totalCard = this.createSummaryCard("\u2611", "Weekly Total", "148", "+12%", true);
        HBox.setHgrow((Node)pendingCard, (Priority)Priority.ALWAYS);
        HBox.setHgrow((Node)totalCard, (Priority)Priority.ALWAYS);
        summaryCards.getChildren().addAll((Object[])new Node[]{pendingCard, totalCard});
        VBox quickActions = new VBox(15.0);
        quickActions.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        Label quickActionTitle = new Label("Quick Actions");
        quickActionTitle.getStyleClass().add((Object)"section-title");
        HBox action1 = this.createQuickAction("\ud83d\udcbb", "Validate New Request", true);
        action1.setCursor(Cursor.HAND);
        action1.setOnMouseClicked(e -> App.showTeacherDashboard());
        HBox action2 = this.createQuickAction("\ud83d\udc65", "View Student List", false);
        action2.setCursor(Cursor.HAND);
        action2.setOnMouseClicked(e -> App.showTeacherStudentList());
        HBox action3 = this.createQuickAction("\ud83d\udcca", "Export Weekly Report", false);
        action3.setCursor(Cursor.HAND);
        action3.setOnMouseClicked(e -> App.showTeacherReportExport());
        quickActions.getChildren().addAll((Object[])new Node[]{quickActionTitle, action1, action2, action3});
        VBox recentActivity = new VBox(15.0);
        recentActivity.setPadding(new Insets(10.0, 0.0, 0.0, 0.0));
        HBox recentHeader = new HBox();
        recentHeader.setAlignment(Pos.CENTER_LEFT);
        Label recTitle = new Label("Recent Activity");
        recTitle.getStyleClass().add((Object)"section-title");
        Region recSpacer = new Region();
        HBox.setHgrow((Node)recSpacer, (Priority)Priority.ALWAYS);
        Label viewAll = new Label("View All");
        viewAll.getStyleClass().add((Object)"view-all-text");
        viewAll.setCursor(Cursor.HAND);
        viewAll.setOnMouseClicked(e -> App.showTeacherHistory());
        recentHeader.getChildren().addAll((Object[])new Node[]{recTitle, recSpacer, viewAll});
        VBox activity1 = this.createActivityCard("MJ", "Marcus Johnson", "B.Sc Computer Science \u2022 Level 3", "PENDING", "2m ago");
        VBox activity2 = this.createActivityCard("ER", "Elena Rodriguez", "M.A. Digital Arts \u2022 Level 1", "APPROVED", "1h ago");
        recentActivity.getChildren().addAll((Object[])new Node[]{recentHeader, activity1, activity2});
        VBox scrollContent = new VBox(20.0);
        scrollContent.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
        scrollContent.getChildren().addAll((Object[])new Node[]{topHeader, welcomeSection, summaryCards, quickActions, recentActivity});
        ScrollPane scrollPane = new ScrollPane((Node)scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add((Object)"dashboard-scroll-pane");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox scrollContentContainer = new VBox(new Node[]{scrollPane});
        VBox.setVgrow((Node)scrollContentContainer, (Priority)Priority.ALWAYS);
        HBox bottomNav = new HBox();
        bottomNav.getStyleClass().add((Object)"bottom-nav");
        bottomNav.setAlignment(Pos.CENTER);
        VBox homeNav = this.createNavItem("\ud83c\udfe0", "Home", true);
        VBox reqNav = this.createNavItem("\ud83d\udccb", "Requests", false);
        reqNav.setOnMouseClicked(e -> App.showTeacherDashboard());
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

    private VBox createSummaryCard(String iconStr, String title, String number, String trend, boolean positive) {
        VBox card = new VBox(10.0);
        card.getStyleClass().add((Object)"summary-card");
        HBox header = new HBox(5.0);
        header.setAlignment(Pos.CENTER_LEFT);
        Label icon = new Label(iconStr);
        icon.setStyle("-fx-text-fill: #fac736;");
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-text-fill: #666666; -fx-font-weight: bold; -fx-font-size: 12px;");
        header.getChildren().addAll((Object[])new Node[]{icon, titleLbl});
        Label numLbl = new Label(number);
        numLbl.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #121212;");
        HBox trendBox = new HBox(5.0);
        trendBox.setAlignment(Pos.CENTER_LEFT);
        Label arrow = new Label(positive ? "\u2197" : "\u2198");
        arrow.setStyle(positive ? "-fx-text-fill: #2dd4bf;" : "-fx-text-fill: #ff4d4f;");
        Label trendLbl = new Label(trend);
        trendLbl.setStyle(positive ? "-fx-text-fill: #2dd4bf; -fx-font-weight: bold; -fx-font-size: 12px;" : "-fx-text-fill: #ff4d4f; -fx-font-weight: bold; -fx-font-size: 12px;");
        trendBox.getChildren().addAll((Object[])new Node[]{arrow, trendLbl});
        card.getChildren().addAll((Object[])new Node[]{header, numLbl, trendBox});
        return card;
    }

    private HBox createQuickAction(String iconStr, String text, boolean isPrimary) {
        HBox action = new HBox(15.0);
        action.setAlignment(Pos.CENTER_LEFT);
        action.getStyleClass().add((Object)(isPrimary ? "quick-action-primary" : "quick-action-secondary"));
        Label icon = new Label(iconStr);
        icon.setStyle(isPrimary ? "-fx-text-fill: #121212; -fx-font-size: 20px;" : "-fx-text-fill: #fac736; -fx-font-size: 20px;");
        Label textLbl = new Label(text);
        textLbl.setStyle(isPrimary ? "-fx-text-fill: #121212; -fx-font-weight: bold; -fx-font-size: 14px;" : "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label arrow = new Label("\u276f");
        arrow.setStyle(isPrimary ? "-fx-text-fill: #121212;" : "-fx-text-fill: white;");
        action.getChildren().addAll((Object[])new Node[]{icon, textLbl, spacer, arrow});
        return action;
    }

    private VBox createActivityCard(String initials, String name, String details, String status, String time) {
        VBox card = new VBox();
        card.getStyleClass().add((Object)"activity-card");
        HBox content = new HBox(15.0);
        content.setAlignment(Pos.CENTER_LEFT);
        Label avatar = new Label(initials);
        avatar.getStyleClass().add((Object)"activity-avatar");
        VBox infoBox = new VBox(2.0);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-text-fill: #121212; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label detailsLbl = new Label(details);
        detailsLbl.setStyle("-fx-text-fill: #666666; -fx-font-size: 12px;");
        infoBox.getChildren().addAll((Object[])new Node[]{nameLbl, detailsLbl});
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        VBox statusBox = new VBox(5.0);
        statusBox.setAlignment(Pos.TOP_RIGHT);
        Label statusLbl = new Label(status);
        if (status.equals("PENDING")) {
            statusLbl.getStyleClass().addAll((Object[])new String[]{"status-pill", "pill-review"});
        } else {
            statusLbl.getStyleClass().addAll((Object[])new String[]{"status-pill", "pill-accepted"});
        }
        Label timeLbl = new Label(time);
        timeLbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 10px;");
        statusBox.getChildren().addAll((Object[])new Node[]{statusLbl, timeLbl});
        content.getChildren().addAll((Object[])new Node[]{avatar, infoBox, spacer, statusBox});
        card.getChildren().add((Object)content);
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

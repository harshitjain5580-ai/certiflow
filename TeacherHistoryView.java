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
 *  javafx.scene.layout.StackPane
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TeacherHistoryView {
    private VBox view = new VBox();

    public TeacherHistoryView() {
        this.view.setAlignment(Pos.CENTER);
        this.view.setStyle("-fx-background-color: #f8f9fa;");
        VBox container = new VBox(18.0);
        container.setStyle("-fx-background-color: #f8f9fa;");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(24.0, 20.0, 10.0, 20.0));
        HBox header = new HBox(12.0);
        header.setAlignment(Pos.CENTER_LEFT);
        Label historyIcon = new Label("\u23f2");
        historyIcon.setStyle("-fx-background-color: #121212; -fx-text-fill: #d4af37; -fx-font-size: 22px; -fx-padding: 10px 12px; -fx-background-radius: 25px; -fx-min-width: 44px; -fx-min-height: 44px; -fx-alignment: center;");
        VBox titleBox = new VBox(2.0);
        Label title = new Label("NOC History");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #121212;");
        Label subtitle = new Label("Past request decisions");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #94a3b8;");
        titleBox.getChildren().addAll((Object[])new Node[]{title, subtitle});
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label optionsBtn = new Label("\u22ee");
        optionsBtn.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        optionsBtn.setCursor(Cursor.HAND);
        header.getChildren().addAll((Object[])new Node[]{historyIcon, titleBox, spacer, optionsBtn});
        HBox searchContainer = new HBox(10.0);
        searchContainer.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12px; -fx-border-color: #e2e8f0; -fx-border-radius: 12px; -fx-padding: 12px 16px;");
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        Label searchIcon = new Label("\ud83d\udd0d");
        searchIcon.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 16px;");
        TextField searchField = new TextField();
        searchField.setPromptText("Search student or ID...");
        searchField.setStyle("-fx-background-color: transparent; -fx-text-fill: #121212; -fx-prompt-text-fill: #94a3b8; -fx-border-width: 0; -fx-font-size: 14px;");
        HBox.setHgrow((Node)searchField, (Priority)Priority.ALWAYS);
        searchContainer.getChildren().addAll((Object[])new Node[]{searchIcon, searchField});
        HBox filters = new HBox(10.0);
        filters.setAlignment(Pos.CENTER_LEFT);
        Button allBtn = new Button("All Decisions");
        allBtn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 20px; -fx-padding: 8px 18px; -fx-cursor: hand;");
        Label approvedDot = new Label("\u2713");
        approvedDot.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 1px 4px; -fx-font-size: 10px; -fx-font-weight: bold;");
        Label approvedText = new Label("Approved");
        approvedText.setStyle("-fx-text-fill: #334155; -fx-font-size: 13px;");
        HBox approvedBox = new HBox(6.0, new Node[]{approvedDot, approvedText});
        approvedBox.setAlignment(Pos.CENTER);
        Button approvedBtn = new Button();
        approvedBtn.setGraphic((Node)approvedBox);
        approvedBtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        Label rejectedDot = new Label("\u2717");
        rejectedDot.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-background-radius: 10px; -fx-padding: 1px 4px; -fx-font-size: 10px; -fx-font-weight: bold;");
        Label rejectedText = new Label("Rejected");
        rejectedText.setStyle("-fx-text-fill: #334155; -fx-font-size: 13px;");
        HBox rejectedBox = new HBox(6.0, new Node[]{rejectedDot, rejectedText});
        rejectedBox.setAlignment(Pos.CENTER);
        Button rejectedBtn = new Button();
        rejectedBtn.setGraphic((Node)rejectedBox);
        rejectedBtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-radius: 20px; -fx-background-radius: 20px; -fx-padding: 8px 16px; -fx-cursor: hand;");
        filters.getChildren().addAll((Object[])new Node[]{allBtn, approvedBtn, rejectedBtn});
        HBox sectionHeader = new HBox();
        sectionHeader.setAlignment(Pos.CENTER_LEFT);
        sectionHeader.setPadding(new Insets(5.0, 0.0, 0.0, 0.0));
        Label secTitle = new Label("RECENT DECISIONS");
        secTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #64748b;");
        Region secSpacer = new Region();
        HBox.setHgrow((Node)secSpacer, (Priority)Priority.ALWAYS);
        Label resCount = new Label("Showing 24 results");
        resCount.setStyle("-fx-font-size: 12px; -fx-text-fill: #94a3b8;");
        sectionHeader.getChildren().addAll((Object[])new Node[]{secTitle, secSpacer, resCount});
        VBox decisionsList = new VBox(12.0);
        decisionsList.getChildren().add((Object)sectionHeader);
        decisionsList.getChildren().addAll((Object[])new Node[]{this.createDecisionCard("MW", "Marcus Wright", "ID: #NOC-78291 \u2022 CompSci", "Decided on Oct 24, 2023", true, "Full Access"), this.createDecisionCard("ER", "Elena Rodriguez", "ID: #NOC-78285 \u2022 Engineering", "Decided on Oct 23, 2023", false, "GPA Threshold")});
        Label dateHeader = new Label("October 20, 2023");
        dateHeader.setStyle("-fx-font-size: 13px; -fx-text-fill: #d4af37; -fx-padding: 8px 0 0 0;");
        decisionsList.getChildren().add((Object)dateHeader);
        decisionsList.getChildren().addAll((Object[])new Node[]{this.createDecisionCard("JM", "Jameson Miller", "ID: #NOC-78244 \u2022 Arts & Design", "Decided on Oct 20, 2023", true, "Exhibition"), this.createDecisionCard("DC", "David Chen", "ID: #NOC-78239 \u2022 Economics", "Decided on Oct 18, 2023", false, "Missing Docs")});
        Button loadOlder = new Button("Load older decisions");
        loadOlder.setMaxWidth(Double.MAX_VALUE);
        loadOlder.setStyle("-fx-background-color: transparent; -fx-border-color: #cbd5e1; -fx-border-style: dashed; -fx-border-radius: 10px; -fx-text-fill: #64748b; -fx-font-size: 13px; -fx-padding: 14px; -fx-cursor: hand;");
        decisionsList.getChildren().add((Object)loadOlder);
        ScrollPane scrollPane = new ScrollPane((Node)decisionsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow((Node)scrollPane, (Priority)Priority.ALWAYS);
        HBox bottomNav = new HBox();
        bottomNav.setStyle("-fx-background-color: #ffffff; -fx-padding: 12px 0 8px 0; -fx-border-color: #e2e8f0; -fx-border-width: 1px 0 0 0;");
        bottomNav.setAlignment(Pos.CENTER);
        VBox homeNav = this.createNavItem("\ud83c\udfe0", "HOME", false);
        homeNav.setOnMouseClicked(e -> App.showTeacherHome());
        VBox reqNav = this.createNavItem("\ud83d\udccb", "REQUESTS", false);
        reqNav.setOnMouseClicked(e -> App.showTeacherDashboard());
        VBox histNav = this.createNavItem("\u23f2", "HISTORY", true);
        VBox profNav = this.createNavItem("\ud83d\udc64", "PROFILE", false);
        bottomNav.getChildren().addAll((Object[])new Node[]{homeNav, this.createSpacer(), reqNav, this.createSpacer(), histNav, this.createSpacer(), profNav});
        container.getChildren().addAll((Object[])new Node[]{header, searchContainer, filters, scrollPane, bottomNav});
        this.view.getChildren().add((Object)container);
    }

    private Region createSpacer() {
        Region r = new Region();
        HBox.setHgrow((Node)r, (Priority)Priority.ALWAYS);
        return r;
    }

    private VBox createDecisionCard(String initials, String name, String details, String date, boolean approved, String reason) {
        VBox card = new VBox(10.0);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 14px; -fx-border-color: #e2e8f0; -fx-border-radius: 14px; -fx-padding: 16px;");
        HBox content = new HBox(14.0);
        content.setAlignment(Pos.CENTER_LEFT);
        Label avatar = new Label(initials);
        avatar.setStyle("-fx-background-color: #e2e8f0; -fx-text-fill: #475569; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 14px; -fx-background-radius: 30px; -fx-min-width: 48px; -fx-min-height: 48px; -fx-max-width: 48px; -fx-max-height: 48px; -fx-alignment: center;");
        Label statusBadge = new Label(approved ? "\u2713" : "\u2717");
        statusBadge.setStyle(approved ? "-fx-text-fill: white; -fx-background-color: #22c55e; -fx-background-radius: 10px; -fx-padding: 2px 4px; -fx-font-size: 9px; -fx-font-weight: bold;" : "-fx-text-fill: white; -fx-background-color: #ef4444; -fx-background-radius: 10px; -fx-padding: 2px 4px; -fx-font-size: 9px; -fx-font-weight: bold;");
        statusBadge.setTranslateX(8.0);
        statusBadge.setTranslateY(8.0);
        StackPane avatarPane = new StackPane(new Node[]{avatar, statusBadge});
        avatarPane.setAlignment(Pos.BOTTOM_RIGHT);
        VBox infoBox = new VBox(3.0);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-text-fill: #1e293b; -fx-font-weight: bold; -fx-font-size: 15px;");
        Label detailsLbl = new Label(details);
        detailsLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        Label dateLbl = new Label(date);
        dateLbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        infoBox.getChildren().addAll((Object[])new Node[]{nameLbl, detailsLbl, dateLbl});
        Region cardSpacer = new Region();
        HBox.setHgrow((Node)cardSpacer, (Priority)Priority.ALWAYS);
        VBox rightBox = new VBox(6.0);
        rightBox.setAlignment(Pos.TOP_RIGHT);
        Label statusLbl = new Label(approved ? "APPROVED" : "REJECTED");
        statusLbl.setStyle(approved ? "-fx-background-color: rgba(34, 197, 94, 0.15); -fx-text-fill: #22c55e; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px; -fx-font-weight: bold;" : "-fx-background-color: rgba(239, 68, 68, 0.15); -fx-text-fill: #ef4444; -fx-padding: 3px 10px; -fx-background-radius: 10px; -fx-font-size: 11px; -fx-font-weight: bold;");
        Label reasonLbl = new Label(reason);
        reasonLbl.setStyle(approved ? "-fx-text-fill: #d4af37; -fx-font-size: 12px; -fx-font-weight: bold;" : "-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        rightBox.getChildren().addAll((Object[])new Node[]{statusLbl, reasonLbl});
        content.getChildren().addAll((Object[])new Node[]{avatarPane, infoBox, cardSpacer, rightBox});
        card.getChildren().add((Object)content);
        return card;
    }

    private VBox createNavItem(String emoji, String text, boolean active) {
        VBox box = new VBox(4.0);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);
        Label icon = new Label(emoji);
        icon.setStyle(active ? "-fx-text-fill: #1e293b; -fx-font-size: 20px;" : "-fx-text-fill: #94a3b8; -fx-font-size: 20px;");
        Label lbl = new Label(text);
        lbl.setStyle(active ? "-fx-text-fill: #1e293b; -fx-font-size: 10px; -fx-font-weight: bold;" : "-fx-text-fill: #94a3b8; -fx-font-size: 10px;");
        box.getChildren().addAll((Object[])new Node[]{icon, lbl});
        return box;
    }

    public VBox getView() {
        return this.view;
    }
}

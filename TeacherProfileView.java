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
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.StackPane
 *  javafx.scene.layout.VBox
 *  javafx.scene.shape.Circle
 */
package com.certiflow;

import com.certiflow.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class TeacherProfileView {
    private VBox view = new VBox();

    public TeacherProfileView() {
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"profile-bg");
        VBox container = new VBox(20.0);
        container.getStyleClass().add((Object)"profile-container");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(20.0));
        HBox header = new HBox(12.0);
        header.setAlignment(Pos.CENTER_LEFT);
        Label backIcon = new Label("\u2190");
        backIcon.getStyleClass().add((Object)"profile-back-btn");
        backIcon.setCursor(Cursor.HAND);
        backIcon.setOnMouseClicked(e -> App.showOptionView());
        Label title = new Label("Teacher Profile");
        title.getStyleClass().add((Object)"profile-title");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label editIcon = new Label("\u270e");
        editIcon.getStyleClass().add((Object)"profile-edit-icon");
        editIcon.setStyle("-fx-text-fill: #d4af37;");
        header.getChildren().addAll((Object[])new Node[]{backIcon, title, spacer, editIcon});
        VBox profileInfo = new VBox(8.0);
        profileInfo.setAlignment(Pos.CENTER);
        profileInfo.setPadding(new Insets(10.0, 0.0, 20.0, 0.0));
        StackPane avatarPane = new StackPane();
        Circle avatarBg = new Circle(45.0);
        avatarBg.setStyle("-fx-fill: #d4af37;");
        Label avatarInitials = new Label("HV");
        avatarInitials.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label cameraIcon = new Label("\ud83d\udcf7");
        cameraIcon.getStyleClass().add((Object)"profile-camera-badge");
        StackPane.setAlignment((Node)cameraIcon, (Pos)Pos.BOTTOM_RIGHT);
        StackPane.setMargin((Node)cameraIcon, (Insets)new Insets(0.0, 5.0, 5.0, 0.0));
        avatarPane.getChildren().addAll((Object[])new Node[]{avatarBg, avatarInitials, cameraIcon});
        avatarPane.setMaxSize(90.0, 90.0);
        Label nameLbl = new Label("Dr. Helena Vance");
        nameLbl.getStyleClass().add((Object)"profile-name");
        Label roleLbl = new Label("SENIOR LECTURER");
        roleLbl.getStyleClass().add((Object)"profile-role");
        Label deptLbl = new Label("Department of Applied Sciences");
        deptLbl.getStyleClass().add((Object)"profile-dept");
        profileInfo.getChildren().addAll((Object[])new Node[]{avatarPane, nameLbl, roleLbl, deptLbl});
        VBox contactDetails = new VBox(10.0);
        contactDetails.setPadding(new Insets(0.0, 0.0, 15.0, 0.0));
        Label contactTitle = new Label("CONTACT DETAILS");
        contactTitle.getStyleClass().add((Object)"profile-section-title");
        HBox emailCard = this.createContactCard("\u2709", "Email Address", "h.vance@certiflow.edu");
        HBox phoneCard = this.createContactCard("\ud83d\udcde", "Office Phone", "+1 (555) 012-3456");
        contactDetails.getChildren().addAll((Object[])new Node[]{contactTitle, emailCard, phoneCard});
        VBox accountSettings = new VBox(10.0);
        accountSettings.setPadding(new Insets(0.0, 0.0, 15.0, 0.0));
        Label accountTitle = new Label("ACCOUNT SETTINGS");
        accountTitle.getStyleClass().add((Object)"profile-section-title");
        HBox notifyCard = this.createSettingCard("\ud83d\udd14", "Notification Preferences");
        HBox passwordCard = this.createSettingCard("\ud83d\udd12", "Change Password");
        HBox appSetCard = this.createSettingCard("\u2699", "App Settings");
        accountSettings.getChildren().addAll((Object[])new Node[]{accountTitle, notifyCard, passwordCard, appSetCard});
        HBox logoutBtn = new HBox(10.0);
        logoutBtn.setAlignment(Pos.CENTER);
        logoutBtn.getStyleClass().add((Object)"profile-logout-btn");
        logoutBtn.setCursor(Cursor.HAND);
        logoutBtn.setOnMouseClicked(e -> App.showOptionView());
        Label logoutIcon = new Label("\ud83d\udeaa");
        logoutIcon.setStyle("-fx-text-fill: #ff4d4f;");
        Label logoutTxt = new Label("Logout Account");
        logoutTxt.setStyle("-fx-text-fill: #ff4d4f; -fx-font-weight: bold; -fx-font-size: 16px;");
        logoutBtn.getChildren().addAll((Object[])new Node[]{logoutIcon, logoutTxt});
        VBox scrollContent = new VBox(15.0);
        scrollContent.setPadding(new Insets(10.0, 0.0, 10.0, 0.0));
        scrollContent.getChildren().addAll((Object[])new Node[]{header, profileInfo, contactDetails, accountSettings, logoutBtn});
        ScrollPane scrollPane = new ScrollPane((Node)scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add((Object)"dashboard-scroll-pane");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox scrollContentContainer = new VBox(new Node[]{scrollPane});
        VBox.setVgrow((Node)scrollContentContainer, (Priority)Priority.ALWAYS);
        HBox bottomNav = new HBox();
        bottomNav.getStyleClass().add((Object)"bottom-nav-light");
        bottomNav.setAlignment(Pos.CENTER);
        VBox homeNav = this.createNavItem("\ud83c\udfe0", "Home", false);
        homeNav.setOnMouseClicked(e -> App.showTeacherHome());
        VBox reqNav = this.createNavItem("\ud83d\udccb", "Requests", false);
        reqNav.setOnMouseClicked(e -> App.showTeacherDashboard());
        VBox histNav = this.createNavItem("\u23f2", "History", false);
        histNav.setOnMouseClicked(e -> App.showTeacherHistory());
        VBox profNav = this.createNavItem("\ud83d\udc64", "Profile", true);
        bottomNav.getChildren().addAll((Object[])new Node[]{homeNav, this.createSpacer(), reqNav, this.createSpacer(), histNav, this.createSpacer(), profNav});
        container.getChildren().addAll((Object[])new Node[]{scrollContentContainer, bottomNav});
        this.view.getChildren().add((Object)container);
    }

    private Region createSpacer() {
        Region r = new Region();
        HBox.setHgrow((Node)r, (Priority)Priority.ALWAYS);
        return r;
    }

    private HBox createContactCard(String iconStr, String label, String value) {
        HBox card = new HBox(15.0);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add((Object)"profile-card");
        Label iconBg = new Label(iconStr);
        iconBg.getStyleClass().add((Object)"profile-card-icon");
        VBox textVBox = new VBox(2.0);
        Label lblText = new Label(label);
        lblText.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
        Label valText = new Label(value);
        valText.setStyle("-fx-font-size: 14px; -fx-text-fill: #121212; -fx-font-weight: bold;");
        textVBox.getChildren().addAll((Object[])new Node[]{lblText, valText});
        card.getChildren().addAll((Object[])new Node[]{iconBg, textVBox});
        return card;
    }

    private HBox createSettingCard(String iconStr, String label) {
        HBox card = new HBox(15.0);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add((Object)"profile-card");
        card.setCursor(Cursor.HAND);
        Label icon = new Label(iconStr);
        icon.setStyle("-fx-font-size: 18px; -fx-text-fill: #475569;");
        Label lblText = new Label(label);
        lblText.setStyle("-fx-font-size: 15px; -fx-text-fill: #121212; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label arrow = new Label("\u276f");
        arrow.setStyle("-fx-text-fill: #94a3b8;");
        card.getChildren().addAll((Object[])new Node[]{icon, lblText, spacer, arrow});
        return card;
    }

    private VBox createNavItem(String emoji, String text, boolean active) {
        VBox box = new VBox(5.0);
        box.setAlignment(Pos.CENTER);
        box.setCursor(Cursor.HAND);
        Label icon = new Label(emoji);
        icon.getStyleClass().add((Object)(active ? "nav-icon-active" : "nav-icon-light"));
        Label lbl = new Label(text);
        lbl.getStyleClass().add((Object)(active ? "nav-text-active" : "nav-text-light"));
        box.getChildren().addAll((Object[])new Node[]{icon, lbl});
        return box;
    }

    public VBox getView() {
        return this.view;
    }
}

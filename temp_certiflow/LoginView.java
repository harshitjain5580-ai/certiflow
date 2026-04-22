/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.PasswordField
 *  javafx.scene.control.TextField
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.VBox
 */
package com.certiflow;

import com.certiflow.App;
import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginView {
    private VBox view = new VBox();

    public LoginView(String role, String emoji) {
        Label titleNode;
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"login-bg");
        VBox card = new VBox(25.0);
        card.getStyleClass().add((Object)"login-card");
        card.setMaxWidth(450.0);
        card.setPadding(new Insets(40.0));
        Button backBtn = new Button("\u2190");
        backBtn.getStyleClass().add((Object)"back-btn");
        backBtn.setOnAction(e -> App.showOptionView());
        HBox headerRow = new HBox();
        headerRow.getChildren().add((Object)backBtn);
        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitWidth(200.0);
            logoView.setPreserveRatio(true);
            titleNode = logoView;
        } else {
            Label title = new Label("Login");
            title.getStyleClass().add((Object)"login-title");
            titleNode = title;
        }
        HBox titleBox = new HBox(new Node[]{titleNode});
        titleBox.setAlignment(Pos.CENTER);
        HBox roleBox = new HBox(10.0);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPadding(new Insets(5.0, 15.0, 5.0, 15.0));
        roleBox.getStyleClass().add((Object)"role-badge");
        Label roleIcon = new Label(emoji);
        Label roleLbl = new Label("Logging in as " + role);
        roleLbl.getStyleClass().add((Object)"role-badge-text");
        roleBox.getChildren().addAll((Object[])new Node[]{roleIcon, roleLbl});
        HBox roleWrapper = new HBox(new Node[]{roleBox});
        roleWrapper.setAlignment(Pos.CENTER);
        TextField userField = new TextField();
        userField.setPromptText("ID / Username");
        userField.getStyleClass().add((Object)"text-input");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.getStyleClass().add((Object)"text-input");
        HBox forgotBox = new HBox();
        forgotBox.setAlignment(Pos.CENTER_RIGHT);
        Label forgotLbl = new Label("Forgot Password?");
        forgotLbl.getStyleClass().add((Object)"forgot-text");
        forgotBox.getChildren().add((Object)forgotLbl);
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add((Object)"login-button");
        loginBtn.setOnAction(e -> {
            if ("Student".equalsIgnoreCase(role)) {
                App.showStudentDashboard();
            } else if ("Teacher".equalsIgnoreCase(role)) {
                App.showTeacherHome();
            } else {
                App.showOptionView();
            }
        });
        card.getChildren().addAll((Object[])new Node[]{headerRow, titleBox, roleWrapper, userField, passField, forgotBox, loginBtn});
        this.view.getChildren().add((Object)card);
    }

    public VBox getView() {
        return this.view;
    }
}

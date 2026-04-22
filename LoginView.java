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
        Node titleNode;
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add("login-bg");
        VBox card = new VBox(25.0);
        card.getStyleClass().add("login-card");
        card.setMaxWidth(450.0);
        card.setPadding(new Insets(40.0));
        Button backBtn = new Button("\u2190");
        backBtn.getStyleClass().add("back-btn");
        backBtn.setOnAction(e -> App.showOptionView());
        HBox headerRow = new HBox();
        headerRow.getChildren().add(backBtn);
        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitWidth(200.0);
            logoView.setPreserveRatio(true);
            titleNode = logoView;
        } else {
            Label title = new Label("Login");
            title.getStyleClass().add("login-title");
            titleNode = title;
        }
        HBox titleBox = new HBox(new Node[] { titleNode });
        titleBox.setAlignment(Pos.CENTER);
        HBox roleBox = new HBox(10.0);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPadding(new Insets(5.0, 15.0, 5.0, 15.0));
        roleBox.getStyleClass().add("role-badge");
        Label roleIcon = new Label(emoji);
        Label roleLbl = new Label("Logging in as " + role);
        roleLbl.getStyleClass().add("role-badge-text");
        roleBox.getChildren().addAll(new Node[] { roleIcon, roleLbl });
        HBox roleWrapper = new HBox(new Node[] { roleBox });
        roleWrapper.setAlignment(Pos.CENTER);
        TextField userField = new TextField();
        userField.setPromptText("ID / Username");
        userField.getStyleClass().add("text-input");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.getStyleClass().add("text-input");
        HBox forgotBox = new HBox();
        forgotBox.setAlignment(Pos.CENTER_RIGHT);
        Label forgotLbl = new Label("Forgot Password?");
        forgotLbl.getStyleClass().add("forgot-text");
        forgotBox.getChildren().add(forgotLbl);

        Label errorMsg = new Label();
        errorMsg.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px; -fx-font-weight: bold;");

        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add("login-button");
        loginBtn.setOnAction(e -> {
            String u = userField.getText().trim();
            String p = passField.getText();
            boolean valid = false;

            if ("Admin".equalsIgnoreCase(role)) {
                for (App.AdminProfile a : App.adminDB.values()) {
                    if (p.equals(a.password) && (u.equals(a.email) || u.equals(a.systemId) || u.equalsIgnoreCase(a.name))) {
                        valid = true;
                        App.currentAdmin = a;
                        break;
                    }
                }
            } else if ("Teacher".equalsIgnoreCase(role)) {
                for (App.TeacherProfile t : App.teacherDB.values()) {
                    if (p.equals(t.password) && (u.equals(t.email) || u.equals(t.department) || u.equals(t.phone) || u.equalsIgnoreCase(t.name))) {
                        valid = true;
                        App.currentTeacher = t;
                        break;
                    }
                }
            } else if ("Student".equalsIgnoreCase(role)) {
                for (App.StudentProfile s : App.studentDB.values()) {
                    if (p.equals(s.password) && (u.equals(s.email) || u.equals(s.studentId) || u.equalsIgnoreCase(s.name))) {
                        valid = true;
                        App.currentStudent = s;
                        break;
                    }
                }
            }

            if (!valid) {
                 errorMsg.setText("Invalid credentials. Please try again.");
                 return;
            }

            App.currentUserRole = role;
            if ("Student".equalsIgnoreCase(role)) {
                App.showStudentDashboard();
            } else if ("Teacher".equalsIgnoreCase(role)) {
                App.showTeacherHome();
            } else if ("Admin".equalsIgnoreCase(role)) {
                App.showAdminDashboard();
            } else {
                App.showOptionView();
            }
        });
        Label signUpLink = new Label("New to Certiflow? Sign Up");
        signUpLink.setStyle("-fx-text-fill: #eab308; -fx-font-size: 13px; -fx-cursor: hand; -fx-font-weight: bold;");
        signUpLink.setOnMouseClicked(e -> App.showSignUpView(role, emoji));
        HBox footer = new HBox(signUpLink);
        footer.setAlignment(Pos.CENTER);

        card.getChildren()
                .addAll(new Node[] { headerRow, titleBox, roleWrapper, userField, passField, forgotBox, errorMsg, loginBtn, footer });
        this.view.getChildren().add(card);
    }

    public VBox getView() {
        return this.view;
    }
}

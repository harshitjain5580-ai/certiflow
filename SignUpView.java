package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignUpView {
    private VBox view = new VBox();

    public SignUpView(String role, String emoji) {
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add("login-bg");

        VBox card = new VBox(20);
        card.getStyleClass().add("login-card");
        card.setMaxWidth(480);
        card.setPadding(new Insets(40));

        Button backBtn = new Button("\u2190");
        backBtn.getStyleClass().add("back-btn");
        backBtn.setOnAction(e -> App.showLoginView(role, emoji));

        Label title = new Label("Create " + role + " Account");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        HBox roleBox = new HBox(10);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.getStyleClass().add("role-badge");
        Label roleIcon = new Label(emoji);
        Label roleLbl = new Label("Registering as " + role);
        roleLbl.getStyleClass().add("role-badge-text");
        roleBox.getChildren().addAll(roleIcon, roleLbl);

        VBox form = new VBox(15);
        TextField nameField = createStyledField("Full Name");
        TextField emailField = createStyledField("Official Email");
        TextField phoneField = createStyledField("Phone Number (+91)");
        TextField idField = createStyledField("Admin".equalsIgnoreCase(role) ? "System ID" : ("Teacher".equalsIgnoreCase(role) ? "Department / Teacher ID" : "Student ID"));
        PasswordField passField = new PasswordField();
        passField.setPromptText("Create Password");
        passField.getStyleClass().add("text-input");

        Label errorLbl = new Label();
        errorLbl.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px; -fx-font-weight: bold;");

        Button signUpBtn = new Button("CREATE ACCOUNT");
        signUpBtn.setMaxWidth(Double.MAX_VALUE);
        signUpBtn.getStyleClass().add("login-button");
        signUpBtn.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty() || emailField.getText().trim().isEmpty() || 
                phoneField.getText().trim().isEmpty() || idField.getText().trim().isEmpty() || passField.getText().trim().isEmpty()) {
                errorLbl.setText("Please fill out all required fields.");
                return;
            }

            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            
            // Basic Indian Phone Check
            if (!phone.startsWith("+91") && phone.length() == 10) phone = "+91" + phone;

            if ("Admin".equalsIgnoreCase(role)) {
                if (App.adminDB.containsKey(email)) { errorLbl.setText("Account exists!"); return; }
                App.currentAdmin = new App.AdminProfile();
                App.currentAdmin.name = nameField.getText().trim();
                App.currentAdmin.email = email;
                App.currentAdmin.phone = phone;
                App.currentAdmin.systemId = idField.getText().trim();
                App.currentAdmin.password = passField.getText();
                App.adminDB.put(email, App.currentAdmin);
                App.saveDatabases();
            } else if ("Teacher".equalsIgnoreCase(role)) {
                if (App.teacherDB.containsKey(email)) { errorLbl.setText("Account exists!"); return; }
                App.currentTeacher = new App.TeacherProfile();
                App.currentTeacher.name = nameField.getText().trim();
                App.currentTeacher.email = email;
                App.currentTeacher.phone = phone;
                App.currentTeacher.department = idField.getText().trim();
                App.currentTeacher.password = passField.getText();
                App.teacherDB.put(email, App.currentTeacher);
                App.saveDatabases();
            } else if ("Student".equalsIgnoreCase(role)) {
                if (App.studentDB.containsKey(email)) { errorLbl.setText("Account exists!"); return; }
                App.currentStudent = new App.StudentProfile();
                App.currentStudent.name = nameField.getText().trim();
                App.currentStudent.email = email;
                App.currentStudent.phone = phone;
                App.currentStudent.studentId = idField.getText().trim();
                App.currentStudent.password = passField.getText();
                App.currentStudent.portfolioList = new java.util.ArrayList<>();
                App.currentStudent.nocRequests = new java.util.ArrayList<>();
                App.studentDB.put(email, App.currentStudent);
                App.saveDatabases();
            }
            App.currentUserRole = role;
            App.addNotification("\ud83c\udf89", "Account Created", "Welcome to Certiflow, " + nameField.getText().trim() + "!");
            
            if ("Admin".equalsIgnoreCase(role)) App.showAdminDashboard();
            else if ("Teacher".equalsIgnoreCase(role)) App.showTeacherHome();
            else App.showStudentDashboard();
        });

        Label loginLink = new Label("Already have an account? Login");
        loginLink.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px; -fx-cursor: hand;");
        loginLink.setOnMouseClicked(e -> App.showLoginView(role, emoji));
        HBox footer = new HBox(loginLink);
        footer.setAlignment(Pos.CENTER);

        card.getChildren().addAll(backBtn, title, roleBox, nameField, emailField, phoneField, idField, passField, errorLbl, signUpBtn, footer);
        this.view.getChildren().add(card);
    }

    private TextField createStyledField(String prompt) {
        TextField f = new TextField();
        f.setPromptText(prompt);
        f.setPrefHeight(45);
        f.getStyleClass().add("text-input");
        return f;
    }

    public VBox getView() { return this.view; }
}

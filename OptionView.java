package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.io.InputStream;

public class OptionView {
    private HBox view = new HBox();

    public OptionView() {
        this.view.getStyleClass().add("desktop-root");
        this.view.setAlignment(Pos.CENTER);

        // --- Left Section: Branding & Hero ---
        VBox leftSection = new VBox(30);
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setStyle("-fx-background-color: linear-gradient(to bottom right, #1a1a1a, #0a0a0a); -fx-padding: 60px;");
        HBox.setHgrow(leftSection, Priority.ALWAYS);

        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitWidth(350);
            logoView.setPreserveRatio(true);
            leftSection.getChildren().add(logoView);
        } else {
            Label title = new Label("CERTIFLOW");
            title.setStyle("-fx-text-fill: #eab308; -fx-font-size: 64px; -fx-font-weight: 900; -fx-letter-spacing: 5px;");
            leftSection.getChildren().add(title);
        }

        Label moto = new Label("Sovereign Digital Credential Infrastructure");
        moto.setStyle("-fx-text-fill: #64748b; -fx-font-size: 18px; -fx-font-weight: bold; -fx-letter-spacing: 2px;");
        
        Label desc = new Label("Securely manage, verify, and audit institutional certifications with post-quantum encryption protocols.");
        desc.setStyle("-fx-text-fill: #444; -fx-font-size: 14px; -fx-max-width: 500px; -fx-text-alignment: center;");
        desc.setWrapText(true);

        leftSection.getChildren().addAll(moto, desc);

        // --- Right Section: Role Selection ---
        VBox rightSection = new VBox(40);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setStyle("-fx-background-color: #0a0a0a; -fx-padding: 80px; -fx-min-width: 500px;");
        
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Label welcome = new Label("Welcome to the Vault");
        welcome.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        Label subWelcome = new Label("Please select your access tier to continue.");
        subWelcome.setStyle("-fx-text-fill: #64748b; -fx-font-size: 16px;");
        header.getChildren().addAll(welcome, subWelcome);

        VBox buttonBox = new VBox(15);
        buttonBox.getChildren().addAll(
            createDesktopRoleButton("Student Portal", "Access your verified credentials", "\ud83c\udf93"),
            createDesktopRoleButton("Teacher Console", "Manage and validate requests", "\ud83d\udcda"),
            createDesktopRoleButton("Admin Terminal", "System oversight and audit", "\ud83d\udee1\ufe0f")
        );

        HBox footer = new HBox(8);
        footer.setAlignment(Pos.CENTER_LEFT);
        Label lockIcon = new Label("\ud83d\udd12"); lockIcon.setStyle("-fx-text-fill: #eab308;");
        Label secureText = new Label("Encrypted via Sovereign-X Cloud Protocol");
        secureText.setStyle("-fx-text-fill: #334155; -fx-font-size: 12px; -fx-font-weight: bold;");
        footer.getChildren().addAll(lockIcon, secureText);

        rightSection.getChildren().addAll(header, buttonBox, footer);

        this.view.getChildren().addAll(leftSection, rightSection);
    }

    private Button createDesktopRoleButton(String title, String sub, String emoji) {
        Button btn = new Button();
        btn.getStyleClass().add("role-button-desktop"); // We will add this to CSS if needed, or style here
        btn.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px; -fx-cursor: hand; -fx-border-color: #262626; -fx-border-radius: 12px;");
        btn.setMaxWidth(Double.MAX_VALUE);
        
        HBox content = new HBox(20);
        content.setAlignment(Pos.CENTER_LEFT);
        
        Label icon = new Label(emoji);
        icon.setStyle("-fx-font-size: 24px; -fx-text-fill: #eab308;");
        
        VBox texts = new VBox(2);
        Label primary = new Label(title);
        primary.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");
        Label secondary = new Label(sub);
        secondary.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        texts.getChildren().addAll(primary, secondary);
        
        Region s = new Region(); HBox.setHgrow(s, Priority.ALWAYS);
        Label arrow = new Label("\u276f"); arrow.setStyle("-fx-text-fill: #333;");
        
        content.getChildren().addAll(icon, texts, s, arrow);
        btn.setGraphic(content);
        
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #222; -fx-background-radius: 12px; -fx-padding: 20px; -fx-cursor: hand; -fx-border-color: #eab308; -fx-border-radius: 12px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 12px; -fx-padding: 20px; -fx-cursor: hand; -fx-border-color: #262626; -fx-border-radius: 12px;"));
        
        btn.setOnAction(e -> {
            String role = title.contains("Student") ? "Student" : (title.contains("Teacher") ? "Teacher" : "Admin");
            App.showLoginView(role, emoji);
        });

        return btn;
    }

    public HBox getView() {
        return this.view;
    }
}

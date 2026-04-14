/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.geometry.Insets
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OptionView {
    private VBox view = new VBox(40.0);

    public OptionView() {
        Label titleNode;
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"option-bg");
        VBox titleBox = new VBox(10.0);
        titleBox.setAlignment(Pos.CENTER);
        InputStream logoStream = this.getClass().getResourceAsStream("/logo.png");
        if (logoStream != null) {
            ImageView logoView = new ImageView(new Image(logoStream));
            logoView.setFitWidth(300.0);
            logoView.setPreserveRatio(true);
            titleNode = logoView;
        } else {
            Label title = new Label("Certiflow");
            title.getStyleClass().add((Object)"main-title");
            titleNode = title;
        }
        Label subtitle = new Label("Please select your role");
        subtitle.getStyleClass().add((Object)"sub-title");
        titleBox.getChildren().addAll((Object[])new Node[]{titleNode, subtitle});
        VBox buttonBox = new VBox(20.0);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMaxWidth(380.0);
        buttonBox.setPadding(new Insets(30.0));
        buttonBox.getStyleClass().add((Object)"button-container");
        buttonBox.getChildren().add((Object)this.createRoleButton("Student", "\ud83c\udf93"));
        buttonBox.getChildren().add((Object)this.createRoleButton("Teacher", "\ud83d\udcda"));
        buttonBox.getChildren().add((Object)this.createRoleButton("Admin", "\ud83d\udee1\ufe0f"));
        HBox secureBox = new HBox(8.0);
        secureBox.setAlignment(Pos.CENTER);
        Label lockIcon = new Label("\ud83d\udd12");
        lockIcon.getStyleClass().add((Object)"secure-icon");
        Label secureText = new Label("Secure Access");
        secureText.getStyleClass().add((Object)"secure-text");
        secureBox.getChildren().addAll((Object[])new Node[]{lockIcon, secureText});
        this.view.getChildren().addAll((Object[])new Node[]{titleBox, buttonBox, secureBox});
    }

    private Button createRoleButton(String text, String emojiStr) {
        Button btn = new Button();
        btn.getStyleClass().add((Object)"role-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        HBox box = new HBox(15.0);
        box.setAlignment(Pos.CENTER);
        Label icon = new Label(emojiStr);
        icon.getStyleClass().add((Object)"role-icon");
        Label lbl = new Label(text.toUpperCase());
        lbl.getStyleClass().add((Object)"role-text");
        box.getChildren().addAll((Object[])new Node[]{icon, lbl});
        btn.setGraphic((Node)box);
        btn.setOnAction(e -> App.showLoginView(text, emojiStr));
        return btn;
    }

    public VBox getView() {
        return this.view;
    }
}

package com.certiflow;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AdminPurgeDataView {
    private VBox view = new VBox();

    public AdminPurgeDataView() {
        this.view.setStyle("-fx-background-color: #121212; -fx-padding: 40px;");
        this.view.setAlignment(Pos.CENTER);
        this.view.setSpacing(30);

        Label title = new Label("\u26a0  CRITICAL WARNING");
        title.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 28px; -fx-font-weight: 900;");

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setMaxWidth(400);
        
        Label desc = new Label("Purging archived data is permanent and irreversible. All student credentials older than 5 years will be wiped from all geostationary synchronization nodes.");
        desc.setWrapText(true);
        desc.setStyle("-fx-text-fill: #ffffff; -fx-text-alignment: center; -fx-font-size: 14px; -fx-line-spacing: 5px;");
        
        Label challenge = new Label("Type 'CONFIRM DELETE' to proceed:");
        challenge.setStyle("-fx-text-fill: #9ca3af; -fx-font-size: 12px;");
        
        content.getChildren().addAll(desc, challenge);

        VBox btns = new VBox(15);
        btns.setMaxWidth(400);
        
        Label deleteBtn = new Label("PERMANENTLY PURGE DATA");
        deleteBtn.setMaxWidth(Double.MAX_VALUE);
        deleteBtn.setAlignment(Pos.CENTER);
        deleteBtn.setStyle("-fx-background-color: #ef4444; -fx-text-fill: white; -fx-padding: 15px; -fx-font-weight: bold; -fx-background-radius: 8px; -fx-cursor: hand;");
        deleteBtn.setOnMouseClicked(e -> App.showAdminProfile());

        Label cancelBtn = new Label("NEVERMIND, GO BACK");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setAlignment(Pos.CENTER);
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9ca3af; -fx-padding: 10px; -fx-font-weight: bold; -fx-cursor: hand;");
        cancelBtn.setOnMouseClicked(e -> App.showAdminProfile());
        
        btns.getChildren().addAll(deleteBtn, cancelBtn);

        this.view.getChildren().addAll(title, content, btns);
    }

    public VBox getView() { return this.view; }
}

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
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.VBox
 *  javafx.scene.text.TextAlignment
 */
package com.certiflow;

import com.certiflow.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class TeacherReportExportView {
    private VBox view = new VBox();

    public TeacherReportExportView() {
        this.view.setAlignment(Pos.CENTER);
        this.view.getStyleClass().add((Object)"dashboard-bg");
        VBox container = new VBox(30.0);
        container.getStyleClass().add((Object)"dashboard-container");
        container.setMaxWidth(480.0);
        container.setPadding(new Insets(20.0));
        HBox header = new HBox(12.0);
        header.setAlignment(Pos.CENTER_LEFT);
        Label backIcon = new Label("\u2190");
        backIcon.getStyleClass().add((Object)"teacher-back-btn");
        backIcon.setCursor(Cursor.HAND);
        backIcon.setOnMouseClicked(e -> App.showTeacherHome());
        Label title = new Label("Export Manager");
        title.setStyle("-fx-text-fill: #fac736; -fx-font-size: 20px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow((Node)spacer, (Priority)Priority.ALWAYS);
        Label exportIcon = new Label("\ud83d\udce4");
        exportIcon.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 20px;");
        header.getChildren().addAll((Object[])new Node[]{backIcon, title, spacer, exportIcon});
        VBox exportBox = new VBox(15.0);
        exportBox.getStyleClass().add((Object)"export-container");
        exportBox.setAlignment(Pos.CENTER);
        Label boxTitle = new Label("Weekly Certification Report");
        boxTitle.getStyleClass().add((Object)"export-title");
        Label boxSubtitle = new Label("Includes all student NOC evaluations, status updates, and summary metrics for the current week. Format: PDF.");
        boxSubtitle.getStyleClass().add((Object)"export-subtitle");
        boxSubtitle.setWrapText(true);
        boxSubtitle.setAlignment(Pos.CENTER);
        boxSubtitle.setTextAlignment(TextAlignment.CENTER);
        Button downloadBtn = new Button("Download Report");
        downloadBtn.getStyleClass().add((Object)"export-btn");
        downloadBtn.setOnAction(e -> {
            System.out.println("Triggering 'Export Weekly Report' download...");
            App.showTeacherHome();
        });
        exportBox.getChildren().addAll((Object[])new Node[]{boxTitle, boxSubtitle, downloadBtn});
        VBox contentContainer = new VBox(40.0);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.getChildren().addAll((Object[])new Node[]{header, exportBox});
        VBox.setVgrow((Node)contentContainer, (Priority)Priority.ALWAYS);
        container.getChildren().add((Object)contentContainer);
        this.view.getChildren().add((Object)container);
    }

    public VBox getView() {
        return this.view;
    }
}

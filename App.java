/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 */
package com.certiflow;

import com.certiflow.LoginView;
import com.certiflow.OptionView;
import com.certiflow.StudentDashboardView;
import com.certiflow.TeacherDashboardView;
import com.certiflow.TeacherHistoryView;
import com.certiflow.TeacherHomeView;
import com.certiflow.TeacherProfileView;
import com.certiflow.TeacherReportExportView;
import com.certiflow.TeacherStudentListView;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App
extends Application {
    private static Stage primaryStage;

    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Certiflow");
        try {
            InputStream iconStream = ((Object)((Object)this)).getClass().getResourceAsStream("/logo.png");
            if (iconStream == null) {
                iconStream = ((Object)((Object)this)).getClass().getResourceAsStream("/icon.png");
            }
            if (iconStream != null) {
                Image icon = new Image(iconStream);
                primaryStage.getIcons().add((Object)icon);
            }
        }
        catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
        }
        App.showOptionView();
        primaryStage.show();
    }

    public static void showOptionView() {
        OptionView optionView = new OptionView();
        Scene scene = new Scene((Parent)optionView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showLoginView(String role, String emoji) {
        LoginView loginView = new LoginView(role, emoji);
        Scene scene = new Scene((Parent)loginView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherHome() {
        TeacherHomeView homeView = new TeacherHomeView();
        Scene scene = new Scene((Parent)homeView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherHistory() {
        TeacherHistoryView historyView = new TeacherHistoryView();
        Scene scene = new Scene((Parent)historyView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherStudentList() {
        TeacherStudentListView studentListView = new TeacherStudentListView();
        Scene scene = new Scene((Parent)studentListView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherReportExport() {
        TeacherReportExportView exportView = new TeacherReportExportView();
        Scene scene = new Scene((Parent)exportView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showStudentDashboard() {
        StudentDashboardView dashboardView = new StudentDashboardView();
        Scene scene = new Scene((Parent)dashboardView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherDashboard() {
        TeacherDashboardView dashboardView = new TeacherDashboardView();
        Scene scene = new Scene((Parent)dashboardView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void showTeacherProfile() {
        TeacherProfileView profileView = new TeacherProfileView();
        Scene scene = new Scene((Parent)profileView.getView(), 1000.0, 700.0);
        scene.getStylesheets().add((Object)App.class.getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        App.launch((String[])args);
    }
}

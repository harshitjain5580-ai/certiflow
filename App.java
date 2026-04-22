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
import com.certiflow.AdminDashboardView;
import com.certiflow.AdminProfileView;
import java.io.InputStream;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.*;
import java.nio.file.*;

import java.util.UUID;

public class App
extends Application {
    private static Stage primaryStage;
    public static String currentUserRole = "";
    public static Set<String> shortlistedIds = new HashSet<>();

    public static class AdminProfile implements Serializable {
        public String name = "";
        public String role = "";
        public String email = "";
        public String phone = "";
        public String systemId = "";
        public String password = "";
        public String profilePicPath = null;
        public String accentColor = "#eab308";
        public boolean biometricAuth = true;
        public boolean pushNotifications = true;
        public boolean emailReports = false;
    }

    public static class TeacherProfile implements Serializable {
        public String name = "";
        public String role = "";
        public String department = "";
        public String email = "";
        public String phone = "";
        public String password = "";
        public String profilePicPath = null;
        public String accentColor = "#fac736";
    }

    public static class StudentProfile implements Serializable {
        public String name = "";
        public String major = "";
        public String college = "";
        public String email = "";
        public String phone = "";
        public String studentId = "";
        public String yearOfStudy = "";
        public String semester = "";
        public String password = "";
        public String profilePicPath = null;
        public String accentColor = "#eab308";
        public List<Portfolio> portfolioList = new ArrayList<>();
        public List<NOCRequest> nocRequests = new ArrayList<>();
    }

    public static class Portfolio implements Serializable {
        public String id = UUID.randomUUID().toString();
        public String title;
        public String description;
        public String status = "Pending";
        public String submissionDate = "Just now";
        public String documentName = null;
        public String documentPath = null;

        public Portfolio(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }

    public static class NOCRequest implements Serializable {
        public String id = UUID.randomUUID().toString();
        public String department;
        public String type;
        public String status = "Pending";
        public String date = "Just now";

        public NOCRequest(String department, String type) {
            this.department = department;
            this.type = type;
        }
    }

    public static class Message implements Serializable {
        public String id = UUID.randomUUID().toString();
        public String sender;
        public String recipient;
        public String content;
        public String timestamp;
        public boolean isAdmin;

        public Message(String sender, String recipient, String content, String timestamp, boolean isAdmin) {
            this.sender = sender;
            this.recipient = recipient;
            this.content = content;
            this.timestamp = timestamp;
            this.isAdmin = isAdmin;
        }
    }

    public static class Notification implements Serializable {
        public String icon;
        public String title;
        public String desc;
        public String time;
        public boolean urgent;

        public Notification(String icon, String title, String desc, String time, boolean urgent) {
            this.icon = icon;
            this.title = title;
            this.desc = desc;
            this.time = time;
            this.urgent = urgent;
        }
    }

    public static class HistoryRecord implements Serializable {
        public String initials;
        public String name;
        public String details;
        public String status;
        public String reason;
        public String date;

        public HistoryRecord(String initials, String name, String details, String status, String reason, String date) {
            this.initials = initials;
            this.name = name;
            this.details = details;
            this.status = status;
            this.reason = reason;
            this.date = date;
        }
    }

    public static Map<String, List<Message>> adminChatHistory = new HashMap<>();
    public static Map<String, List<Message>> teacherChatHistory = new HashMap<>();
    public static Map<String, String> validationStatuses = new HashMap<>();
    public static List<Notification> notificationHistory = new ArrayList<>();
    public static List<HistoryRecord> actionHistory = new ArrayList<>();
    
    public static Map<String, AdminProfile> adminDB = new HashMap<>();
    public static Map<String, TeacherProfile> teacherDB = new HashMap<>();
    public static Map<String, StudentProfile> studentDB = new HashMap<>();
    
    public static String globalSearchQuery = "";
    public static Map<String, String> globalFilters = new HashMap<>();
    
    public static AdminProfile currentAdmin = new AdminProfile();
    public static TeacherProfile currentTeacher = new TeacherProfile();
    public static StudentProfile currentStudent = new StudentProfile();

    public static void saveHistory() {
        try (ObjectOutputStream oosAds = new ObjectOutputStream(new FileOutputStream("admin_vault_chats.dat"))) {
            oosAds.writeObject(adminChatHistory);
        } catch (IOException e) { e.printStackTrace(); }

        try (ObjectOutputStream oosTch = new ObjectOutputStream(new FileOutputStream("teacher_vault_chats.dat"))) {
            oosTch.writeObject(teacherChatHistory);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveActionHistory() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("action_history_vault.dat"))) {
            oos.writeObject(actionHistory);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveDatabases() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("admin_db.dat"))) { oos.writeObject(adminDB); } catch (Exception e) {}
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("teacher_db.dat"))) { oos.writeObject(teacherDB); } catch (Exception e) {}
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student_db.dat"))) { oos.writeObject(studentDB); } catch (Exception e) {}
    }

    public static void saveNotifications() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("notifications_vault.dat"))) {
            oos.writeObject(notificationHistory);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveValidations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("validations_vault.dat"))) {
            oos.writeObject(validationStatuses);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void saveShortlist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shortlisted_vault.dat"))) {
            oos.writeObject(shortlistedIds);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @SuppressWarnings("unchecked")
    public static void loadShortlist() {
        File file = new File("shortlisted_vault.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                shortlistedIds = (Set<String>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        // Load Admin Chats
        File adminFile = new File("admin_vault_chats.dat");
        if (adminFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminFile))) {
                adminChatHistory = (Map<String, List<Message>>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
        
        // Load Teacher Chats
        File teacherFile = new File("teacher_vault_chats.dat");
        if (teacherFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(teacherFile))) {
                teacherChatHistory = (Map<String, List<Message>>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
        
        // Load Admin DB
        File adminDbFile = new File("admin_db.dat");
        if (adminDbFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(adminDbFile))) {
                adminDB = (Map<String, AdminProfile>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Load Teacher DB
        File teacherDbFile = new File("teacher_db.dat");
        if (teacherDbFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(teacherDbFile))) {
                teacherDB = (Map<String, TeacherProfile>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Load Student DB
        File studentDbFile = new File("student_db.dat");
        if (studentDbFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(studentDbFile))) {
                studentDB = (Map<String, StudentProfile>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Load Notifications
        File notifFile = new File("notifications_vault.dat");
        if (notifFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(notifFile))) {
                notificationHistory = (List<Notification>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Load Validations
        File valFile = new File("validations_vault.dat");
        if (valFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(valFile))) {
                validationStatuses = (Map<String, String>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }

        // Load Shortlist
        loadShortlist();

        // Load Action History
        File histFile = new File("action_history_vault.dat");
        if (histFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(histFile))) {
                actionHistory = (List<HistoryRecord>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public static void addNotification(String icon, String title, String desc) {
        addNotification(icon, title, desc, "Just now", false);
    }

    public static void addNotification(String icon, String title, String desc, String time, boolean urgent) {
        notificationHistory.add(new Notification(icon, title, desc, time, urgent));
        saveNotifications();
    }

    public static void addMessage(String student, Message msg) {
        // Staff sending to student
        String key = student + ":" + msg.sender; 
        if (currentUserRole.equalsIgnoreCase("ADMIN")) {
            adminChatHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        } else if (currentUserRole.equalsIgnoreCase("TEACHER")) {
            teacherChatHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        }
        addNotification("\ud83d\udcac", "Direct Link Established", msg.sender + " transmitted data to " + student, "Just now", false);
        saveHistory();
    }

    public static void addStudentMessage(String recipient, Message msg, boolean toAdmin) {
        // Student sending to staff
        String key = currentStudent.name + ":" + recipient;
        if (toAdmin) {
            adminChatHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        } else {
            teacherChatHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        }
        addNotification("\ud83d\udcac", "Client Transmission", currentStudent.name + " sent a secure packet to " + recipient, "Just now", false);
        saveHistory();
    }

    public static String getAccentColor() {
        if (currentUserRole.equalsIgnoreCase("ADMIN")) return currentAdmin.accentColor;
        if (currentUserRole.equalsIgnoreCase("TEACHER")) return currentTeacher.accentColor;
        return currentStudent.accentColor;
    }

    public static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) return "??";
        String[] parts = name.trim().split("\\s+");
        if (parts.length > 1) {
            return (parts[0].charAt(0) + "" + parts[parts.length - 1].charAt(0)).toUpperCase();
        }
        return (parts[0].charAt(0) + "").toUpperCase();
    }

    public static void deleteMessage(String student, String messageId) {
        Map<String, List<Message>> TargetMap = "Admin".equalsIgnoreCase(currentUserRole) ? adminChatHistory : teacherChatHistory;
        List<Message> messages = TargetMap.get(student);
        if (messages != null) {
            messages.removeIf(m -> m.id.equals(messageId));
            if (messages.isEmpty()) {
                TargetMap.remove(student);
            }
            saveHistory();
        }
    }

    public static void deleteStudentMessage(String partnerName, String messageId, boolean toAdmin) {
        // students use their own name as the key in the teacher/admin map
        Map<String, List<Message>> targetMap = toAdmin ? adminChatHistory : teacherChatHistory;
        List<Message> messages = targetMap.get(currentStudent.name);
        if (messages != null) {
            messages.removeIf(m -> m.id.equals(messageId));
            if (messages.isEmpty()) {
                targetMap.remove(currentStudent.name);
            }
            saveHistory();
        }
    }

    public static void toggleShortlist(String id) {
        if (shortlistedIds.contains(id)) {
            shortlistedIds.remove(id);
        } else {
            shortlistedIds.add(id);
        }
        saveShortlist();
    }

    public static boolean isShortlisted(String id) {
        return shortlistedIds.contains(id);
    }

    public static void addPortfolio(Portfolio p) {
        currentStudent.portfolioList.add(0, p);
        saveDatabases();
    }

    public static void addNOCRequest(NOCRequest r) {
        currentStudent.nocRequests.add(0, r);
        saveDatabases();
    }

    public static class PendingTask {
        public String id;
        public String category; 
        public String title;
        public String desc;
        public String studentName;
        public boolean isPortfolio;
        public PendingTask(String i, String c, String t, String d, String s, boolean p) {
            id=i; category=c; title=t; desc=d; studentName=s; isPortfolio=p;
        }
    }

    public static List<PendingTask> getAllPendingTasks() {
        List<PendingTask> pending = new ArrayList<>();
        for (StudentProfile s : studentDB.values()) {
            if (s.portfolioList != null) {
                for (Portfolio p : s.portfolioList) {
                    if ("Pending".equalsIgnoreCase(p.status)) pending.add(new PendingTask(p.id, "PORTFOLIO", p.title, p.description, s.name, true));
                }
            }
            if (s.nocRequests != null) {
                for (NOCRequest n : s.nocRequests) {
                    if ("Pending".equalsIgnoreCase(n.status)) pending.add(new PendingTask(n.id, "NOC CLEARANCE", n.department, n.type, s.name, false));
                }
            }
        }
        return pending;
    }

    public static void updatePortfolioStatus(String id, String status) {
        for (StudentProfile s : studentDB.values()) {
            if (s.portfolioList == null) continue;
            for (Portfolio p : s.portfolioList) {
                if (p.id.equals(id)) {
                    p.status = status;
                    saveDatabases();
                    return;
                }
            }
        }
    }

    public static void updateNOCStatus(String id, String status) {
        for (StudentProfile s : studentDB.values()) {
            if (s.nocRequests == null) continue;
            for (NOCRequest r : s.nocRequests) {
                if (r.id.equals(id)) {
                    r.status = status;
                    saveDatabases();
                    return;
                }
            }
        }
    }

    public static javafx.scene.Node getSidebarBranding() {
        javafx.scene.layout.HBox brandBox = new javafx.scene.layout.HBox(12);
        brandBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        brandBox.setStyle("-fx-padding: 0 0 30px 10px;");
        
        try {
            javafx.scene.image.ImageView logoView = new javafx.scene.image.ImageView(new javafx.scene.image.Image(App.class.getResourceAsStream("/logo.png")));
            logoView.setFitHeight(42);
            logoView.setPreserveRatio(true);
            
            // Add a subtle glow/halo to ensure black logo parts are visible on the #0a0a0a sidebar
            javafx.scene.effect.DropShadow glow = new javafx.scene.effect.DropShadow();
            glow.setColor(javafx.scene.paint.Color.web("#ffffff", 0.4));
            glow.setRadius(12.0);
            glow.setSpread(0.2);
            logoView.setEffect(glow);
            
            javafx.scene.control.Label brand = new javafx.scene.control.Label("CertiFlow");
            brand.setStyle("-fx-text-fill: white; -fx-font-weight: 900; -fx-font-size: 24px; -fx-letter-spacing: 0.8px;");
            
            brandBox.getChildren().addAll(logoView, brand);
        } catch (Exception e) {
            javafx.scene.control.Label brand = new javafx.scene.control.Label("CertiFlow");
            brand.setStyle("-fx-text-fill: " + getAccentColor() + "; -fx-font-weight: 900; -fx-font-size: 24px;");
            brandBox.getChildren().add(brand);
        }
        
        return brandBox;
    }

    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Certiflow");
        App.loadData();
        try {
            InputStream iconStream = this.getClass().getResourceAsStream("/logo.png");
            if (iconStream == null) {
                iconStream = this.getClass().getResourceAsStream("/icon.png");
            }
            if (iconStream != null) {
                Image icon = new Image(iconStream);
                primaryStage.getIcons().add(icon);
            }
        }
        catch (Exception e) {
            System.err.println("Could not load icon: " + e.getMessage());
        }
        App.showOptionView();
        primaryStage.setMaximized(true);
        primaryStage.show();
        
        javafx.application.Platform.runLater(() -> {
            primaryStage.setIconified(false);
            primaryStage.setMaximized(true);
            primaryStage.toFront();
            primaryStage.requestFocus();
        });
    }

    private static void setRoot(javafx.scene.Parent root) {
        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(root, 1200, 800);
            scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(root);
        }
        // Force re-maximization if needed to prevent shrinking
        if (primaryStage.isMaximized()) {
            primaryStage.setMaximized(true);
        }
    }

    public static void showOptionView() {
        setRoot(new OptionView().getView());
    }

    public static void showLoginView(String role, String emoji) {
        setRoot(new LoginView(role, emoji).getView());
    }

    public static void showSignUpView(String role, String emoji) {
        setRoot(new SignUpView(role, emoji).getView());
    }

    public static void showTeacherHome() {
        setRoot(new TeacherHomeView().getView());
    }

    public static void showTeacherHistory() {
        setRoot(new TeacherHistoryView().getView());
    }

    public static void showTeacherStudentList() {
        setRoot(new TeacherStudentListView().getView());
    }

    public static void showTeacherReportExport() {
        setRoot(new TeacherReportExportView().getView());
    }

    public static void showStudentDashboard() {
        setRoot(new StudentDashboardView().getView());
    }

    public static void showStudentProfile() {
        setRoot(new StudentProfileView().getView());
    }

    public static void showStudentPortfolio() {
        setRoot(new StudentPortfolioView().getRoot());
    }

    public static void showStudentNOC() {
        setRoot(new StudentNOCView().getRoot());
    }

    public static void showStudentMessaging() {
        setRoot(new StudentMessagingView().getView());
    }

    public static void showStudentChatDetail(String partnerName, boolean toAdmin) {
        setRoot(new StudentChatDetailView(partnerName, toAdmin).getView());
    }

    public static void showStudentEditProfile() {
        setRoot(new StudentEditProfileView().getView());
    }

    public static void showStudentChangePassword() {
        showUnifiedChangePassword("STUDENT");
    }

    public static void showStudentBiometrics() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Biometric Vault");
        alert.setHeaderText("Biometric Hardware Status");
        alert.setContentText("Security check: No compatible biometric hardware detected. Please use your master password for authentication.");
        alert.showAndWait();
    }

    public static void showTeacherDashboard() {
        setRoot(new TeacherDashboardView().getView());
    }

    public static void showTeacherProfile() {
        setRoot(new TeacherProfileView().getView());
    }

    public static void showTeacherEditProfile() {
        setRoot(new TeacherEditProfileView().getView());
    }

    public static void showThemeSelection() {
        setRoot(new ThemeSelectionView().getView());
    }

    public static void showNotificationPopup(javafx.scene.Node anchor) {
        javafx.scene.control.ContextMenu menu = new javafx.scene.control.ContextMenu();
        menu.getStyleClass().add("search-suggestions"); // reuse the dark/gold theme
        
        if (notificationHistory.isEmpty()) {
            javafx.scene.control.MenuItem empty = new javafx.scene.control.MenuItem("Zero system events logged.");
            empty.setDisable(true);
            menu.getItems().add(empty);
        } else {
            // Show last 7 notifications
            int start = Math.max(0, notificationHistory.size() - 7);
            for (int i = notificationHistory.size() - 1; i >= start; i--) {
                Notification n = notificationHistory.get(i);
                javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem(n.icon + " " + n.title + "\n" + n.desc);
                item.setStyle("-fx-text-fill: white; -fx-padding: 10px;");
                menu.getItems().add(item);
            }
        }
        
        menu.show(anchor, javafx.geometry.Side.BOTTOM, 0, 5);
    }

    public static Node getAvatarNode(String name, String path, double size) {
        javafx.scene.layout.StackPane pane = new javafx.scene.layout.StackPane();
        pane.setMaxSize(size, size);
        pane.setMinSize(size, size);
        
        Circle bg = new Circle(size / 2);
        // Important: Set fill via setFill, not setStyle, to avoid overriding patterns
        bg.setFill(javafx.scene.paint.Color.web("#262626"));
        bg.setStroke(javafx.scene.paint.Color.web("#ffffff", 0.1));
        
        if (path != null && !path.trim().isEmpty()) {
            try {
                java.io.File f = new java.io.File(path);
                if (f.exists()) {
                    Image img = new Image(f.toURI().toString(), size, size, true, true);
                    bg.setFill(new ImagePattern(img));
                }
            } catch (Exception e) { 
                System.err.println("Failed to load avatar from: " + path);
            }
        }
        
        javafx.scene.control.Label initial = new javafx.scene.control.Label(getInitials(name));
        initial.setStyle("-fx-text-fill: #fac736; -fx-font-weight: bold; -fx-font-size: " + (size * 0.4) + "px;");
        
        // Initials only visible if no image is successfully loaded
        initial.setVisible(bg.getFill() instanceof javafx.scene.paint.Color);
        
        pane.getChildren().addAll(bg, initial);
        return pane;
    }

    public static StudentProfile findStudentById(String id) {
        return studentDB.get(id);
    }

    public static TeacherProfile findTeacherByName(String name) {
        for (TeacherProfile t : teacherDB.values()) {
            if (t.name.equalsIgnoreCase(name)) return t;
        }
        return null;
    }

    public static AdminProfile findAdminByName(String name) {
        for (AdminProfile a : adminDB.values()) {
            if (a.name.equalsIgnoreCase(name)) return a;
        }
        return null;
    }

    public static Node getCurrentUserAvatar(double size) {
        if ("ADMIN".equals(currentUserRole)) return getAvatarNode(currentAdmin.name, currentAdmin.profilePicPath, size);
        if ("TEACHER".equals(currentUserRole)) return getAvatarNode(currentTeacher.name, currentTeacher.profilePicPath, size);
        if ("STUDENT".equals(currentUserRole)) return getAvatarNode(currentStudent.name, currentStudent.profilePicPath, size);
        return getAvatarNode("User", null, size);
    }

    public static void showTeacherChangePassword() {
        showUnifiedChangePassword("TEACHER");
    }

    public static void showTeacherMessaging() {
        setRoot(new TeacherMessagingView().getView());
    }

    public static void showTeacherChatDetail(String studentName) {
        setRoot(new TeacherChatDetailView(studentName).getView());
    }

    public static void showTeacherStudentProfile(String name, String role, String id) {
        setRoot(new TeacherStudentProfileView(name, role, id).getView());
    }

    public static void showAdminDashboard() {
        setRoot(new AdminDashboardView().getView());
    }

    public static void showAdminProfile() {
        setRoot(new AdminProfileView().getView());
    }

    public static void showAdminStudentProfile(String name, String role, String id) {
        setRoot(new AdminStudentProfileView(name, role, id).getView());
    }

    public static void showAdminNotifications() {
        setRoot(new AdminNotificationsView().getView());
    }

    public static void showAdminAllArchive() {
        setRoot(new AdminAllArchiveView().getView());
    }

    public static void showAdminEditProfile() {
        setRoot(new AdminEditProfileView().getView());
    }

    public static void showAdminFilter() {
        showFilterView(() -> App.showAdminDashboard());
    }

    public static void showFilterView(Runnable backAction) {
        setRoot(new FilterView(backAction).getView());
    }

    public static void showAdminShortlist() {
        setRoot(new AdminShortlistView().getView());
    }

    public static void showAdminMessaging() {
        setRoot(new AdminMessagingView().getView());
    }

    public static void showAdminChatDetail(String studentName) {
        setRoot(new AdminChatDetailView(studentName).getView());
    }

    public static void showAdminChangePassword() {
        showUnifiedChangePassword("ADMIN");
    }

    private static void showUnifiedChangePassword(String role) {
        setRoot(new UnifiedChangePasswordView(role).getView());
    }

    public static void showAdminPurgeData() {
        setRoot(new AdminPurgeDataView().getView());
    }

    public static void showHome() {
        if ("Admin".equalsIgnoreCase(currentUserRole)) {
            showAdminDashboard();
        } else if ("Teacher".equalsIgnoreCase(currentUserRole)) {
            showTeacherHome();
        } else if ("Student".equalsIgnoreCase(currentUserRole)) {
            showStudentDashboard();
        } else {
            showOptionView();
        }
    }

    public static void showPublicTeacherProfile(String name, String dept) {
        setRoot(new PublicProfileView(name, "Teacher / Faculty", dept).getView());
    }

    public static void showPublicAdminProfile(String name, String role) {
        setRoot(new PublicProfileView(name, role, "Administration Hub").getView());
    }

    public static void showForeignProfile(String name) {
        // 0. SELF-LOOKUP CHECK (Prevents switching own profile to read-only)
        if ("Student".equalsIgnoreCase(currentUserRole) && currentStudent != null && currentStudent.name.equalsIgnoreCase(name)) {
            showStudentProfile(); return;
        }
        if ("Teacher".equalsIgnoreCase(currentUserRole) && currentTeacher != null && currentTeacher.name.equalsIgnoreCase(name)) {
            showTeacherProfile(); return;
        }
        if ("Admin".equalsIgnoreCase(currentUserRole) && currentAdmin != null && currentAdmin.name.equalsIgnoreCase(name)) {
            showAdminProfile(); return;
        }

        // 1. Search Students
        for (StudentProfile s : studentDB.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                if ("Admin".equalsIgnoreCase(currentUserRole)) {
                    showAdminStudentProfile(s.name, s.major, s.studentId);
                } else {
                    // Everyone else sees the standard Teacher-Student review view (read-only)
                    showTeacherStudentProfile(s.name, s.major, s.studentId);
                }
                return;
            }
        }
        
        // 2. Search Teachers
        for (TeacherProfile t : teacherDB.values()) {
            if (t.name.equalsIgnoreCase(name)) {
                showPublicTeacherProfile(t.name, t.department);
                return;
            }
        }

        // 3. Search Admins
        for (AdminProfile a : adminDB.values()) {
            if (a.name.equalsIgnoreCase(name)) {
                showPublicAdminProfile(a.name, a.role);
                return;
            }
        }
    }

    public static String saveProfilePicture(File source) {
        try {
            File dir = new File("profile_pics");
            if (!dir.exists()) dir.mkdirs();
            String fileName = UUID.randomUUID().toString() + "_" + source.getName();
            Path dest = Paths.get("profile_pics", fileName);
            Files.copy(source.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            return dest.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String saveDocument(File source) {
        try {
            File dir = new File("portfolio_docs");
            if (!dir.exists()) dir.mkdirs();
            String fileName = UUID.randomUUID().toString() + "_" + source.getName();
            Path dest = Paths.get("portfolio_docs", fileName);
            Files.copy(source.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
            return dest.toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        App.launch(args);
    }
}

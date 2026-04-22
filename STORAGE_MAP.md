# 🗄️ Certiflow Persistence Registry (Database Map)

This document provides a technical overview of where application data is stored on the local workstation. Certiflow uses a **Vault-based Serialization System** instead of a traditional SQL database to ensure fast, local-first performance and easy portability of user profiles.

---

## 🏗️ Core Database Files

| File Path | Description | Access Level | Data Type |
| :--- | :--- | :--- | :--- |
| `admin_vault.dat` | Master Admin Profile (Name, Role, Phone, ID, Theme) | **Admin Only** | Serialized Object |
| `teacher_vault.dat` | Teacher Profile & Personalization Preferences | **Teacher Only** | Serialized Object |
| `validations_vault.dat` | Master Student Registry: All NOC Approvals/Rejections | **Teachers** | Key-Value Map |
| `notifications_vault.dat` | History of all security alerts and system events | **Shared** | List Object |
| `admin_vault_chats.dat` | Historical messaging logs for Administrative chat | **Admin Only** | Serialized Map |
| `teacher_vault_chats.dat` | Messaging logs for Teacher-Student interactions | **Teacher Only** | Serialized Map |

---

## 🖼️ Media & Binary Storage

### `/profile_pics/` Directory
This folder acts as the **Local Avatar Cache**. When a user selects a profile picture, Certiflow copies the file here to ensure:
1. **Persistence**: The photo remains even if the original file is deleted from the downloads folder.
2. **Speed**: Instant loading of high-resolution avatars during session start.

---

## 🔐 Technical Implementation
Data is managed via the `App.java` persistence layer using standard Java Serialization. 

### Key Methods:
- `App.saveProfile()` / `App.saveTeacherProfile()`: Serializes identity objects.
- `App.saveValidations()`: Commits all NOC decision-making to disk.
- `App.saveHistory()`: Exports messaging histories to respective role vaults.

> [!NOTE]
> All `.dat` files are binary. To view them in a human-readable format, you can use the **Admin Dashboard** or the **Teacher History View** within the application, which deserializes this data for the interface.

# 📱 MyChat Android Application

Welcome to **MyChat**, an Android application designed to manage and display user data in real-time, based on their roles. The app integrates with Firebase to fetch and display user details (name, mobile number, and role) dynamically, using a `RecyclerView` for a smooth and engaging user interface.

### 📝 Features:
- **Real-Time Data**: Fetches and displays user information in real-time from Firebase.
- **User Roles**: Displays users based on specific roles (e.g., CR, BR and Faculty).
- **User Interface**: Uses a `RecyclerView` to display the list of users dynamically.
- **WhatsApp Automation**: Contains an `AccessibilityService` to interact with WhatsApp and automatically send messages.
- **Firebase Integration**: Real-time database to store and retrieve users.

---

## 🔧 Technologies Used:
- **Android SDK** (Java)
- **Firebase Realtime Database** for dynamic data retrieval
- **RecyclerView** for displaying the list of users
- **AccessibilityService** for WhatsApp automation

---

## 🛠️ Setup Instructions

Follow these steps to get MyChat up and running on your local machine.

### 1. Clone the Repository
```bash
git clone https://github.com/SaeeDesai/MyChat.git
```
### 1. Open the Project in Android Studio
- Open Android Studio.
- Select **Open an existing project** and choose the project folder you just cloned.

### 2. Setup Firebase 🔥
- Go to [Firebase Console](https://console.firebase.google.com/).
- Create a new project (or use an existing one).
- Add Firebase Realtime Database and enable it.
- Download the `google-services.json` file from Firebase Console and place it in the `app/` directory of the project.

### 3. Sync Gradle 🔄
- Click **Sync Now** in Android Studio to sync the Firebase SDK and dependencies.

## Code Structure 🧑‍💻

### 1. `MainActivity.java` 🚀
- **Purpose**: The main entry point for the app, responsible for navigating between different activities and setting up the Firebase instance.
- **Functions**: 
    - Initializes Firebase.
    - Handles authentication.
    - Navigates to `StudentActivity`.

### 2. `MyAccessibilityService.java` 📲
- **Purpose**: Contains an accessibility service for interacting with WhatsApp.
- **Functionality**: Automates the process of sending messages on WhatsApp when certain conditions are met (e.g., window change or app focus).

### 3. `MyAdapter.java` 🔄
- **Purpose**: Adapter for the RecyclerView that binds user data to the list items in the UI.
- **Functionality**: Maps the user details (name, mobile number, and role) to the RecyclerView views.

### 4. `StudentActivity.java` 🧑‍🎓
- **Purpose**: Displays a list of users in the RecyclerView, categorized by their role (e.g., CR, BR).
- **Functionality**: Fetches the user data from Firebase and displays it based on their role.

### 5. `User.java` 👤
- **Purpose**: A model class that represents a user with properties like name, mobile number, and role.
- **Functionality**: Used to parse the user data from Firebase into Java objects.

## How It Works ⚡

### Fetching Data 📡:
- The app fetches user data in real-time from Firebase Realtime Database. It listens for changes in the database and updates the UI accordingly.

### Displaying Data 📊:
- The user data is displayed in a **RecyclerView**, with each item showing the user's name, mobile number, and role.
- The `RecyclerView.Adapter` is responsible for binding the data to the UI.

### Handling Different Roles 🎓:
- The app filters users based on their roles (CR and BR), ensuring that only users with specific roles are displayed.

### WhatsApp Automation 🤖:
- The `AccessibilityService` listens for window state changes in WhatsApp and automates the sending of messages when certain conditions are met.

## Demo 🎬
- **Firebase Integration**: Users are fetched from Firebase based on their roles (CR, BR).
- **UI**: Users are displayed in a beautiful and smooth RecyclerView list.
- **Automation**: The app interacts with WhatsApp through the AccessibilityService to send messages automatically.

## Troubleshooting ⚠️

### Firebase Connectivity Issues 🚫:
- Ensure that your Firebase project is properly configured and the `google-services.json` file is correctly placed.

### Permissions 🛠️:
- Make sure to grant all necessary permissions for the app, especially for the AccessibilityService to work correctly.


# Auto École Management System

A comprehensive JavaFX desktop application for managing driving school operations, including student management, instructor scheduling, exam tracking, financial management, and more.

## 📋 Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Database Setup](#database-setup)
- [Dependencies](#dependencies)
- [Building the Project](#building-the-project)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### Student Management
- Add, modify, and manage candidate information
- Store candidate photos and personal details
- Track candidate registration dates
- Manage candidate blood groups and contact information

### Instructor Management
- Manage instructor profiles
- Assign instructors to exams and sessions
- Track instructor availability and assignments

### Vehicle Management
- Register and manage school vehicles
- Track vehicle assignments to sessions and exams

### Exam Management
- Schedule and manage driving exams
- Assign candidates to exams
- Track exam results and status
- Manage exam instructors

### Session Management
- Create and manage code/theory sessions
- Assign candidates to sessions
- Track session attendance

### Financial Management
- Track candidate payments (versements)
- Manage expenses (dépenses)
- Generate financial reports
- Monitor accounting (quantabilité)

### Inscription Management
- Handle candidate registrations
- Track registration dates and details
- Manage candidate enrollment status

### User Management
- Secure user authentication
- Role-based access control
- User profile management

### Additional Features
- Bilingual support (French/Arabic)
- Modern Material Design UI
- Dashboard with statistics and charts
- Print functionality
- Search and filter capabilities

## 🛠 Technologies Used

- **Java**: Core programming language
- **JavaFX**: Desktop application framework
- **MySQL**: Database management system
- **JFoenix**: Material Design components for JavaFX
- **MaterialFX**: Additional Material Design components
- **ControlsFX**: Enhanced JavaFX controls
- **AnimateFX**: Animation library for JavaFX
- **jBCrypt**: Password hashing library
- **jfxtras**: Additional JavaFX controls (Agenda, etc.)
- **NetBeans**: IDE and build system (Ant)

## 📦 Prerequisites

Before running this application, ensure you have the following installed:

- **Java JDK 8 or higher** (JavaFX included)
- **MySQL Server 5.7 or higher**
- **NetBeans IDE** (recommended) or any Java IDE
- **MySQL Connector/J** (included in dependencies)

## 🚀 Installation

1. **Clone or download the repository**
   ```bash
   git clone https://github.com/youyou-dev4/autoEcole.git
   cd auto_ecole
   ```

2. **Set up MySQL Database**
   - Create a MySQL database named `auto_ecole`
   - Import the database schema using the provided documentation:
     📄 [Database Architecture & Schema (PDF)](shema_bdd.pdf)
   - See [Database Setup](#database-setup) for more details

3. **Configure Database Connection**
   - Open `src/Modele/BDD_Connexion.java`
   - Update the connection parameters:
     ```java
     private static final String URL = "jdbc:mysql://127.0.0.1:3306/auto_ecole";
     private static final String USERNAME = "root";
     private static final String PASSWORD = "your_password";
     ```

4. **Open in NetBeans**
   - Launch NetBeans IDE
   - Open the project: `File > Open Project > Select auto_ecole folder`

## ⚙️ Configuration

### Database Configuration

Edit `src/Modele/BDD_Connexion.java` to match your MySQL setup:

```java
private static final String URL = "jdbc:mysql://localhost:3306/auto_ecole";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Application Settings

Default values and application settings can be configured through the `GestionnaireValeurParDefaut` class.

## 💻 Usage

### Running the Application

1. **From NetBeans:**
   - Right-click on the project
   - Select `Run` or press `F6`

2. **From Command Line:**
   ```bash
   cd dist
   java -jar auto_ecole.jar
   ```

3. **Login:**
   - Use your credentials to log in
   - Default credentials may need to be set up in the database

### Main Features Navigation

- **Home Dashboard**: Overview of statistics and recent activities
- **Candidates**: Manage student records
- **Instructors**: Manage instructor profiles
- **Vehicles**: Manage school vehicles
- **Exams**: Schedule and track exams
- **Sessions**: Manage code/theory sessions
- **Inscriptions**: Handle registrations
- **Payments**: Track candidate payments
- **Expenses**: Manage school expenses
- **Accounting**: Financial overview and reports
- **Users**: Manage system users
- **Settings**: Configure application parameters

## 📁 Project Structure

```
auto_ecole/
├── src/
│   ├── Controller/          # FXML controllers
│   │   ├── HomeController.java
│   │   ├── AjouterCondidatController.java
│   │   ├── Se_ConnecterController.java
│   │   └── ...
│   ├── Modele/              # Data models and business logic
│   │   ├── Auto_ecole.java  # Main application class
│   │   ├── BDD_Connexion.java
│   │   ├── Condidat.java
│   │   ├── Moniteur.java
│   │   ├── Vehicule.java
│   │   ├── Examen.java
│   │   └── ...
│   ├── View/                # FXML UI files
│   │   ├── home.fxml
│   │   ├── Se_Connecter.fxml
│   │   ├── version_arabe/   # Arabic version UI
│   │   └── ...
│   ├── icon_image/          # Application icons
│   └── images/              # Application images
├── bibliotheque/            # External JAR dependencies
├── build/                   # Compiled classes
├── dist/                    # Distribution files
│   ├── auto_ecole.jar
│   └── lib/                 # Library dependencies
├── nbproject/               # NetBeans project configuration
├── build.xml                # Ant build script
└── README.md
```

## 🗄 Database Setup

1. **Create the database:**
   ```sql
   CREATE DATABASE auto_ecole;
   USE auto_ecole;
   ```

2. **Create required tables:**
   The application expects tables for:
   - `utilisateur` (users)
   - `condidat` (candidates)
   - `moniteur` (instructors)
   - `vehicule` (vehicles)
   - `examen` (exams)
   - `seance` (sessions)
   - `inscription` (registrations)
   - `versement` (payments)
   - `depence` (expenses)
   - And related junction tables

3. **Set up initial user:**
   ```sql
   INSERT INTO utilisateur (username, password) 
   VALUES ('admin', 'your_hashed_password');
   ```

## 📚 Dependencies

All dependencies are included in the `bibliotheque/` folder:

- `adapter-0.2.0.jar`
- `AnimateFX-1.2.0.jar`
- `com.mysql.jdbc_5.1.5.jar` / `mysql-connector-java-5.1.42-bin.jar`
- `controlsfx-8.40.11.jar`
- `fontawesomefx-8.9.jar`
- `jBCrypt-0.4.jar`
- `jfoenix-8.0.8.jar`
- `jfxtras-all-8.0-r6.jar`
- `materialfx-11.13.5.jar`
- `virtualizedfx-11.2.6.1.jar`

## 🔨 Building the Project

### Using NetBeans

1. Open the project in NetBeans
2. Right-click on the project
3. Select `Clean and Build`
4. The JAR file will be created in the `dist/` folder

### Using Ant (Command Line)

```bash
ant clean
ant jar
```

The executable JAR will be in the `dist/` directory.

## 🎨 UI Features

- **Modern Material Design**: Clean and intuitive interface
- **Responsive Layout**: Adapts to different screen sizes
- **Bilingual Support**: French and Arabic language options
- **Dark/Light Theme**: Material Design themes
- **Animations**: Smooth transitions and animations
- **Charts and Statistics**: Visual data representation

## 🔒 Security

- Password hashing using jBCrypt
- Secure database connections
- User authentication and authorization
- Input validation

## 🐛 Troubleshooting

### Connection Issues

- Ensure MySQL server is running
- Verify database credentials in `BDD_Connexion.java`
- Check that the database `auto_ecole` exists

### Build Issues

- Ensure all JAR files are in the `bibliotheque/` folder
- Check that JavaFX is properly configured
- Verify Java version compatibility (JDK 8+)

### Runtime Issues

- Check JavaFX runtime is available
- Verify all dependencies are in the classpath
- Check database connection status

## 📝 Notes

- The application uses French terminology in the codebase (e.g., "Condidat" instead of "Candidate")
- Arabic language support is available in the `version_arabe` folder
- Default photos for candidates are stored in `PhotoCondidat/`
- Application icons are in the `icon_image/` folder

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE) (or specify your license).

## 👤 Author

**Younes MATOUB**

## 🙏 Acknowledgments

- JavaFX community
- JFoenix and MaterialFX developers
- All open-source library contributors

---

For more information or support, please open an issue in the repository.


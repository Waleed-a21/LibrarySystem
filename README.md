# LibrarySystem - Bibliotheksverwaltung

Ein robustes Bibliotheksverwaltungssystem (Library Management System), entwickelt im Rahmen des Moduls **Programmierung 3**. Dieses Projekt demonstriert die Anwendung moderner Java-Technologien, Clean Code Prinzipien, einer sauberen Drei-Schichten-Architektur und Test-Driven Development (TDD).

## 🚀 Technologien & Tools

Das Projekt basiert auf folgendem Tech-Stack:

| Technologie | Verwendung |
|-------------|------------|
| **Java 17** | Programmiersprache |
| **Maven** | Build-Management und Abhängigkeitsverwaltung |
| **jOOQ** | Typsichere SQL-Abfragen und Datenbankinteraktion |
| **SQLite** | Eingebettete, relationale Datenbank (keine externe Installation nötig) |
| **JUnit 5** | Unit-Tests und TDD |

## ✨ Features

* **Interaktive CLI (Konsole):** Benutzerfreundliches Menü zur umfassenden Verwaltung.
* **Bücherverwaltung:** Hinzufügen, Suchen (nach ISBN) und Löschen von Büchern.
* **Mitgliederverwaltung:** Neues Modul zum Hinzufügen von Mitgliedern.
* **Ausleihsystem:** Ausleihen (Checkout) und Zurückgeben (Return) von Büchern inklusive Verfügbarkeitsprüfung.
* **Datenpersistenz:** Automatische Initialisierung der Datenbank (`library.db`). Daten bleiben nach dem Neustart erhalten.
* **Architektur:** Saubere **Drei-Schichten-Architektur**:
  * *Presentation Layer:* `ConsoleUI`
  * *Service Layer:* `LibraryService` (Geschäftslogik)
  * *Repository Layer:* `BookRepository`, `LoanRepository`, `MemberRepository` (Datenbankzugriff)

## 🛠 Installation & Ausführung

### Voraussetzungen

Stellen Sie sicher, dass **Java 17** (oder höher) und **Maven** installiert sind:

```bash
java -version
mvn -version
```

### Projekt klonen

```bash
git clone <repository-url>
cd LibrarySystem
```

### Kompilieren

```bash
mvn clean compile
```

### Anwendung starten

```bash
mvn exec:java
```

### Tests ausführen

```bash
mvn test
```

## 📖 Verwendung

Nach dem Start erscheint folgendes Hauptmenü (`ConsoleUI`):

```
===========================================
       📚 LIBRARY SYSTEM v2.0 📚
===========================================
-------------------------------------------
                 MAIN MENU
-------------------------------------------
  1. ➕ Add a Book
  2. 🔍 Search for a Book (by ISBN)
  3. 🗑️  Delete a Book (by ID)
  4. 👤 Add a Member
  5. 📤 Checkout a Book
  6. 📥 Return a Book
  7. 🚪 Exit
-------------------------------------------
Enter your choice:
```

### Beispiel: Buch ausleihen

1. Wähle Option `5`
2. Gib die ID des gewünschten Buches und die ID des Mitglieds ein
3. Das Buch wird ausgeliehen und die verfügbare Anzahl automatisch reduziert.

## 📁 Projektstruktur

```
LibrarySystem/
├── pom.xml                          # Maven Konfiguration
├── library.db                       # SQLite Datenbank (wird automatisch erstellt)
├── RETROSPECTIVE.md                 # Projektrückblick
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java            # Composition Root (Startpunkt)
│   │   │   ├── repository/          # Repository-Schicht (Datenbankzugriff)
│   │   │   │   ├── DatabaseManager.java
│   │   │   │   ├── BookRepository.java
│   │   │   │   ├── MemberRepository.java
│   │   │   │   └── LoanRepository.java
│   │   │   ├── service/             # Service-Schicht (Geschäftslogik)
│   │   │   │   └── LibraryService.java
│   │   │   └── ui/                  # Präsentations-Schicht (User Interface)
│   │   │       └── ConsoleUI.java
│   │   └── resources/db/
│   │       └── schema.sql           # Datenbankschema
│   └── test/java/org/example/       # Unit-Tests
└── README.md
```

## 👤 Autor

**Walid** – Programmierung 3, 3. Semester

## 📄 Lizenz

Dieses Projekt ist zu Lernzwecken im Rahmen eines Hochschulmoduls entstanden.

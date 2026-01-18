# LibrarySystem - Bibliotheksverwaltung

Ein robustes Bibliotheksverwaltungssystem (Library Management System), entwickelt im Rahmen des Moduls **Programmierung 3**. Dieses Projekt demonstriert die Anwendung moderner Java-Technologien, Clean Code Prinzipien und Test-Driven Development (TDD).

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

* **Interaktive CLI (Konsole):** Benutzerfreundliches Menü zum Hinzufügen, Suchen und Löschen von Büchern.
* **Datenpersistenz:** Automatische Initialisierung der Datenbank (`library.db`). Daten bleiben nach dem Neustart erhalten (kein Datenverlust).
* **Architektur:** Verwendung des **Repository Pattern** (`BookRepository`) zur Trennung der Datenbanklogik von der Geschäftslogik.
* **Clean Code:** Zentralisierte Datenbank-Konfiguration im `DatabaseManager` (Vermeidung von Code-Duplizierung und `DROP TABLE` Problemen).

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

Nach dem Start erscheint folgendes Menü:

```
-------------------------------------------
                 MAIN MENU
-------------------------------------------
  1. ➕ Add a Book
  2. 🔍 Search for a Book (by ISBN)
  3. 🗑️  Delete a Book (by ID)
  4. 🚪 Exit
-------------------------------------------
Enter your choice:
```

### Beispiel: Buch hinzufügen

1. Wähle Option `1`
2. Gib Titel, Autor, ISBN und Anzahl der Exemplare ein
3. Das Buch wird in der Datenbank gespeichert

## 📁 Projektstruktur

```
LibrarySystem/
├── pom.xml                          # Maven Konfiguration
├── library.db                       # SQLite Datenbank (wird automatisch erstellt)
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java            # Hauptprogramm mit CLI-Menü
│   │   │   ├── DatabaseManager.java # Datenbankverbindung & Initialisierung
│   │   │   ├── BookRepository.java  # CRUD-Operationen für Bücher
│   │   │   ├── MemberRepository.java
│   │   │   └── LoanRepository.java
│   │   └── resources/db/
│   │       └── schema.sql           # Datenbankschema
│   └── test/java/org/example/       # Unit-Tests
└── README.md
```

## 👤 Autor

**Walid** – Programmierung 3, 3. Semester

## 📄 Lizenz

Dieses Projekt ist zu Lernzwecken im Rahmen eines Hochschulmoduls entstanden.

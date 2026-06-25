# 🔄 Projektrückblick (Retrospektive)

Diese Retrospektive fasst unsere Erfahrungen, Lernfortschritte und Herausforderungen während der Entwicklung des Library Systems (Programmierung 3) zusammen.

## 🟢 Was lief gut?
* **Einsatz von jOOQ & SQLite:** Die Integration der Datenbank über jOOQ hat hervorragend funktioniert. Die generierten Klassen (Records) haben die Typsicherheit extrem erhöht und SQL-Fehler vermieden.
* **Architektur-Umstellung:** Die schrittweise Refaktorierung des anfangs monolithischen Codes (`Main.java`) in eine saubere **Drei-Schichten-Architektur** (Presentation, Service, Repository) war ein voller Erfolg. Der Code ist nun viel wartbarer und leichter zu testen.
* **Agiles Arbeiten & CI/CD:** Die Nutzung von GitHub Actions zur automatischen Überprüfung der Tests bei jedem Push (Continuous Integration) hat uns sofort auf Fehler (wie z. B. fehlende Constraint-Werte in der Datenbank) aufmerksam gemacht, sodass diese schnell behoben werden konnten.

## 🟡 Was haben wir gelernt?
* **Dependency Injection:** Wir haben gelernt, wie wichtig es ist, Abhängigkeiten (wie die Repositories) von außen in den Service (`LibraryService`) hineinzugeben, anstatt sie hart im Code zu instanziieren.
* **Datenbank-Constraints:** Fehler bei `NOT NULL`-Constraints (z. B. beim fehlenden `membership_date`) haben uns gezeigt, wie wichtig es ist, das Datenbankschema stets genau mit den INSERT-Methoden abzugleichen.
* **Transaktionen:** Bei Ausleihe (`checkoutBook`) und Rückgabe (`returnBook`) haben wir gelernt, wie wichtig relationale Transaktionen sind, um Dateninkonsistenzen (z. B. Buch ausgeliehen, aber Anzahl der verfügbaren Kopien nicht reduziert) zu vermeiden.

## 🔴 Was machen wir beim nächsten Mal anders? (Verbesserungspotenzial)
* **Test-Driven Development (TDD):** Beim nächsten Projekt wollen wir versuchen, die Tests *vor* der eigentlichen Implementierung zu schreiben, um Randfälle noch besser abzudecken.
* **Bessere Git-Strategie:** Statt alles direkt auf dem `master`-Branch zu pushen, sollten in zukünftigen Projekten "Feature-Branches" (z. B. `feature/loan-system`) und Pull Requests genutzt werden, um Code-Konflikte zu vermeiden.
* **Grafische Benutzeroberfläche (GUI):** Das aktuelle System basiert auf einer Konsolenanwendung (CLI). Für ein besseres Nutzererlebnis könnte das Projekt in Zukunft um eine JavaFX- oder Web-Oberfläche (Spring Boot) erweitert werden.

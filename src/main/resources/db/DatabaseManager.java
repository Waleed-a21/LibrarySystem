package db;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // SQLite-Datenbank (Datei wird automatisch erstellt)
    private static final String JDBC_URL = "jdbc:sqlite:library.db";

    private static DSLContext context;

    /**
     * Liefert ein jOOQ DSLContext-Objekt für SQLite zurück.
     */
    public static DSLContext getContext() {
        if (context == null) {
            try {
                Connection connection = DriverManager.getConnection(JDBC_URL);
                context = DSL.using(connection, SQLDialect.SQLITE);
            } catch (SQLException e) {
                throw new RuntimeException("Fehler bei der Verbindung zur SQLite-Datenbank", e);
            }
        }
        return context;
    }
}

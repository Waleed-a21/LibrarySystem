package org.example;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static Connection connection;

    // =========================
    // CONNECTION
    // =========================
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        }
        return connection;
    }

    // =========================
    // jOOQ CONTEXT
    // =========================
    public static DSLContext getDSLContext() throws SQLException {
        return DSL.using(getConnection(), SQLDialect.SQLITE);
    }

    // =========================
    // INIT DATABASE (SCHEMA)
    // =========================
    public static void initializeDatabase() throws SQLException, IOException {
        System.out.println("🔧 Initializing database...");
        Connection conn = getConnection();
        String schema = readSchemaFile();

        String[] statements = schema.split(";");

        try (Statement stmt = conn.createStatement()) {
            for (String sql : statements) {
                String trimmed = sql.trim();
                // Skip empty lines and comments
                if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                    stmt.execute(trimmed);
                }
            }
        }
        System.out.println("✅ Database schema initialized.");
    }

    private static String readSchemaFile() throws IOException {
        InputStream is = DatabaseManager.class
                .getClassLoader()
                .getResourceAsStream("db/schema.sql");

        if (is == null) {
            throw new IOException("❌ schema.sql not found in resources/db/");
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // =========================
    // CLEANUP
    // =========================
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ignored) {
        }
    }
}

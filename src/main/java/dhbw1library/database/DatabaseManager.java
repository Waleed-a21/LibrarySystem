package dhbw1library.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {


    private static final String URL = "jdbc:sqlite:library.db";

    public static DSLContext getContext() {
        try {
            Connection connection = DriverManager.getConnection(URL);
            return DSL.using(connection, SQLDialect.SQLITE);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database: " + e.getMessage(), e);
        }
    }
}
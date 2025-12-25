package org.example;


import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testDatabaseConnection() {
        System.out.println("🔄 Testing database connection...");

        DSLContext context = DatabaseManager.getContext();

        assertNotNull(context, "DSLContext should not be null");

        var result = context.selectOne().fetch();

        System.out.println("✅ Database Connection Successful!");
    }
}
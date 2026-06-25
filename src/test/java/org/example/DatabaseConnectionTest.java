package org.example;

import org.example.repository.DatabaseManager;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionTest {

    @Test
    void testDatabaseConnection() throws Exception {
        System.out.println("🧪 Testing database connection...");

        DSLContext context = DatabaseManager.getDSLContext();

        assertNotNull(context, "DSLContext should not be null");

        context.selectOne().fetch();

        System.out.println("✅ Database Connection Successful!");
    }
}

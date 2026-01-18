package org.example;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    private DSLContext ctx;
    private MemberRepository memberRepo;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        DatabaseManager.initializeDatabase(); // Ensure DB is init
        ctx = DatabaseManager.getDSLContext();
        memberRepo = new MemberRepository(ctx);

        // Clean up members table to ensure fresh state (optional but good for
        // uniqueness tests)
        ctx.deleteFrom(org.example.db.Tables.MEMBERS).execute();
    }

    @Test
    void testAddMember() {
        int id = memberRepo.addMember("Test User", "test@user.com");
        assertTrue(id > 0, "Member ID should be positive");
    }

    @Test
    void testMemberExists() {
        int id = memberRepo.addMember("Test User 2", "test2@user.com");
        assertTrue(memberRepo.memberExists(id), "Member should exist after adding");
        assertFalse(memberRepo.memberExists(9999), "Non-existent member should not exist");
    }
}

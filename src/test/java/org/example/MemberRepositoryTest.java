package org.example;

import org.example.db.Tables;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    private DSLContext ctx;
    private MemberRepository memberRepo;

    @BeforeEach
    void setUp() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");
        ctx = DSL.using(conn, SQLDialect.SQLITE);
        memberRepo = new MemberRepository(ctx);

        // Initialize DB
        try {
            DatabaseManager.initializeDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Aufräumen vor jedem Test
        ctx.deleteFrom(Tables.LOANS).execute();
        ctx.deleteFrom(Tables.MEMBERS).execute();
    }

    @Test
    void testAddMember() {
        // Act
        memberRepo.addMember("Max Mustermann", "max@uni.de");

        // Assert
        boolean exists = ctx.fetchExists(
                ctx.selectFrom(Tables.MEMBERS).where(Tables.MEMBERS.EMAIL.eq("max@uni.de")));
        assertTrue(exists, "Member should be saved in database");
    }

    @Test
    void testMemberExists() {
        // Arrange
        memberRepo.addMember("Anna", "anna@uni.de");
        int memberId = ctx.selectFrom(Tables.MEMBERS).fetchOne().getId();

        // Act & Assert
        assertTrue(memberRepo.memberExists(memberId));
        assertFalse(memberRepo.memberExists(99999), "Non-existing ID should return false");
    }
}
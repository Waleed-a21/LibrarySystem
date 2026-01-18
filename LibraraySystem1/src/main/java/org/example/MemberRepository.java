package org.example;

import org.example.db.Tables;
import org.jooq.DSLContext;
import java.time.LocalDate;

public class MemberRepository {
    private final DSLContext ctx;

    public MemberRepository(DSLContext ctx) {
        this.ctx = ctx;
    }

    public int addMember(String name, String email) {
        return ctx.insertInto(Tables.MEMBERS)
                .set(Tables.MEMBERS.NAME, name)
                .set(Tables.MEMBERS.EMAIL, email)
                .set(Tables.MEMBERS.MEMBERSHIP_DATE, LocalDate.now().toString())
                .returningResult(Tables.MEMBERS.ID)
                .fetchOne()
                .getValue(Tables.MEMBERS.ID);
    }

    public boolean memberExists(int memberId) {
        return ctx.fetchExists(
                ctx.selectFrom(Tables.MEMBERS).where(Tables.MEMBERS.ID.eq(memberId)));
    }
}
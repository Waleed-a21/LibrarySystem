package org.example;

import org.jooq.DSLContext;
//Lager
public class MemberRepository {
    private final DSLContext ctx;

    public MemberRepository(DSLContext ctx) {
        this.ctx = ctx;
    }
    //Neues Mitglied
    public  void AddMember(String name, String email){

    }
}

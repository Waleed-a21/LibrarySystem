package org.example;

import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.db.Tables;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookRepositoryTest {

    private DSLContext ctx;
    private BookRepository repository;

    @BeforeEach
    void setup() {
        // تجهيز الاتصال وتنظيف الجدول قبل كل اختبار
        ctx = DatabaseManager.getContext();
        ctx.deleteFrom(Tables.BOOKS).execute();
        repository = new BookRepository(ctx);
    }

    @Test
    void testAddBook() {
        // الخطوة 1: إضافة كتاب (لاحظ أننا أضفنا الرقم 5 كعدد نسخ)
        repository.addBook("Clean Code", "Robert C. Martin", "978-0132350884", 5);

        // الخطوة 2: التحقق من أن الكتاب تم إضافته
        var result = ctx.selectFrom(Tables.BOOKS).fetch();

        assertEquals(1, result.size(), "يجب أن يكون هناك كتاب واحد في قاعدة البيانات");
        assertEquals("Clean Code", result.get(0).getTitle());
    }
}
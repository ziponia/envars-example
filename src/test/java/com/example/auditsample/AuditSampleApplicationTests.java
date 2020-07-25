package com.example.auditsample;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;

import java.util.Optional;

@SpringBootTest
class AuditSampleApplicationTests {

    Logger log = LoggerFactory.getLogger(AuditSampleApplicationTests.class);

    @Autowired
    private BookRepository bookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void updateTest() {

        Book book1 = new Book();
        book1.setIdx(1L);
        book1.setTitle("일요일 일요일밤");
        book1.setAuthor("화염의지배자 불닭볶음닭");

        bookRepository.save(book1);

        System.out.println("1번 book name 을 변경");
        Book book = bookRepository.findById(1L).get();
        System.out.println(book.toString());

        book.setTitle("일요일은 화요일밤에");
        bookRepository.save(book);

        book.setAuthor("커비");
        bookRepository.save(book);

        System.out.println(book.toString());

        System.out.println("1번 book 의 리비전을 역으로 조회");
        Revisions<Integer, Book> findBook1Revisions = bookRepository.findRevisions(1L);

        findBook1Revisions.reverse().getContent()
                .forEach(history -> {
                    Book historyOfBook1 = history.getEntity();
                    String revisionType = history.getMetadata().getRevisionType().name();
                    System.out.println(revisionType + " " + historyOfBook1.toString());
                });

        System.out.println("1번 book 을 첫번째 리비전으로 롤백");
        Revision<Integer, Book> findBook1 = bookRepository.findRevision(1L, 1).orElse(null);
        if (findBook1 != null) {
            Book b = findBook1.getEntity();
            System.out.println(b.toString());

            bookRepository.save(b);

            Book originalBook = bookRepository.findById(1L).orElse(null);
            System.out.println(originalBook.toString());
        }

    }
}

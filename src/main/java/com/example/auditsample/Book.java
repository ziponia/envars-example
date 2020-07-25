package com.example.auditsample;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Audited
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @Column(name = "book_id")
    private Long idx;

    private String title;

    private String author;
}

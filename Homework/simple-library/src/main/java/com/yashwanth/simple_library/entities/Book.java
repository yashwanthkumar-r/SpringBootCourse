package com.yashwanth.simple_library.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"authors"})
@AllArgsConstructor
@Table()
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String book_name;

    @Column(nullable = false)
    private Long edition;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")

    )
    private List<Author> authors;


}

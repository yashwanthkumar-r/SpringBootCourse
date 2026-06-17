package com.yashwanth.simple_library.repositories;

import com.yashwanth.simple_library.TestContainersConfiguration;
import com.yashwanth.simple_library.entities.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


@DataJpaTest
@Import(TestContainersConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    public void setUp(){
        author = Author.builder()
                .author_name("Yash")
                .build();
    }

    @Test
    public void testGetAuthorByName_isValid(){
        authorRepository.save(author);

       Optional<Author> author = authorRepository.getAuthorByName("Yash");

       assertThat(author).isNotEmpty();
       assertThat(author.get().getAuthor_name()).isEqualTo("Yash");
    }

    @Test
    public void testGetAuthorByName_isInvalid(){

        Optional<Author> author = authorRepository.getAuthorByName("Yashfhfh");

        assertThat(author).isEmpty();
    }

    }
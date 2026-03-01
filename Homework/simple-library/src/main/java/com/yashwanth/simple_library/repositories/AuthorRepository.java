package com.yashwanth.simple_library.repositories;

import com.yashwanth.simple_library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("Select a from Author a where a.author_name=:name")
    Optional<Author> getAuthorByName(@Param("name") String name);
}

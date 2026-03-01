package com.yashwanth.simple_library.repositories;

import com.yashwanth.simple_library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    @Query("select b.id,b.book_name,b.edition,b.releaseDate, a.author_name from Book b join b.authors a where b.id=:id")
//    BookDto getBookByID(@Param("id") Long id);

    @Query("Select b from Book b Join fetch b.authors where b.id=:id")
    Optional<Book> getBookByID(@Param("id") Long id);

    @Query("Select b from Book b Join fetch b.authors")
    List<Book> getAllBooks();

//    @Query("""
//       select distinct b
//       from Book b
//       join fetch b.authors
//       where (:bookName is null or lower(b.book_name) = lower(:bookName))
//       or (:date is null or b.releaseDate > :date)
//       """)
//    List<Book> getBookByField(@Param("bookName")String bookName, @Param("date")LocalDate date);

    @Query("Select b from Book b Join fetch b.authors a where b.releaseDate > :date")
    List<Book> findBookAfterReleaseDate(LocalDate date);
}

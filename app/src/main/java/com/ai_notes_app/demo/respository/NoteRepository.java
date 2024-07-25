package com.ai_notes_app.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ai_notes_app.demo.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n JOIN User u ON n.userId = u.id WHERE u.username = :username")
    List<Note> findByUsername(@Param("username") String username);

    @Query("SELECT n FROM Note n WHERE n.id = :id AND n.userId = :userId")
    Optional<Note> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT n FROM Note n WHERE n.title = :title AND n.userId = :userId " +
           "UNION " +
           "SELECT n FROM Note n WHERE n.title LIKE %:title% AND n.userId = :userId")
    List<Note> findByTitleExactOrContainingAndUserId(@Param("title") String title, @Param("userId") Long userId);

    Optional<Note> findByTitleAndUserId(@Param("title") String title, @Param("userId") Long userId);
}
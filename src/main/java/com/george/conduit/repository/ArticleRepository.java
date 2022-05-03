package com.george.conduit.repository;

import com.george.conduit.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitle(String title);

    Optional<Article> findBySlug(String slug);

    @Query(value = "SELECT *\n" +
            "FROM article\n" +
            "WHERE article.author_id IN (\n" +
            "\tSELECT profile_id\n" +
            "    FROM profile_followers\n" +
            "    WHERE follower_id = ?1);",
            nativeQuery = true)
    List<Article> findFeed(Long profileId);
}

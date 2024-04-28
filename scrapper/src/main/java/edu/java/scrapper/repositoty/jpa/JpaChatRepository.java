package edu.java.scrapper.repositoty.jpa;

import edu.java.scrapper.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
}

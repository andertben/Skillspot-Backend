package de.skillspot.repository;

import de.skillspot.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findAllByThreadIdOrderByCreatedAtAsc(Long threadId);
}

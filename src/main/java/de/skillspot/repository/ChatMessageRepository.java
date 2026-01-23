package de.skillspot.repository;

import de.skillspot.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findAllByThreadIdOrderByCreatedAtAsc(Long threadId);

    @Query("SELECT COUNT(m) FROM ChatMessageEntity m WHERE m.threadId = :threadId AND m.senderSub != :userSub AND m.isRead = false")
    Long countUnreadMessages(@Param("threadId") Long threadId, @Param("userSub") String userSub);

    @Query(value = "SELECT * FROM skillspot.chat_message WHERE thread_id = :threadId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<ChatMessageEntity> findLatestMessageInThread(@Param("threadId") Long threadId);

    @Modifying
    @Query("UPDATE ChatMessageEntity m SET m.isRead = true WHERE m.threadId = :threadId AND m.senderSub != :userSub AND m.isRead = false")
    void markMessagesAsRead(@Param("threadId") Long threadId, @Param("userSub") String userSub);

    @Query("SELECT COUNT(m) FROM ChatMessageEntity m WHERE m.senderSub != :userSub AND m.isRead = false AND m.threadId IN (SELECT t.threadId FROM ChatThreadEntity t LEFT JOIN t.dienstleistung d WHERE t.userSub = :userSub OR d.anbieter.benutzer.auth0Sub = :userSub)")
    Long countTotalUnreadMessages(@Param("userSub") String userSub);
}

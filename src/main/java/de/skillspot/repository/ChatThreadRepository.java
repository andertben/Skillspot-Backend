package de.skillspot.repository;

import de.skillspot.entity.ChatThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatThreadRepository extends JpaRepository<ChatThreadEntity, Long> {
    @Query("SELECT t FROM ChatThreadEntity t LEFT JOIN FETCH t.dienstleistung d LEFT JOIN FETCH d.anbieter a LEFT JOIN FETCH a.benutzer WHERE t.userSub = :userSub AND t.dienstleistungId = :dienstleistungId")
    Optional<ChatThreadEntity> findByUserSubAndDienstleistungId(@Param("userSub") String userSub, @Param("dienstleistungId") Long dienstleistungId);

    @Query("SELECT t FROM ChatThreadEntity t LEFT JOIN FETCH t.dienstleistung d LEFT JOIN FETCH d.anbieter a LEFT JOIN FETCH a.benutzer WHERE t.threadId = :threadId AND t.userSub = :userSub")
    Optional<ChatThreadEntity> findByThreadIdAndUserSub(@Param("threadId") Long threadId, @Param("userSub") String userSub);

    @Query("SELECT t FROM ChatThreadEntity t LEFT JOIN FETCH t.dienstleistung d LEFT JOIN FETCH d.anbieter a LEFT JOIN FETCH a.benutzer WHERE t.userSub = :sub OR d.anbieter.benutzer.auth0Sub = :sub")
    List<ChatThreadEntity> findAllByUserSubOrProviderSub(@Param("sub") String sub);

    @Query("SELECT t FROM ChatThreadEntity t LEFT JOIN FETCH t.dienstleistung d LEFT JOIN FETCH d.anbieter a LEFT JOIN FETCH a.benutzer WHERE t.userSub = :userSub")
    List<ChatThreadEntity> findAllByUserSub(@Param("userSub") String userSub);
}

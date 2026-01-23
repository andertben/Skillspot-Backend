package de.skillspot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_thread", schema = "skillspot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatThreadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long threadId;

    @Column(name = "dienstleistung_id", nullable = false)
    private Long dienstleistungId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dienstleistung_id", insertable = false, updatable = false)
    private DienstleistungEntity dienstleistung;

    @Column(nullable = false)
    private String userSub;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

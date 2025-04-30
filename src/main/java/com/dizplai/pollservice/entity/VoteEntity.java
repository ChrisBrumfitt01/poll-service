package com.dizplai.pollservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id", referencedColumnName = "id")
    private OptionEntity selectedOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private PollEntity poll;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}

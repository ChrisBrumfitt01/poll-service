package com.dizplai.pollservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "vote")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "selected_option_id", nullable = false)
    private Long selectedOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OptionEntity selectedOption;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}

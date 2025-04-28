package com.dizplai.pollservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "poll")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    private List<OptionEntity> options;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}

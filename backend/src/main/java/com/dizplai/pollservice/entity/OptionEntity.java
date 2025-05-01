package com.dizplai.pollservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "Option")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", referencedColumnName = "id", nullable = false)
    private PollEntity poll;

    @Column(name = "description")
    private String description;

}

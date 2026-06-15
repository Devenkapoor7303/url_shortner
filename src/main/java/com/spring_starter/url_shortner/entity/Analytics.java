package com.spring_starter.url_shortner.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    private Integer redirectCount = 0;

    private String lastAccessedIp;
    private String country;
    private String city;


    @UpdateTimestamp
    private LocalDateTime lastAccessedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}

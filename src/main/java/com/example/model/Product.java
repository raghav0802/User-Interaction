package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Integer tokenPrice;

    @Column(nullable = false)
    private boolean active;

    @Column(length = 50)
    private String category;

    @Column(length = 255)
    private String imageUrl;

    @Column
    private Integer durationDays;

    @Column
    private Integer stockCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;



    @Column(nullable = false)
    private boolean isTokenOnly;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public long getActiveDays() {
        if (createdAt != null && updatedAt != null) {
            return Duration.between(createdAt, updatedAt).toDays();
        }
        return 0;
    }

}
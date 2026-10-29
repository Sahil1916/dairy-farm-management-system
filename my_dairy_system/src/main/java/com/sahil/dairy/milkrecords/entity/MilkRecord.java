package com.sahil.dairy.milkrecords.entity;

import com.sahil.dairy.animal.entity.Animal;
import com.sahil.dairy.milkrecords.enums.MilkSession;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "milk_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "milk_date", nullable = false)
    private LocalDate milkDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "milk_session", nullable = false)
    private MilkSession milkSession;

    @Column(name = "quantity_liters", precision = 8, scale = 2, nullable = false)
    private BigDecimal quantityLiters;

    @Column(name = "fat_percentage", precision = 4, scale = 2)
    private BigDecimal fatPercentage;

    @Column(name = "snf_percentage", precision = 4, scale = 2)
    private BigDecimal snfPercentage;

    @Column(name = "rate_per_liter", precision = 8, scale = 2, nullable = false)
    private BigDecimal ratePerLiter;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(length = 500)
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
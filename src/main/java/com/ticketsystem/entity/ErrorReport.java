package com.ticketsystem.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "error_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer id;

    @Column(nullable=false, unique=true, updatable=false, length=36)
     String errorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
     User user; // null nếu guest

    @Column
     String contactEmail;

    @Column(nullable=false, length=255)
     String title;

    @Column(nullable=false, columnDefinition="TEXT")
     String description;

    @Column(columnDefinition="TEXT")
     String stepsToReproduce;

    @Column
     String screenshotUrl;

    @Column(nullable=false)
     String status = "new";

    @Column(nullable=false)
     Boolean consentAttachInternal = false;

    @Column(nullable=false)
     LocalDateTime createdAt;

    @Column(nullable=false)
     LocalDateTime updatedAt;

    @PreUpdate void onUpdate(){ updatedAt = LocalDateTime.now(); }
}

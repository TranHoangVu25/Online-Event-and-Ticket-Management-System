//package com.ticketsystem.entity;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "error_reports")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class ErrorReport {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable=false, unique=true, updatable=false, length=36)
//    private String errorId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user; // null nếu guest
//
//    @Column
//    private String contactEmail;
//
//    @Column(nullable=false, length=255)
//    private String title;
//
//    @Column(nullable=false, columnDefinition="TEXT")
//    private String description;
//
//    @Column(columnDefinition="TEXT")
//    private String stepsToReproduce;
//
//    @Column
//    private String screenshotUrl;
//
//    @Column(nullable=false)
//    private String status = "new";
//
//    @Column(nullable=false)
//    private Boolean consentAttachInternal = false;
//
//    @Column(nullable=false)
//    private LocalDateTime createdAt;
//
//    @Column(nullable=false)
//    private LocalDateTime updatedAt;
//
//    @PreUpdate void onUpdate(){ updatedAt = LocalDateTime.now(); }
//}

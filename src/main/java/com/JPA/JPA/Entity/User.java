package com.JPA.JPA.Entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @Column(name = "user_id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_age")
    private int age;

    @Column(name = "is_married", columnDefinition = "TINYINT(1)")
    private boolean married;

    @JdbcTypeCode(SqlTypes.JSON)
    @Type(JsonStringType.class)
    private Address address;

    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private String updatedBy;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() throws UnknownHostException {
        this.createdBy = InetAddress.getLocalHost().getHostName();
    }

    @PreUpdate
    protected void onUpdate() throws UnknownHostException {
        this.updatedBy = InetAddress.getLocalHost().getHostName();
    }
}

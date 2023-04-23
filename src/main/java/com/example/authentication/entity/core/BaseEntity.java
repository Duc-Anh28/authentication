package com.example.authentication.entity.core;

import com.example.authentication.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    @Column(name = "created_at")
    private Instant createdAt;

    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private Account createdBy;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Instant modifiedAt;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private Account modifiedBy;

    @Column(name = "is_deleted", nullable = false)
    @ColumnDefault("0")
    private Boolean isDeleted = false;

    public Account getCreatedBy() {
        return createdBy;
    }

    public Account getModifiedBy() {
        return modifiedBy;
    }

}

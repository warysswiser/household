package com.warys.app.household.infrastructure.repository.mongo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditableEntity {
    @Id
    protected String id;
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime creationDate;
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    protected LocalDateTime updateDate;

    protected AuditableEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return this.updateDate;
    }
}

package com.devsuperior.dscatalog.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}

package com.devsuperior.dscatalog.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private Long id;
    private String name;
}

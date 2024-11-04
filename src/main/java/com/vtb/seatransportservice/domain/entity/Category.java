package com.vtb.seatransportservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "url_name")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Topic> subcategories;
    @Column(name = "is_deleted")
    private Boolean deleted = false;
    @NotBlank
    @Size(max = 255)
    private String url_name;

}

package com.vtb.seatransportservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name = "topic", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "url_name")
})
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Tên chủ đề không được để trống")
    @Size(max = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @NotBlank(message = "Tên đường dẫn không được để trống")
    @Size(max = 255)
    private String url_name;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private String icon;
    @Column(name = "is_deleted")
    private Boolean deleted = false;

}

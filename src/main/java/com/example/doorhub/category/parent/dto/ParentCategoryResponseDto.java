package com.example.doorhub.category.parent.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.doorhub.review.entity.Review;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParentCategoryResponseDto {

    private Integer id;
    private String name;
    private Integer categoryId;
    private List<Review> review;

}

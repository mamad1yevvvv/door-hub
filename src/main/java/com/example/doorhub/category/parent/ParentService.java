package com.example.doorhub.category.parent;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.doorhub.category.CategoryRepository;
import org.example.doorhub.category.entity.Category;
import org.example.doorhub.category.parent.dto.ParentCategoryCreateDto;
import org.example.doorhub.category.parent.dto.ParentCategoryPathDto;
import org.example.doorhub.category.parent.dto.ParentCategoryResponseDto;
import org.example.doorhub.category.parent.dto.ParentCategoryUpdateDto;
import org.example.doorhub.category.parent.entity.ParentCategory;
import org.example.doorhub.common.service.GenericCrudService;
import org.example.doorhub.review.ReviewRepository;
import org.example.doorhub.review.entity.Review;
import org.example.doorhub.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
public class ParentService extends GenericCrudService<ParentCategory, Integer, ParentCategoryCreateDto, ParentCategoryUpdateDto, ParentCategoryPathDto, ParentCategoryResponseDto> {


    private final ParentRepository repository;
    private final ParentMapperDto mapper;
    private final Class<ParentCategory> EntityClass = ParentCategory.class;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    @Override
    protected ParentCategory save(ParentCategoryCreateDto parentCategoryCreateDto) {

        Review reviewIdNotFound = reviewRepository.findById(parentCategoryCreateDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(reviewIdNotFound);

        Category category = categoryRepository.findById(parentCategoryCreateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));


        ParentCategory parentCategory = mapper.toEntity(parentCategoryCreateDto);

        parentCategory.setViews(reviews);
        reviewIdNotFound.setParentCategory(parentCategory);

        parentCategory.setCategory(category);
        category.getParents().add(parentCategory);
        return repository.save(parentCategory);
    }

    public ParentCategoryResponseDto create(ParentCategoryCreateDto createDto) {

        Review reviewIdNotFound = reviewRepository.findById(createDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        Category category = categoryRepository.findById(createDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        List<Review> reviews = new ArrayList<>();

        ParentCategory parentCategory = save(createDto);

        ParentCategoryResponseDto responseDto = mapper.toResponseDto(parentCategory);
        responseDto.setReview(reviews);
        responseDto.setCategoryId(category.getId());

        return responseDto;

    }

    @Override
    protected ParentCategory updateEntity(ParentCategoryUpdateDto parentCategoryUpdateDto, ParentCategory parentCategory) {

        Review reviewIdNotFound = reviewRepository.findById(parentCategoryUpdateDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        Category category = categoryRepository.findById(parentCategoryUpdateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        ArrayList<Review> reviews = new ArrayList<>();
        reviews.add(reviewIdNotFound);

        mapper.update(parentCategoryUpdateDto, parentCategory);
        parentCategory.setViews(reviews);
        parentCategory.setCategory(category);
        return repository.save(parentCategory);

    }

    public ParentCategoryResponseDto update(Integer id, ParentCategoryUpdateDto parentCategoryUpdateDto) {

        Review reviewIdNotFound = reviewRepository.findById(parentCategoryUpdateDto.getReviewId())
                .orElseThrow(() -> new EntityNotFoundException("review id not found"));

        Category category = categoryRepository.findById(parentCategoryUpdateDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("category id not found"));

        ParentCategory parentCategory = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("parentCategory id not found"));

        ArrayList<Review> reviews = new ArrayList<>();
        ParentCategory updateEntity = updateEntity(parentCategoryUpdateDto, parentCategory);

        ParentCategoryResponseDto responseDto = mapper.toResponseDto(updateEntity);

        responseDto.setCategoryId(category.getId());
        responseDto.setReview(reviews);

        return responseDto;

    }
}

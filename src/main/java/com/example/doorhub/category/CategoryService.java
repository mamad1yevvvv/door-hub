package com.example.doorhub.category;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.doorhub.category.dto.*;
import org.example.doorhub.category.entity.Category;
import org.example.doorhub.category.parent.ParentRepository;
import org.example.doorhub.common.service.GenericCrudService;
import org.example.doorhub.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class CategoryService extends GenericCrudService<Category, Integer, CategoryCreateDto, CategoryUpdateDto, CategoryPatchDto, CategoryResponseDto> {

    private final CategoryRepository repository;
    private final CategoryMapperDto mapper;
    private final Class<Category> EntityClass = Category.class;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ParentRepository parentRepository;

    @Override
    @Transactional
    public Category save(CategoryCreateDto categoryCreateDto) {

        Category category = mapper.toEntity(categoryCreateDto);
        return repository.save(category);
    }


    @Override
    @Transactional
    public Category updateEntity(CategoryUpdateDto categoryUpdateDto, Category category) {
        mapper.update(categoryUpdateDto, category);
        return repository.save(category);
    }
}

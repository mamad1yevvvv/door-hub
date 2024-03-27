package com.example.doorhub.category.parent;

import lombok.RequiredArgsConstructor;
import org.example.doorhub.category.dto.CategoryResponseDto;
import org.example.doorhub.category.entity.Category;
import org.example.doorhub.category.parent.dto.ParentCategoryCreateDto;
import org.example.doorhub.category.parent.dto.ParentCategoryResponseDto;
import org.example.doorhub.category.parent.dto.ParentCategoryUpdateDto;
import org.example.doorhub.category.parent.entity.ParentCategory;
import org.example.doorhub.common.service.GenericDtoMapper;
import org.example.doorhub.review.dto.ReviewResponseDto;
import org.example.doorhub.review.entity.Review;
import org.example.doorhub.user.dto.UserResponseDto;
import org.example.doorhub.user.entity.User;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ParentMapperDto extends GenericDtoMapper<ParentCategory, ParentCategoryCreateDto, ParentCategoryUpdateDto, ParentCategoryResponseDto> {

    private final ModelMapper modelMapper;

    @Override
    public ParentCategory toEntity(ParentCategoryCreateDto parentCategoryCreateDto) {
        return modelMapper.map(parentCategoryCreateDto, ParentCategory.class);
    }

    @Override
    public ParentCategoryResponseDto toResponseDto(ParentCategory parentCategory) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        TypeMap<ParentCategory, ParentCategoryResponseDto> typeMap = modelMapper.typeMap(ParentCategory.class, ParentCategoryResponseDto.class)
                .addMappings(mapper -> mapper.map(src -> src.getCategory().getId(), ParentCategoryResponseDto::setCategoryId));

        return modelMapper.map(parentCategory, ParentCategoryResponseDto.class);
    }

    @Override
    public void update(ParentCategoryUpdateDto parentCategoryUpdateDto, ParentCategory parentCategory) {
        modelMapper.map(parentCategoryUpdateDto, parentCategory);
    }

    @Override
    public ParentCategoryCreateDto toCreateDto(ParentCategory parentCategory) {
        return modelMapper.map(parentCategory, ParentCategoryCreateDto.class);
    }
}

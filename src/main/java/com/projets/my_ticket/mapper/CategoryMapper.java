package com.projets.my_ticket.mapper;


import com.projets.my_ticket.domain.Category;
import com.projets.my_ticket.dto.CategoryCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface CategoryMapper {

    Category toEntity(CategoryCreateDto categoryCreateDto);
}

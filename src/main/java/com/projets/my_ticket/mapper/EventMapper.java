package com.projets.my_ticket.mapper;

import com.projets.my_ticket.domain.Event;
import com.projets.my_ticket.dto.EventCreateDto;
import com.projets.my_ticket.mapper.helpers.CategoryMapperHelper;
import com.projets.my_ticket.mapper.helpers.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapperHelper.class, UserMapperHelper.class})
public interface EventMapper {
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "userId", target = "user")
    Event toEntity(EventCreateDto eventCreateDto);
}

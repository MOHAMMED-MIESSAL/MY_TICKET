package com.projets.my_ticket.mapper;


import com.projets.my_ticket.domain.User;
import com.projets.my_ticket.dto.UserCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" )
public interface UserMapper {
    User toEntity(UserCreateDto userCreateDto);
}

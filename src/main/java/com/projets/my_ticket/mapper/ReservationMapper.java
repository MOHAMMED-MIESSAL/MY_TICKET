package com.projets.my_ticket.mapper;


import com.projets.my_ticket.domain.Reservation;
import com.projets.my_ticket.dto.ReservationCreateDto;
import com.projets.my_ticket.mapper.helpers.EventMapperHelper;
import com.projets.my_ticket.mapper.helpers.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapperHelper.class, EventMapperHelper.class})
public interface ReservationMapper {

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "eventId", target = "event")
    Reservation toEntity(ReservationCreateDto reservationCreateDto);
}

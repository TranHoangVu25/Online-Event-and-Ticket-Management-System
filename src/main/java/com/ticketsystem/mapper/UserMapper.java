package com.ticketsystem.mapper;

import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.request.UserUpdateRequest;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.dto.response.UserResponse1;
import com.ticketsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "email", target = "email")
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(source = "mustChangePassword", target = "mustChangePassword")
    UserResponse1 toUserResponse1(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

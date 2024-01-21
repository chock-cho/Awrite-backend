package Awrite_project.Awrite.converter;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.UserRequestDTO;
import Awrite_project.Awrite.web.dto.UserResponseDTO;

import java.time.LocalDateTime;

public class UserConverter {

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user){
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(user.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static User toUser(UserRequestDTO.JoinDto request){

        return User.builder()
                .nickname(request.getNickname())
                .build();
    }
}
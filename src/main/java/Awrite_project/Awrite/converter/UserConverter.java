package Awrite_project.Awrite.converter;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import Awrite_project.Awrite.web.dto.UserDTO.UserResponseDTO;

import java.time.LocalDateTime;

public class UserConverter {

    public static UserResponseDTO.JoinResultDTO toJoinResultDTO(User user){
        return UserResponseDTO.JoinResultDTO.builder()
                .userId(user.getId())
                .verified(user.isVerified())
                .verificationCode(user.getVerificationCode())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDTO.CodeVerificationResultDTO toVerifyResultDTO(User user){
        return UserResponseDTO.CodeVerificationResultDTO.builder()
                .verified(user.isVerified())
                .verificationCode(user.getVerificationCode())
                .build();
    }

    public static User toUser(UserRequestDTO.JoinDto request){

        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .profilePicture(1)
                .build();
    }
}
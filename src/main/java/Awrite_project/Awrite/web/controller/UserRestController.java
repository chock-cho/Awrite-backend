package Awrite_project.Awrite.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Awrite_project.Awrite.apiPayload.ApiResponse;
import Awrite_project.Awrite.converter.UserConverter;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.UserService.UserCommandService;
import Awrite_project.Awrite.web.dto.UserRequestDTO;
import Awrite_project.Awrite.web.dto.UserResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserRestController {

    private final UserCommandService userCommandService;

    @PostMapping("/")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDto request){
        User member = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(member));
    }

}
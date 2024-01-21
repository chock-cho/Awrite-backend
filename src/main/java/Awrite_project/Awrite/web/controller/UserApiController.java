package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.service.UserService.UserCommandService;
import Awrite_project.Awrite.service.UserService.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserApiController {

    public final UserCommandService userCommandService;


}

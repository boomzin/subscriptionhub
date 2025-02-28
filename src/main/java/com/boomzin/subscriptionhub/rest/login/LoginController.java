package com.boomzin.subscriptionhub.rest.login;

import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;


@RestController
@RequestMapping(BASIC_PATH_V1)
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/login")
    public DataApiResponse<LoginRespDto> login(@RequestHeader("X-Device-Id") String deviceId, @RequestBody @Valid LoginDto dto) {
        String token = userService.login(dto.getUsername(), dto.getPassword(), deviceId);
        return new DataApiResponse<>(new LoginRespDto(token));
    }
}

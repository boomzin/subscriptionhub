package com.boomzin.subscriptionhub.rest.user;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.domain.user.User;
import com.boomzin.subscriptionhub.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public PagedDataApiResponse<UserDto> list(

            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "roleId", required = false) String roleId,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<User> cmdItems = userService.search(new HashMap<>() {{

            put("id", id);
            put("roleId", roleId);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<UserDto> dtoList = cmdItems.getItems().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<UserDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new UserDto(userService.findById(id)));
    }


    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        userService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid UserDto dto
    ) {
        userService.create(
                new User(
                        UUID.randomUUID(),
                        dto.getEmail(),
                        null,
                        dto.getCreatedAt(),
                        dto.getRoleId()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(

            @RequestBody @Valid UserDto dto
    ) {
        userService.update(
                new User(
                        UUID.randomUUID(),
                        dto.getEmail(),
                        null,
                        dto.getCreatedAt(),
                        dto.getRoleId()
                )
        );
        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

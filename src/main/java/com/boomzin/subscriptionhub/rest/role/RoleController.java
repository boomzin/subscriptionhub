package com.boomzin.subscriptionhub.rest.role;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.domain.role.Role;
import com.boomzin.subscriptionhub.domain.role.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/roles")
public class RoleController {
    private final RoleService roleService;


    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public PagedDataApiResponse<RoleDto> list(

            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Role> cmdItems = roleService.search(new HashMap<>() {{

            put("id", id);
            put("name", name);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<RoleDto> dtoList = cmdItems.getItems().stream()
                .map(RoleDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<RoleDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new RoleDto(roleService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid RoleDto dto
    ) {
        roleService.create(
                new Role(
                        UUID.randomUUID(),
                        dto.getName()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(

            @RequestBody @Valid RoleDto dto
    ) {
        roleService.update(
                new Role(
                        dto.getId(),
                        dto.getName()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        roleService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

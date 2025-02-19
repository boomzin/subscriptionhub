package com.boomzin.subscriptionhub.rest.permission;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.domain.permission.Permission;
import com.boomzin.subscriptionhub.domain.permission.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/permissions")
public class PermissionController {
    private final PermissionService permissionService;


    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping()
    public PagedDataApiResponse<PermissionDto> list(

            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Permission> cmdItems = permissionService.search(new HashMap<>() {{

            put("id", id);
            put("name", name);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<PermissionDto> dtoList = cmdItems.getItems().stream()
                .map(PermissionDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<PermissionDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new PermissionDto(permissionService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid PermissionDto dto
    ) {
        permissionService.create(
                new Permission(
                        UUID.randomUUID(),
                        dto.getName(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(

            @RequestBody @Valid PermissionDto dto
    ) {
        permissionService.update(
                new Permission(
                        dto.getId(),
                        dto.getName(),
                        dto.getDescription()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        permissionService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

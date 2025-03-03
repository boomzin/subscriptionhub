package com.boomzin.subscriptionhub.rest.rolepermission;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.config.security.SecurityPermission;
import com.boomzin.subscriptionhub.domain.rolepermission.RolePermission;
import com.boomzin.subscriptionhub.domain.rolepermission.RolePermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/rolePermissions")
@SecurityPermission("adminAccess")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;


    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping()
    public PagedDataApiResponse<RolePermissionDto> list(

            @RequestParam(name = "roleId", required = false) String roleId,
            @RequestParam(name = "permissionId", required = false) String permissionId,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<RolePermission> cmdItems = rolePermissionService.search(new HashMap<>() {{

            put("roleId", roleId);
            put("permissionId", permissionId);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<RolePermissionDto> dtoList = cmdItems.getItems().stream()
                .map(RolePermissionDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid RolePermissionDto dto
    ) {
        RolePermission rolePermission = new RolePermission(dto.getRoleId(), dto.getPermissionId());
        rolePermissionService.create(rolePermission);

        return new DataApiResponse<>(rolePermission);
    }


    @DeleteMapping()
    public StatusApiResponse delete(
            @RequestParam("roleId") UUID roleId,
            @RequestParam("permissionId") UUID permissionId
    ) {
        rolePermissionService.delete(roleId, permissionId);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

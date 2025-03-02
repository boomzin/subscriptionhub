package com.boomzin.subscriptionhub.rest.subscriptiontype;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.config.security.SecurityPermission;
import com.boomzin.subscriptionhub.domain.subscriptiontype.SubscriptionType;
import com.boomzin.subscriptionhub.domain.subscriptiontype.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/subscriptionTypes")
@SecurityPermission("managementAccess")
public class SubscriptionTypeController {
    private final SubscriptionTypeService subscriptionTypeService;


    public SubscriptionTypeController(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @GetMapping()
    public PagedDataApiResponse<SubscriptionTypeDto> list(


            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<SubscriptionType> cmdItems = subscriptionTypeService.search(new HashMap<>() {{


            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<SubscriptionTypeDto> dtoList = cmdItems.getItems().stream()
                .map(SubscriptionTypeDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<SubscriptionTypeDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new SubscriptionTypeDto(subscriptionTypeService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid SubscriptionTypeDto dto
    ) {
        subscriptionTypeService.create(
                new SubscriptionType(
                        UUID.randomUUID(),
                        dto.getName(),
                        dto.getDurationDays(),
                        dto.getPrice(),
                        dto.getFeature()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(

            @RequestBody @Valid SubscriptionTypeDto dto
    ) {
        subscriptionTypeService.update(
                new SubscriptionType(
                        dto.getId(),
                        dto.getName(),
                        dto.getDurationDays(),
                        dto.getPrice(),
                        dto.getFeature()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        subscriptionTypeService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

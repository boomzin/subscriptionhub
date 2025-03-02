package com.boomzin.subscriptionhub.rest.subscription;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.config.security.SecurityPermission;
import com.boomzin.subscriptionhub.domain.subscription.Subscription;
import com.boomzin.subscriptionhub.domain.subscription.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/subscriptions")
@SecurityPermission("managementAccess")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;


    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping()
    public PagedDataApiResponse<SubscriptionDto> list(

            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "typeId", required = false) String typeId,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "startDateFrom", required = false) String startDateFrom,
            @RequestParam(name = "startDateTo", required = false) String startDateTo,
            @RequestParam(name = "endDate", required = false) String endDate,
            @RequestParam(name = "endDateFrom", required = false) String endDateFrom,
            @RequestParam(name = "endDateTo", required = false) String endDateTo,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Subscription> cmdItems = subscriptionService.search(new HashMap<>() {{

            put("id", id);
            put("userId", userId);
            put("typeId", typeId);
            put("startDate", startDate);
            put("startDateFrom", startDateFrom);
            put("startDateTo", startDateTo);
            put("endDate", endDate);
            put("endDateFrom", endDateFrom);
            put("endDateTo", endDateTo);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<SubscriptionDto> dtoList = cmdItems.getItems().stream()
                .map(SubscriptionDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<SubscriptionDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new SubscriptionDto(subscriptionService.findById(id)));
    }

    @PostMapping()
    public StatusApiResponse create(
            @RequestBody @Valid SubscriptionDto dto
    ) {
        subscriptionService.create(
                new Subscription(
                        UUID.randomUUID(),
                        dto.getUserId(),
                        dto.getTypeId(),
                        dto.getStartDateTime(),
                        dto.getCreatedAt(),
                        dto.getEndDate(),
                        dto.getStatus()
                )
        );

        return new StatusApiResponse(HttpStatus.CREATED.value(), true);
    }

    @PutMapping(value = "/{id}")
    public StatusApiResponse update(

            @RequestBody @Valid SubscriptionDto dto
    ) {
        subscriptionService.update(
                new Subscription(
                        dto.getId(),
                        dto.getUserId(),
                        dto.getTypeId(),
                        dto.getStartDateTime(),
                        dto.getCreatedAt(),
                        dto.getEndDate(),
                        dto.getStatus()
                )
        );

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }

    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        subscriptionService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

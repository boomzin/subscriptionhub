package com.boomzin.subscriptionhub.rest.session;


import com.boomzin.subscriptionhub.common.data.PagedResult;
import com.boomzin.subscriptionhub.common.response.DataApiResponse;
import com.boomzin.subscriptionhub.common.response.PagedDataApiResponse;
import com.boomzin.subscriptionhub.common.response.StatusApiResponse;
import com.boomzin.subscriptionhub.domain.session.Session;
import com.boomzin.subscriptionhub.domain.session.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.boomzin.subscriptionhub.common.Constants.BASIC_PATH_V1;

@RestController
@RequestMapping(BASIC_PATH_V1 + "/sessions")
public class SessionController {
    private final SessionService sessionService;


    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping()
    public PagedDataApiResponse<SessionDto> list(

            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "userId", required = false) String userId,
            @RequestParam(name = "deviceId", required = false) String deviceId,
            @RequestParam(name = "token", required = false) String token,

            @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", required = false, defaultValue = "100") Integer limit,
            @RequestParam(name = "order", required = false, defaultValue = "") String orders
    ) {

        PagedResult<Session> cmdItems = sessionService.search(new HashMap<>() {{

            put("id", id);
            put("userId", userId);
            put("deviceId", deviceId);
            put("token", token);

            put("offset", String.valueOf(offset));
            put("limit", String.valueOf(limit));
            put("order", orders);
        }});


        List<SessionDto> dtoList = cmdItems.getItems().stream()
                .map(SessionDto::new)
                .collect(Collectors.toList());

        return new PagedDataApiResponse<>(dtoList, cmdItems.getItemsCount(), cmdItems.getOffset(), cmdItems.getLimit());
    }

    @GetMapping(value = "/{id}")
    public DataApiResponse<SessionDto> getByUuid(
            @PathVariable("id") UUID id
    ) {
        return new DataApiResponse<>(new SessionDto(sessionService.findById(id)));
    }


    @DeleteMapping(value = "/{id}")
    public StatusApiResponse delete(
            @PathVariable("id") UUID id
    ) {
        sessionService.delete(id);

        return new StatusApiResponse(HttpStatus.OK.value(), true);
    }
}

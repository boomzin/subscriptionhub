package com.boomzin.subscriptionhub.common.response;


import com.boomzin.subscriptionhub.common.exception.ResponseStatus;

public class SuccessStatusApiResponse extends StatusApiResponse {
    public static SuccessStatusApiResponse SUCCESS = new SuccessStatusApiResponse(ResponseStatus.SUCCESS);

    public SuccessStatusApiResponse(ResponseStatus status) {
        super(status.getValue(), true);
    }
}

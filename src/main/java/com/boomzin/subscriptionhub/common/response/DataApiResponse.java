package com.boomzin.subscriptionhub.common.response;


import com.boomzin.subscriptionhub.common.exception.ResponseStatus;

public class DataApiResponse<T> extends SuccessStatusApiResponse {
    private T data;

    public DataApiResponse(T data) {
        super(ResponseStatus.SUCCESS);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}

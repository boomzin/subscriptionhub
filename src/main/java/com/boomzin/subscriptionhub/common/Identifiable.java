package com.boomzin.subscriptionhub.common;


import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}

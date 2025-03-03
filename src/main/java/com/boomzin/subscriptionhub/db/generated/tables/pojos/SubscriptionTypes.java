/*
 * This file is generated by jOOQ.
 */
package com.boomzin.subscriptionhub.db.generated.tables.pojos;


import com.boomzin.subscriptionhub.db.generated.tables.interfaces.ISubscriptionTypes;

import java.math.BigDecimal;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.JSONB;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.18"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SubscriptionTypes implements ISubscriptionTypes {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private Integer durationDays;
    private BigDecimal price;
    private JSONB features;

    public SubscriptionTypes() {}

    public SubscriptionTypes(ISubscriptionTypes value) {
        this.id = value.getId();
        this.name = value.getName();
        this.durationDays = value.getDurationDays();
        this.price = value.getPrice();
        this.features = value.getFeatures();
    }

    public SubscriptionTypes(
        UUID id,
        String name,
        Integer durationDays,
        BigDecimal price,
        JSONB features
    ) {
        this.id = id;
        this.name = name;
        this.durationDays = durationDays;
        this.price = price;
        this.features = features;
    }

    /**
     * Getter for <code>subscription_types.id</code>.
     */
    @Override
    public UUID getId() {
        return this.id;
    }

    /**
     * Setter for <code>subscription_types.id</code>.
     */
    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Getter for <code>subscription_types.name</code>.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Setter for <code>subscription_types.name</code>.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for <code>subscription_types.duration_days</code>.
     */
    @Override
    public Integer getDurationDays() {
        return this.durationDays;
    }

    /**
     * Setter for <code>subscription_types.duration_days</code>.
     */
    @Override
    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    /**
     * Getter for <code>subscription_types.price</code>.
     */
    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Setter for <code>subscription_types.price</code>.
     */
    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Getter for <code>subscription_types.features</code>.
     */
    @Override
    public JSONB getFeatures() {
        return this.features;
    }

    /**
     * Setter for <code>subscription_types.features</code>.
     */
    @Override
    public void setFeatures(JSONB features) {
        this.features = features;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final SubscriptionTypes other = (SubscriptionTypes) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.durationDays == null) {
            if (other.durationDays != null)
                return false;
        }
        else if (!this.durationDays.equals(other.durationDays))
            return false;
        if (this.price == null) {
            if (other.price != null)
                return false;
        }
        else if (!this.price.equals(other.price))
            return false;
        if (this.features == null) {
            if (other.features != null)
                return false;
        }
        else if (!this.features.equals(other.features))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.durationDays == null) ? 0 : this.durationDays.hashCode());
        result = prime * result + ((this.price == null) ? 0 : this.price.hashCode());
        result = prime * result + ((this.features == null) ? 0 : this.features.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SubscriptionTypes (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(durationDays);
        sb.append(", ").append(price);
        sb.append(", ").append(features);

        sb.append(")");
        return sb.toString();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(ISubscriptionTypes from) {
        setId(from.getId());
        setName(from.getName());
        setDurationDays(from.getDurationDays());
        setPrice(from.getPrice());
        setFeatures(from.getFeatures());
    }

    @Override
    public <E extends ISubscriptionTypes> E into(E into) {
        into.from(this);
        return into;
    }
}

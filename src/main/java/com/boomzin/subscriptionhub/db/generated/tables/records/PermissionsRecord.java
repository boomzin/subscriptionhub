/*
 * This file is generated by jOOQ.
 */
package com.boomzin.subscriptionhub.db.generated.tables.records;


import com.boomzin.subscriptionhub.db.generated.tables.Permissions;
import com.boomzin.subscriptionhub.db.generated.tables.interfaces.IPermissions;

import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class PermissionsRecord extends UpdatableRecordImpl<PermissionsRecord> implements IPermissions {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>permissions.id</code>.
     */
    @Override
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>permissions.id</code>.
     */
    @Override
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>permissions.name</code>.
     */
    @Override
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>permissions.name</code>.
     */
    @Override
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>permissions.description</code>.
     */
    @Override
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>permissions.description</code>.
     */
    @Override
    public String getDescription() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    @Override
    public void from(IPermissions from) {
        setId(from.getId());
        setName(from.getName());
        setDescription(from.getDescription());
        resetChangedOnNotNull();
    }

    @Override
    public <E extends IPermissions> E into(E into) {
        into.from(this);
        return into;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PermissionsRecord
     */
    public PermissionsRecord() {
        super(Permissions.PERMISSIONS);
    }

    /**
     * Create a detached, initialised PermissionsRecord
     */
    public PermissionsRecord(UUID id, String name, String description) {
        super(Permissions.PERMISSIONS);

        setId(id);
        setName(name);
        setDescription(description);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised PermissionsRecord
     */
    public PermissionsRecord(com.boomzin.subscriptionhub.db.generated.tables.pojos.Permissions value) {
        super(Permissions.PERMISSIONS);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
            setDescription(value.getDescription());
            resetChangedOnNotNull();
        }
    }
}

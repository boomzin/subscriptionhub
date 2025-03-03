/*
 * This file is generated by jOOQ.
 */
package com.boomzin.subscriptionhub.db.generated;


import com.boomzin.subscriptionhub.db.generated.tables.Permissions;
import com.boomzin.subscriptionhub.db.generated.tables.RolePermissions;
import com.boomzin.subscriptionhub.db.generated.tables.Roles;
import com.boomzin.subscriptionhub.db.generated.tables.Sessions;
import com.boomzin.subscriptionhub.db.generated.tables.SubscriptionTypes;
import com.boomzin.subscriptionhub.db.generated.tables.Subscriptions;
import com.boomzin.subscriptionhub.db.generated.tables.Users;
import com.boomzin.subscriptionhub.db.generated.tables.records.PermissionsRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.RolePermissionsRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.RolesRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.SessionsRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.SubscriptionTypesRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.SubscriptionsRecord;
import com.boomzin.subscriptionhub.db.generated.tables.records.UsersRecord;

import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.19.18"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<PermissionsRecord> PERMISSIONS_NAME_KEY = Internal.createUniqueKey(Permissions.PERMISSIONS, DSL.name("permissions_name_key"), new TableField[] { Permissions.PERMISSIONS.NAME }, true);
    public static final UniqueKey<PermissionsRecord> PERMISSIONS_PKEY = Internal.createUniqueKey(Permissions.PERMISSIONS, DSL.name("permissions_pkey"), new TableField[] { Permissions.PERMISSIONS.ID }, true);
    public static final UniqueKey<RolePermissionsRecord> ROLE_PERMISSIONS_PKEY = Internal.createUniqueKey(RolePermissions.ROLE_PERMISSIONS, DSL.name("role_permissions_pkey"), new TableField[] { RolePermissions.ROLE_PERMISSIONS.ROLE_ID, RolePermissions.ROLE_PERMISSIONS.PERMISSION_ID }, true);
    public static final UniqueKey<RolesRecord> ROLES_NAME_KEY = Internal.createUniqueKey(Roles.ROLES, DSL.name("roles_name_key"), new TableField[] { Roles.ROLES.NAME }, true);
    public static final UniqueKey<RolesRecord> ROLES_PKEY = Internal.createUniqueKey(Roles.ROLES, DSL.name("roles_pkey"), new TableField[] { Roles.ROLES.ID }, true);
    public static final UniqueKey<SessionsRecord> SESSIONS_PKEY = Internal.createUniqueKey(Sessions.SESSIONS, DSL.name("sessions_pkey"), new TableField[] { Sessions.SESSIONS.ID }, true);
    public static final UniqueKey<SubscriptionTypesRecord> SUBSCRIPTION_TYPES_NAME_KEY = Internal.createUniqueKey(SubscriptionTypes.SUBSCRIPTION_TYPES, DSL.name("subscription_types_name_key"), new TableField[] { SubscriptionTypes.SUBSCRIPTION_TYPES.NAME }, true);
    public static final UniqueKey<SubscriptionTypesRecord> SUBSCRIPTION_TYPES_PKEY = Internal.createUniqueKey(SubscriptionTypes.SUBSCRIPTION_TYPES, DSL.name("subscription_types_pkey"), new TableField[] { SubscriptionTypes.SUBSCRIPTION_TYPES.ID }, true);
    public static final UniqueKey<SubscriptionsRecord> SUBSCRIPTIONS_PKEY = Internal.createUniqueKey(Subscriptions.SUBSCRIPTIONS, DSL.name("subscriptions_pkey"), new TableField[] { Subscriptions.SUBSCRIPTIONS.ID }, true);
    public static final UniqueKey<UsersRecord> USERS_EMAIL_KEY = Internal.createUniqueKey(Users.USERS, DSL.name("users_email_key"), new TableField[] { Users.USERS.EMAIL }, true);
    public static final UniqueKey<UsersRecord> USERS_PKEY = Internal.createUniqueKey(Users.USERS, DSL.name("users_pkey"), new TableField[] { Users.USERS.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<RolePermissionsRecord, PermissionsRecord> ROLE_PERMISSIONS__ROLE_PERMISSIONS_PERMISSION_ID_FKEY = Internal.createForeignKey(RolePermissions.ROLE_PERMISSIONS, DSL.name("role_permissions_permission_id_fkey"), new TableField[] { RolePermissions.ROLE_PERMISSIONS.PERMISSION_ID }, Keys.PERMISSIONS_PKEY, new TableField[] { Permissions.PERMISSIONS.ID }, true);
    public static final ForeignKey<RolePermissionsRecord, RolesRecord> ROLE_PERMISSIONS__ROLE_PERMISSIONS_ROLE_ID_FKEY = Internal.createForeignKey(RolePermissions.ROLE_PERMISSIONS, DSL.name("role_permissions_role_id_fkey"), new TableField[] { RolePermissions.ROLE_PERMISSIONS.ROLE_ID }, Keys.ROLES_PKEY, new TableField[] { Roles.ROLES.ID }, true);
    public static final ForeignKey<SessionsRecord, UsersRecord> SESSIONS__SESSIONS_USER_ID_FKEY = Internal.createForeignKey(Sessions.SESSIONS, DSL.name("sessions_user_id_fkey"), new TableField[] { Sessions.SESSIONS.USER_ID }, Keys.USERS_PKEY, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<SubscriptionsRecord, SubscriptionTypesRecord> SUBSCRIPTIONS__SUBSCRIPTIONS_TYPE_ID_FKEY = Internal.createForeignKey(Subscriptions.SUBSCRIPTIONS, DSL.name("subscriptions_type_id_fkey"), new TableField[] { Subscriptions.SUBSCRIPTIONS.TYPE_ID }, Keys.SUBSCRIPTION_TYPES_PKEY, new TableField[] { SubscriptionTypes.SUBSCRIPTION_TYPES.ID }, true);
    public static final ForeignKey<SubscriptionsRecord, UsersRecord> SUBSCRIPTIONS__SUBSCRIPTIONS_USER_ID_FKEY = Internal.createForeignKey(Subscriptions.SUBSCRIPTIONS, DSL.name("subscriptions_user_id_fkey"), new TableField[] { Subscriptions.SUBSCRIPTIONS.USER_ID }, Keys.USERS_PKEY, new TableField[] { Users.USERS.ID }, true);
    public static final ForeignKey<UsersRecord, RolesRecord> USERS__USERS_ROLE_ID_FKEY = Internal.createForeignKey(Users.USERS, DSL.name("users_role_id_fkey"), new TableField[] { Users.USERS.ROLE_ID }, Keys.ROLES_PKEY, new TableField[] { Roles.ROLES.ID }, true);
}

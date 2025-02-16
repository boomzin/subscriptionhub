package com.boomzin.subscriptionhub.common.search;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.function.BiFunction;

public class JooqSearchUtils {

    public static final BiFunction<Field<String>, String, Condition> STR_EQ = Field::eq;
    public static final BiFunction<Field<String>, String, Condition> STR_LIKE = (f, v) -> f.like(likeWrap(v));
    public static final BiFunction<Field<String>, String, Condition> STR_LIKE_IC = (f, v) -> f.likeIgnoreCase(likeWrap(v));

    public static final BiFunction<Field<Boolean>, String, Condition> BOOL_EQ = (f, v) -> f.eq(toBool(v));

    public static final BiFunction<Field<Integer>, String, Condition> INT_EQ = (f, v) -> f.eq(toInt(v));

    public static final BiFunction<Field<UUID>, String, Condition> UUID_EQ = (f, v) -> f.eq(toUuid(v));
    public static final BiFunction<Field<UUID>, String, Condition> UUID_NULL_EQ = (f, v) -> {
        if (v.isEmpty())
            return f.isNull();
        else return f.eq(toUuid(v));
    };

    public static final BiFunction<Field<OffsetDateTime>, String, Condition> DATE_TIME_EQ = (f, v) -> f.eq(dateTime(v));
    public static final BiFunction<Field<OffsetDateTime>, String, Condition> DATE_TIME_FROM = (f, v) -> f.ge(dateTime(v));
    public static final BiFunction<Field<OffsetDateTime>, String, Condition> DATE_TIME_TO = (f, v) -> f.lt(dateTime(v));

    public static final BiFunction<Field<DateTime>, String, Condition> DTT_MIN_EQ_STR =
            (f, v) -> f.ge(getTimestamp(v));

    public static final BiFunction<Field<DateTime>, String, Condition> DTT_MAX_EQ_STR =
            (f, v) -> f.le(getTimestamp(v));

    public static final BiFunction<Field<Integer>, String, Condition> INT_MIN_EQ_STR =
            (f, v) -> f.ge(getInteger(v));

    public static final BiFunction<Field<Integer>, String, Condition> INT_MAX_EQ_STR =
            (f, v) -> f.le(getInteger(v));


    private JooqSearchUtils() {
        // utils class
    }

    public static <T> BiFunction<Field<T>, String, Condition> listOr(BiFunction<Field<T>, String, Condition> conditionLambda) {
        return (f, v) -> {
            Condition res = null;
            for (var value : v.split(",")) {
                Condition nextCondition = conditionLambda.apply(f, value);
                if (res == null) {
                    res = nextCondition;
                } else {
                    res = res.or(nextCondition);
                }
            }
            return res;
        };
    }

    public static String likeWrap(String value) {
        return "%" + DSL.escape(value, '%') + "%";
    }

    private static boolean toBool(String value) {
        if ("true".equalsIgnoreCase(value) || "1".equals(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value) || "0".equals(value)) {
            return false;
        }

        throw new RuntimeException("can not convert: " + value + " to boolean");
    }

    private static int toInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new RuntimeException("can not convert: " + value + " to int");
        }
    }

    private static UUID toUuid(String value) {
        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            throw new RuntimeException("can not convert: " + value + " to boolean");
        }
    }

    private static OffsetDateTime dateTime(String value) {
        return OffsetDateTime.parse(value);
    }

    private static DateTime getTimestamp(String s) {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parseDateTime(s);
    }

    private static Integer getInteger(String s) {
        return Integer.valueOf(s);
    }

}

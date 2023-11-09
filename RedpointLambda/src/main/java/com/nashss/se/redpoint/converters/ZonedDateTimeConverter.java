package com.nashss.se.redpoint.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter implements DynamoDBTypeConverter<String, ZonedDateTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public String convert(ZonedDateTime time) {
        return FORMATTER.format(time);
    }

    @Override
    public ZonedDateTime unconvert(String s) {
        return ZonedDateTime.parse(s, FORMATTER);
    }
}

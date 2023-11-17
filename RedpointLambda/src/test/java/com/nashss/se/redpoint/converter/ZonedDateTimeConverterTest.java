package com.nashss.se.redpoint.converter;

import com.nashss.se.redpoint.converters.ZonedDateTimeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZonedDateTimeConverterTest {
    ZonedDateTimeConverter converter;
    ZonedDateTime zonedDateTime;
    String dateTimeString;

    @BeforeEach
    public void setup() {
        converter = new ZonedDateTimeConverter();
        dateTimeString = "2023-11-17T12:34:56Z";
        zonedDateTime = ZonedDateTime.of(2023, 11, 17, 12, 34, 56, 0, ZoneId.of("Z"));
    }

    @Test
    public void convert_withZonedDateTime_returnsString() {
        assertEquals(converter.convert(zonedDateTime), dateTimeString);
    }

    @Test
    public void unconvert_withString_returnsZonedDateTime() {
        assertEquals(converter.unconvert(dateTimeString), zonedDateTime);
    }
}


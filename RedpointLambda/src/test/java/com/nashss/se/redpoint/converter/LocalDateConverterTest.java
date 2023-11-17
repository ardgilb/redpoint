package com.nashss.se.redpoint.converter;

import com.nashss.se.redpoint.converters.LocalDateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalDateConverterTest {
    LocalDateConverter converter;
    LocalDate localDate;
    String date;
    @BeforeEach
    public void setup(){
        converter = new LocalDateConverter();
        date = "2023-11-17";
        localDate = LocalDate.of(2023, 11, 17);
    }
    @Test
    public void convert_withLocalDate_returnsString(){
        assertEquals(converter.convert(localDate), date);
    }
    @Test
    public void unconvert_withString_returnsDate(){
        assertEquals(converter.unconvert(date), localDate);
    }
}

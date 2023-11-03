package com.nashss.se.redpoint.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.redpoint.dynamodb.models.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class AreaDaoTest {
    HttpClient client = HttpClient.newHttpClient();
    DynamoDBMapper mapper;
    AreaDao areaDao;
    @BeforeEach
    public void setup(){
        areaDao = new AreaDao(mapper, client);
    }
    @Test
    public void getAllAreasTest() throws IOException, InterruptedException {
        String query = "Obed";
        List<Area> result = areaDao.getAllAreasFromQuery(query);
    }
    @Test
    public void getAreaTest() throws IOException, InterruptedException {
        String uuid = "c45a18d4-5e0d-5637-a0e9-a018520dfccb";
        Area result = areaDao.getArea(uuid);
    }
}

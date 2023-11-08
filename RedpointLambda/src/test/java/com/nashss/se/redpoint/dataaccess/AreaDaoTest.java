package com.nashss.se.redpoint.dataaccess;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.redpoint.dataaccess.models.Area;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class AreaDaoTest {
    HttpClient client = HttpClient.newHttpClient();
    AreaDao areaDao;
    @BeforeEach
    public void setup(){
        areaDao = new AreaDao(client);
    }
    @Test
    public void getAllAreasTest() throws IOException, InterruptedException {
        String query = "Right Bunker";
        List<Area> result = areaDao.getAllAreasFromQuery(query);
    }
    @Test
    public void getAreaTest() throws IOException, InterruptedException {
        String uuid = "72860a67-e121-5ab7-8bb2-0f2d9be3cdf9";
        Area result = areaDao.getArea(uuid);
    }
}

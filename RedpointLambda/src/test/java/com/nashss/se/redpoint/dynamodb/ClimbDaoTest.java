package com.nashss.se.redpoint.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.redpoint.dynamodb.models.Area;
import com.nashss.se.redpoint.dynamodb.models.Climb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class ClimbDaoTest {
    HttpClient client = HttpClient.newHttpClient();
    DynamoDBMapper mapper;
    ClimbDao climbDao;
    @BeforeEach
    public void setup(){
        climbDao = new ClimbDao(mapper, client);
    }
    @Test
    public void getAllAreasTest() throws IOException, InterruptedException {
        String uuid = "0082d06b-e8ea-5cdf-8002-aab5cbe1c4ae";
        Climb result = climbDao.getClimb(uuid);
    }
}

package com.nashss.se.redpoint.dataaccess;

import com.nashss.se.redpoint.dataaccess.models.Climb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;

public class ClimbDaoTest {
    HttpClient client = HttpClient.newHttpClient();
    ClimbDao climbDao;
    @BeforeEach
    public void setup(){
        climbDao = new ClimbDao(client);
    }
    @Test
    public void getClimbTest() {
        String uuid = "0082d06b-e8ea-5cdf-8002-aab5cbe1c4ae";
        Climb result = climbDao.getClimb(uuid);
    }
}

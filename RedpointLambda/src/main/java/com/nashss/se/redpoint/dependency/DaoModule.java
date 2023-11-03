package com.nashss.se.redpoint.dependency;

import com.nashss.se.redpoint.dataaccess.DynamoDbClientProvider;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import dagger.Module;
import dagger.Provides;

import java.net.http.HttpClient;

import javax.inject.Singleton;


/**
 * Dagger Module providing dependencies for DAO classes.
 */
@Module
public class DaoModule {
    /**
     * Provides a DynamoDBMapper singleton instance.
     *
     * @return DynamoDBMapper object
     */
    @Singleton
    @Provides
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient(Regions.US_EAST_2));
    }
    /**
     * Provides a HttpClient singleton instance.
     *
     * @return HttpClient object
     */
    @Singleton
    @Provides
    public HttpClient provideHttpClient() {
        return HttpClient.newHttpClient();
    }
}

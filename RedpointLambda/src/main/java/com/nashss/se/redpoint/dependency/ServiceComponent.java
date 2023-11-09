package com.nashss.se.redpoint.dependency;

import com.nashss.se.redpoint.activity.*;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in Redpoint.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     * @return GetAllAreasActivity
     */
    GetAllAreasActivity provideGetAllAreasActivity();
    /**
     * Provides the relevant activity.
     * @return GetAreaActivity
     */
    GetAreaActivity provideGetAreaActivity();
    /**
     * Provides the relevant activity.
     * @return GetClimbActivity
     */
    GetClimbActivity provideGetClimbActivity();
    /**
     * Provides the relevant activity.
     * @return CreateCommentActivity
     */
    CreateCommentActivity provideCreateCommentActivity();
    /**
     * Provides the relevant activity.
     * @return CreateCommentActivity
     */
    DeleteCommentActivity provideDeleteCommentActivity();

}


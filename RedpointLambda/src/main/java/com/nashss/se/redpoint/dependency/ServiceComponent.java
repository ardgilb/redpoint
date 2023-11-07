package com.nashss.se.redpoint.dependency;

import com.nashss.se.redpoint.activity.GetAllAreasActivity;
import com.nashss.se.redpoint.activity.GetAreaActivity;
import com.nashss.se.redpoint.activity.GetClimbActivity;

import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in Redpoint.
 */
@Singleton
@Component(modules = {DaoModule.class})
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
}


package com.nashss.se.redpoint.dependency;

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
     * @return
     */

}


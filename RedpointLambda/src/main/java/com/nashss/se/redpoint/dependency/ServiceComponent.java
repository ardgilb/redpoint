package com.nashss.se.redpoint.dependency;

import com.nashss.se.redpoint.activity.CreateCommentActivity;
import com.nashss.se.redpoint.activity.CreateLogbookEntryActivity;
import com.nashss.se.redpoint.activity.DeleteCommentActivity;
import com.nashss.se.redpoint.activity.DeleteLogbookEntryActivity;
import com.nashss.se.redpoint.activity.GetAllAreasActivity;
import com.nashss.se.redpoint.activity.GetAllLogbookEntriesForUserActivity;
import com.nashss.se.redpoint.activity.GetAreaActivity;
import com.nashss.se.redpoint.activity.GetClimbActivity;
import com.nashss.se.redpoint.activity.GetCommentsForClimbActivity;

import com.nashss.se.redpoint.activity.UpdateLogbookEntryActivity;

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
    /**
     * Provides the relevant activity.
     * @return GetCommentsForClimbActivity
     */
    GetCommentsForClimbActivity provideGetCommentsForClimbActivity();
    /**
     * Provides the relevant activity.
     * @return CreateLogbookEntryActivity
     */
    CreateLogbookEntryActivity provideCreateLogbookEntryActivity();
    /**
     * Provides the relevant activity.
     * @return UpdateLogbookEntryActivity
     */
    UpdateLogbookEntryActivity provideUpdateLogbookEntryActivity();
    /**
     * Provides the relevant activity.
     * @return DeleteLogbookEntryActivity
     */
    DeleteLogbookEntryActivity provideDeleteLogbookEntryActivity();
    /**
     * Provides the relevant activity.
     * @return GetAllLogbookEntriesForUser
     */
    GetAllLogbookEntriesForUserActivity provideGetAllLogbookEntriesForUserActivity();
}


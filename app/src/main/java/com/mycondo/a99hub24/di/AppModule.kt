package com.mycondo.a99hub24.di

import android.content.Context
import com.mycondo.a99hub24.data.network.*
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.repository.AuthRepository
import com.mycondo.a99hub24.data.repository.ChangePassRepository
import com.mycondo.a99hub24.data.repository.HomeRepository
import com.mycondo.a99hub24.data.repository.LedgerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource()
    }

    /*---------------START-LOGIN----------------*/
    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java, context)
    }

    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepository(authApi, userPreferences)
    }
    /*--------------END-LOGIN----------------*/


    /*--------------START-CHANGE-PASS----------------*/
    @Singleton
    @Provides
    fun provideChangePassApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): ChangePassApi {
        return remoteDataSource.buildApi(ChangePassApi::class.java, context)
    }

    @Provides
    fun provideChangePassRepository(
        ledgerApi: ChangePassApi
    ): ChangePassRepository {
        return ChangePassRepository(ledgerApi)
    }
    /*--------------END-CHANGE-PASS----------------*/

    /*-------------START-HOME-INPLAY----------------*/
    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): HomeApi {
        return remoteDataSource.buildApi(HomeApi::class.java, context)
    }

    @Provides
    fun provideUserRepository(
        homeApi: HomeApi,
        limitPreferences: LimitPreferences
    ): HomeRepository {
        return HomeRepository(homeApi, limitPreferences)
    }
    /*-------------END-HOME-INPLAY----------------*/


    /*-------------START-LEDGER----------------*/
    @Singleton
    @Provides
    fun provideLedgerApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): LedgerApi {
        return remoteDataSource.buildApi(LedgerApi::class.java, context)
    }

    @Provides
    fun provideLedgerRepository(
        ledgerApi: LedgerApi
    ): LedgerRepository {
        return LedgerRepository(ledgerApi)
    }
    /*-------------END-LEDGER----------------*/

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideLimitPreferences(@ApplicationContext context: Context): LimitPreferences {
        return LimitPreferences(context)
    }



}
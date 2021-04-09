package com.mycondo.a99hub24.di

import android.content.Context
import com.mycondo.a99hub24.common.Common
import com.mycondo.a99hub24.data.network.*
import com.mycondo.a99hub24.data.preferences.LimitPreferences
import com.mycondo.a99hub24.data.preferences.UserPreferences
import com.mycondo.a99hub24.data.repository.AuthRepository
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

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): AuthApi {
        return remoteDataSource.buildApi(AuthApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): HomeApi {
        return remoteDataSource.buildApi(HomeApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideLedgerApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): LedgerApi {
        return remoteDataSource.buildApi(LedgerApi::class.java, context)
    }

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideLimtiPreferences(@ApplicationContext context: Context): LimitPreferences {
        return LimitPreferences(context)
    }
    @Singleton
    @Provides
    fun provideCommon(@ApplicationContext context: Context): Common {
        return Common(context)
    }


    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepository(authApi, userPreferences)
    }

    @Provides
    fun provideUserRepository(
        homeApi: HomeApi,
        limitPreferences: LimitPreferences
    ): HomeRepository {
        return HomeRepository(homeApi, limitPreferences)
    }

    @Provides
    fun provideLedgerRepository(
        ledgerApi: LedgerApi
    ): LedgerRepository {
        return LedgerRepository(ledgerApi)
    }
}
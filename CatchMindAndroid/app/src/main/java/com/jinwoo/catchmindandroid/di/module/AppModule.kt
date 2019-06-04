package com.jinwoo.catchmindandroid.di.module

import android.app.Application
import android.content.Context
import com.jinwoo.catchmindandroid.di.BaseApp
import com.jinwoo.catchmindandroid.di.scope.ActivityScope
import com.jinwoo.catchmindandroid.util.AutoDrawClass
import com.jinwoo.catchmindandroid.util.DrawClass
import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.util.SocketApplication
import dagger.Module
import dagger.Provides
import io.socket.client.Socket
import javax.inject.Singleton

@Module(includes = [DrawModule::class, ObjectModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(app: BaseApp): Context = app

    @Provides
    @Singleton
    fun provideApplication(app: BaseApp): Application = app
}
package com.jinwoo.catchmindandroid.di.module

import com.jinwoo.catchmindandroid.util.GameData
import com.jinwoo.catchmindandroid.util.SocketApplication
import dagger.Module
import dagger.Provides
import io.socket.client.Socket
import javax.inject.Singleton

@Module
class ObjectModule {
    @Singleton
    @Provides
    fun provideSocket(): Socket = SocketApplication.socket

    @Singleton
    @Provides
    fun provideGameData(): GameData = GameData
}
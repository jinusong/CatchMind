package com.jinwoo.catchmindandroid.di.module

import android.content.Context
import com.jinwoo.catchmindandroid.util.AutoDrawClass
import com.jinwoo.catchmindandroid.util.DrawClass
import dagger.Module
import dagger.Provides
import io.socket.client.Socket
import javax.inject.Singleton

@Module
class DrawModule {
    @Singleton
    @Provides
    fun provideDrawClass(socket: Socket, context: Context)
            = DrawClass(context, socket)

    @Singleton
    @Provides
    fun provideAutoDrawClass(socket: Socket, context: Context)
            = AutoDrawClass(context, socket)
}
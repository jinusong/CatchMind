package com.jinwoo.catchmindandroid.di.component

import com.jinwoo.catchmindandroid.di.module.AppModule
import com.jinwoo.catchmindandroid.di.BaseApp
import com.jinwoo.catchmindandroid.di.module.ActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityModule::class, AndroidSupportInjectionModule::class])
interface AppComponent: AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApp): AppComponent.Builder
        fun build(): AppComponent
    }
}
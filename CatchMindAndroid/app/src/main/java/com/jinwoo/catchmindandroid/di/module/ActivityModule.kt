package com.jinwoo.catchmindandroid.di.module

import com.jinwoo.catchmindandroid.di.scope.ActivityScope
import com.jinwoo.catchmindandroid.di.scope.DialogFragmentScope
import com.jinwoo.catchmindandroid.ui.activity.MainActivity
import com.jinwoo.catchmindandroid.ui.activity.ReadyActivity
import com.jinwoo.catchmindandroid.ui.activity.SubMainActivity
import com.jinwoo.catchmindandroid.ui.dialog.EndDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    abstract fun readyActivity(): ReadyActivity

    @ActivityScope
    abstract fun mainActivity(): MainActivity

    @ActivityScope
    abstract fun subMainActivity(): SubMainActivity

    @DialogFragmentScope
    abstract fun endDialog(): EndDialog
}
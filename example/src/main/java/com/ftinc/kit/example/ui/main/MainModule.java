/*
 * Copyright (c) 2015 52inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ftinc.kit.example.ui.main;

import com.ftinc.kit.example.ui.UiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by r0adkll on 3/12/15.
 */
@Module(
    injects = MainActivity.class,
    addsTo = UiModule.class,
    complete = false
)
public class MainModule {

    private MainView mView;

    public MainModule(MainView view) {
        this.mView = view;
    }

    @Provides @Singleton
    MainView provideView(){
        return mView;
    }

    @Provides @Singleton
    MainPresenter providePresenter(MainView view){
        return new MainPresenterImpl(view);
    }

}

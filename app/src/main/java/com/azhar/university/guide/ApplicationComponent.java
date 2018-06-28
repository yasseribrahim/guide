package com.azhar.university.guide;

import com.azhar.university.guide.domain.modules.PreferencesModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yasser.Ibrahim on 6/28/2018.
 */

@Singleton
@Component(modules = {PreferencesModule.class})
public interface ApplicationComponent {
}

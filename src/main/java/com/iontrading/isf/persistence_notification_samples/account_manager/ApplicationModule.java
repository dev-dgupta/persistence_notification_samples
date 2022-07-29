/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.account_manager;

import com.google.inject.AbstractModule;
import com.iontrading.isf.boot.guice.BootModule;
import com.iontrading.isf.committer.guice.PersistenceNotificationModule;
import com.iontrading.isf.modules.annotation.ModuleDescriptor;
import com.iontrading.talk.ionbus.guice.TalkIonBusModule;

/**
 * Guice module that provides the application services.
 *
 */
@ModuleDescriptor(requires = { PersistenceNotificationModule.class, BootModule.class, TalkIonBusModule.class })
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        TalkIonBusModule.exportFunctions(binder(), ProcessAccountService.class);
        TalkIonBusModule.exportFunctions(binder(), ModifyAccountService.class);
        TalkIonBusModule.exportFunctions(binder(), DeleteAccountService.class);
    }
}

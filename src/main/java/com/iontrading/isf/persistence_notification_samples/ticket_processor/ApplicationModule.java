/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.iontrading.isf.boot.guice.BootModule;
import com.iontrading.isf.boot.spi.IBootService.RunPhase;
import com.iontrading.isf.committer.guice.PersistenceNotificationModule;
import com.iontrading.isf.modules.annotation.ModuleDescriptor;
import com.iontrading.talk.api.guice.TalkModule;
import com.iontrading.talk.ionbus.guice.TalkIonBusModule;

/**
 * Guice module that provides the application services. It is required to set
 * the list of services that the application depends on through the ModuleInfo
 * annotation. In this example the only dependency to specify is
 * PersistenceNotificationModule
 *
 */
@ModuleDescriptor(requires = { TalkIonBusModule.class, PersistenceNotificationModule.class })
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(GlobalConfigurationService.class).in(Singleton.class);
        BootModule.registerBootService(binder(), GlobalConfigurationService.class, RunPhase.INIT);

        // Register the services that we provide publicly on the ION bus
        bind(TicketService.class).in(Singleton.class);
        TalkModule.exportFunctions(binder(), TicketService.class);
    }
}

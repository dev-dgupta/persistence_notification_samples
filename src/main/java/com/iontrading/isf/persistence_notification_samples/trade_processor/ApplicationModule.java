/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.iontrading.isf.boot.guice.BootModule;
import com.iontrading.isf.boot.spi.IBootService.RunPhase;
import com.iontrading.isf.committer.guice.PersistenceNotificationModule;
import com.iontrading.isf.modules.annotation.ModuleDescriptor;
import com.iontrading.talk.api.guice.TalkModule;

/**
 * Guice module that provides the application services.
 *
 */
@ModuleDescriptor(requires = { PersistenceNotificationModule.class, BootModule.class })
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PositionByInstrumentCalculator.class).in(Singleton.class);
        BootModule.registerBootService(binder(), PositionByInstrumentCalculator.class, RunPhase.START);

        bind(TradeService.class).in(Singleton.class);
        TalkModule.exportFunctions(binder(), TradeService.class);
    }
}

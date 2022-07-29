/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 *
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor;

import com.google.inject.Inject;
import com.iontrading.isf.boot.spi.IBootService.RunPhase;
import com.iontrading.isf.boot.spi.IService;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.LinkTableNameGenerator;
import com.iontrading.isf.committer.spi.PNGlobalConfigurator;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Ticket;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Trade;

/**
 * Global, application-wide setup that will run during the {@link RunPhase.INIT} boot phase.
 * Registered in {@link ApplicationModule#configure()}.
 */
final class GlobalConfigurationService implements IService {
    
    private final PNGlobalConfigurator pnGlobalConfigurator;
    
    @Inject
    GlobalConfigurationService(PNGlobalConfigurator pnGlobalConfigurator) {
        this.pnGlobalConfigurator = pnGlobalConfigurator;
    }
    
    @Override
    public void start() throws Exception {
        // Override the names of the db tables used for entities linking. Note that this is optional 
        pnGlobalConfigurator.withLinkTableGenerator(new LinkTableNameGenerator() {
            @Override
            public String getLinkTableName(Class<? extends DomainEntity> source, Class<? extends DomainEntity> destination,
                    String linkName, String defaultName) {
                if (source == Trade.class && destination == Ticket.class)
                    return "TRD_TKT";
                return defaultName;
            }
        });
        
    }

    @Override
    public void shutdown() {
    }

    @Override
    public String getName() {
        return "PNGlobalConfigurationService";
    }
}
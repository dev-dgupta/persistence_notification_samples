/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor;

import com.iontrading.isf.applicationserver.spi.AS;
import com.iontrading.isf.boot.spi.IBootServiceManager;
import com.iontrading.isf.modules.ModuleContext;

/**
 * Application start-up entry point.
 * 
 * It starts the Mkv engine and initializes the Guice environment with the
 * required modules for the persistence notification engine and the application
 * services.
 * 
 * <p>
 * The following steps are required:
 * <li>Implement the {@link ApplicationModule} which defines its dependencies (through the @ModuleInfo annotation)</li>
 * <li>Implement the {@link ApplicationPNConfiguration} to register the domain entities to the persistence and
 * notification service</li>
 * <li>Create the injector with the {@link ModuleContext} by the providing the modules above</li>
 * <li>run the application through the {@link IBootServiceManager}</li>
 * 
 */

public class ApplicationLauncher {

    public static void main(String[] args) {
        AS.createLaunchConfiguration()
        // pass command line arguments to the application
                .withArgs(args)
                // Entry point modules for the application
                .withModules(ApplicationModule.class, ApplicationPNConfiguration.class)
                // configure ION Component properties
                .withComponentInfo("TradeProcessor", "Trade Processor ION 2.0 sample", "1.0.0", "NA")
                // starts the application
                .launch();
    }
}

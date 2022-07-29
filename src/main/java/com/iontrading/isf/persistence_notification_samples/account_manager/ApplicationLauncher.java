/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.account_manager;

import com.iontrading.isf.applicationserver.spi.AS;
//import com.iontrading.isf.pn.ddm.DomainTrade_DomainTicket;

/**
 * Application start-up entry point.
 * 
 * It starts the Mkv engine and initializes the Guice environment with the
 * required modules for the persistence notification engine and the application
 * services.
 * 
 * <p>The following steps are required:
 * <li>Implement the {@link ApplicationModule} which defines its dependencies (through the @ModuleInfo annotation)</li> 
 * <li>Implement the {@link ApplicationPNConfiguration} to register the domain entities to the persistence and notification service</li> 
 *  
 */
public class ApplicationLauncher {
    /**
     * @param args
     */
    public static void main(String[] args) {
        AS.createLaunchConfiguration()
        // pass command line arguments to the application
                .withArgs(args)
                // Entry point modules for the application
                .withModules(ApplicationModule.class, ApplicationPNConfiguration.class)
                // configure ION Component properties
                .withComponentInfo("AccountManager", "Account Manager ION 2.0 sample", "1.0.0", "NA")
                // starts the application
                .launch();
    }
}

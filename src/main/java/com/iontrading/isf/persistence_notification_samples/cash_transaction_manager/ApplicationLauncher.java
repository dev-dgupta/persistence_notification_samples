/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager;

import com.iontrading.isf.applicationserver.spi.AS;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.CashTransaction;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PublishableCashTransactionMovement;
import com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator.PublishableCashTransactionMovementMapper;

/**
 * This sample shows how it is possible to configure P&N so that, for a given
 * pair of linked entities ({@link CashTransaction} and {@link CashMovement}),
 * it provides a recoverable stream of notifications, through the P&N
 * MessageQueue notification protocol, with each notification representing a
 * (suitable, user-defined) set of joined data of two linked entities (
 * {@link CashTransaction} - {@link CashMovement}).
 *
 * <p>
 * P&N setup requirements.
 * 
 * In order to activate this feature, the user, in addition to register in P&N
 * the involved domain entities in the usual way, must provide a publishable
 * entity (see {@link PublishableCashTransactionMovement}) used to notify the
 * set of joined data, and a mapper for this publishable entity (see
 * {@link PublishableCashTransactionMovementMapper}).
 * 
 * 
 * <p>
 * Application start-up entry point.
 * 
 * It starts the Mkv engine and initializes the Guice environment with the
 * required modules for the persistence notification engine and the application
 * services.
 * 
 * <p>
 * The following steps are required:
 * <li>Implement the {@link CashTransactionApplicationModule} which defines its
 * dependencies (through the @ModuleInfo annotation)</li>
 * <li>Implement the {@link CashTransactionApplicationPNConfiguration} to
 * register the domain entities to the persistence and notification service</li>
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
            .withModules(CashTransactionApplicationModule.class)
            // configure ION Component properties
            .withComponentInfo("CashTransactionManager", "Cash Transaction Manager ION 2.0 sample", "1.0.0", "NA")
            // starts the application
            .launch();
    }
}

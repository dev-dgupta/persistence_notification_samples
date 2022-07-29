package com.iontrading.isf.persistence_notification_samples.trade_processor;


import com.iontrading.isf.committer.spi.AbstractPNConfiguration;
import com.iontrading.isf.committer.spi.PNConfigurator;
import com.iontrading.isf.persistence_notification_samples.trade_processor.configurator.DomainTrade;
import com.iontrading.isf.persistence_notification_samples.trade_processor.configurator.PersistableTrade;
import com.iontrading.isf.persistence_notification_samples.trade_processor.configurator.PersistableTradeMapper;
import com.iontrading.isf.persistence_notification_samples.trade_processor.configurator.TradeExternalId;

/**
 * Register the Trade domain entity together its related persistable and
 * publishable classes. The trade is revisionable (the default)
 * 
 *
 * The configuration also shows how to setup the external id {@Code
 * TradeExternalId} with enforced uniqueness constraint
 */
public class ApplicationPNConfiguration extends AbstractPNConfiguration {

    @Override
    public void onConfigure(PNConfigurator config) {
        config.registerPersistable(PersistableTrade.class)
            .withExternalId(TradeExternalId.class)
            .withEnforcedUniquenessOnExternalId(TradeExternalId.class)
            .register();
        
        config.registerDomain(DomainTrade.class)
            .withPersistable(PersistableTrade.class).toDomain(PersistableTradeMapper.class).fromDomain(PersistableTradeMapper.class)
            .register();
    }
}

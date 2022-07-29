package com.iontrading.isf.persistence_notification_samples.ticket_processor;

import com.iontrading.isf.committer.spi.AbstractPNConfiguration;
import com.iontrading.isf.committer.spi.PNConfigurator;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PersistableTicket;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PersistableTicketMapper;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PersistableTrade;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PersistableTradeMapper;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PublishableTicket;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PublishableTicketMapper;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PublishableTrade;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.PublishableTradeMapper;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Ticket;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Trade;

/**
 * Register the Trade and Ticket entities with theirs related persistable and publishable classes. They are both revisionable (the default).
 */
public class ApplicationPNConfiguration extends AbstractPNConfiguration {

    @Override
    public void onConfigure(PNConfigurator config) {
        config.registerPersistable(PersistableTrade.class).register();
        config.registerPersistable(PersistableTicket.class).register();

        config.registerDomain(Trade.class)
            .withPublishable(PublishableTrade.class, PublishableTradeMapper.class)
            .withPersistable(PersistableTrade.class).fromDomain(PersistableTradeMapper.class).toDomain(PersistableTradeMapper.class)
        .register();

        config.registerDomain(Ticket.class)
            .withPublishable(PublishableTicket.class, PublishableTicketMapper.class)
            .withPersistable(PersistableTicket.class).fromDomain(PersistableTicketMapper.class).toDomain(PersistableTicketMapper.class)
        .register();
    }
}

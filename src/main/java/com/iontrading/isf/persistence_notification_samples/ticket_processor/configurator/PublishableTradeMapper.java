/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator;

import com.iontrading.isf.committer.spi.MapperException;
import com.iontrading.isf.committer.spi.PublishableFromDomainMapper;

/**
 * 
 * Mapper from the domain trade to the publishable trade entities.
 * 
 * This mapper is invoked by the notification engine when a Trade is committed in order to convert it to a
 * PublishableTrade
 * 
 */

public class PublishableTradeMapper implements PublishableFromDomainMapper<PublishableTrade, Trade> {

    @Override
    //@fmt:off
    public PublishableTrade write(Trade domainTrade) throws MapperException {
    //@fmt:on
        return new PublishableTrade(domainTrade);
    }

    @Override
    //@fmt:off
    public Class<? extends PublishableTrade> getPublishableEntityClass() {
    //@fmt:on
        return PublishableTrade.class;
    }
}

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
import com.iontrading.isf.committer.spi.PersistableFromDomainMapper;
import com.iontrading.isf.committer.spi.PersistableToDomainMapper;

/**
 * Mapper from the domain trade to the persistable trade entities and vice-versa.
 * 
 * The mapper is invoked by the persistence engine when:
 * - a domain trade is committed (mapping from @{link Trade} to @{link PersistableTrade}).
 * - a domain trade is fetched (mapping from @{link PersistableTrade} to the @{link Trade}).
 * 
 * Note: the relation with the Ticket is automatically managed by the persistence and notification engine.
 * 
 */
public class PersistableTradeMapper
implements PersistableFromDomainMapper<PersistableTrade, Trade>, PersistableToDomainMapper<PersistableTrade, Trade> {

    @Override
    //@fmt:off
    public PersistableTrade write(Trade domainTrade) throws MapperException {
    //@fmt:on
        return new PersistableTrade(domainTrade);
    }

    @Override
    public Class<? extends PersistableTrade> getPersistableEntityClass() {
        return PersistableTrade.class;
    }

    @Override
    //@fmt:off
    public Trade read(PersistableTrade persistableTrade) throws MapperException {
    //@fmt:on
        return persistableTrade.getDomain();
    }

    @Override
    public Class<? extends Trade> getDomainEntityClass() {
        return Trade.class;
    }

}

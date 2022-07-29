/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor.configurator;

import com.iontrading.isf.committer.spi.MapperException;
import com.iontrading.isf.committer.spi.PersistableFromDomainMapper;
import com.iontrading.isf.committer.spi.PersistableToDomainMapper;

/**
 * Mapper from the domain trade to the persistable trade entities and vice-versa.
 * 
 * The mapper is invoked by the persistence engine when:
 * - a domain trade is committed (mapping from @{link DomainTrade} to @{link PersistableTrade}).
 * - a domain trade is fetched (mapping from @{link PersistableTrade} to the @{link DomainTrade}).
 * 
 */
public class PersistableTradeMapper implements PersistableFromDomainMapper<PersistableTrade, DomainTrade>,
        PersistableToDomainMapper<PersistableTrade, DomainTrade> {

    @Override
    public PersistableTrade write(DomainTrade trade) throws MapperException {
        PersistableTrade persistable = new PersistableTrade();
        persistable.setExternalId(trade.getExternalId());
        persistable.setInstrumentId(trade.getInstrumentId());
        persistable.setQty(trade.getQty());
        persistable.setValue(trade.getValue());
        persistable.setVerb(trade.getVerb());
        return persistable;
    }

    @Override
    public Class<? extends DomainTrade> getDomainEntityClass() {
        return DomainTrade.class;
    }

    @Override
    public DomainTrade read(PersistableTrade persistable) throws MapperException {
        DomainTrade trade = new DomainTrade();
        trade.setExternalId(persistable.getExternalId());
        trade.setInstrumentId(persistable.getInstrumentId());
        trade.setQty(persistable.getQty());
        trade.setValue(persistable.getValue());
        trade.setVerb(persistable.getVerb());
        return trade;
    }

    @Override
    public Class<? extends PersistableTrade> getPersistableEntityClass() {
        return PersistableTrade.class;
    }
}

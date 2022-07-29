/*
 * Created: Sep 12, 2013
 *
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
 * Mapper from Ticket to PersistableTicek and vice-versa.
 *
 */
public class PersistableTicketMapper
implements PersistableFromDomainMapper<PersistableTicket, Ticket>, PersistableToDomainMapper<PersistableTicket, Ticket> {

    @Override
    //@fmt:off
    public PersistableTicket write(Ticket domainTrade) throws MapperException {
    //@fmt:on
        return new PersistableTicket(domainTrade);
    }

    @Override
    public Class<? extends PersistableTicket> getPersistableEntityClass() {
        return PersistableTicket.class;
    }

    @Override
    //@fmt:off
    public Ticket read(PersistableTicket persistableTrade) throws MapperException {
    //@fmt:on
        return persistableTrade.getDomain();
    }

    @Override
    public Class<? extends Ticket> getDomainEntityClass() {
        return Ticket.class;
    }

}

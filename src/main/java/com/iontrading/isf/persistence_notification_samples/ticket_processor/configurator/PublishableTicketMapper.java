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
import com.iontrading.isf.committer.spi.PublishableFromDomainMapper;

/**
 * 
 * Mapper from the domain ticket to the publishable ticket entities.
 * 
 * This mapper is invoked by the notification engine when a Ticket is committed to convert it to a
 * PublishableTicket
 * 
 */
public class PublishableTicketMapper implements PublishableFromDomainMapper<PublishableTicket, Ticket> {

    @Override
    //@fmt:off
    public PublishableTicket write(Ticket domain) throws MapperException {
    //@fmt:on
        return new PublishableTicket(domain);
    }

    @Override
    //@fmt:off
    public Class<? extends PublishableTicket> getPublishableEntityClass() {
    //@fmt:on
        return PublishableTicket.class;
    }

}

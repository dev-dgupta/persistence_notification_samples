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

import com.iontrading.isf.committer.spi.AbstractPublishableEntity;
import com.iontrading.talk.api.annotation.TalkProperty;
import com.iontrading.talk.api.annotation.TalkType;

/**
 * Ticket bean object that is serialized on the MQ.
 * 
 * In this example it is just a delegate to the Ticket.
 */
@TalkType(name = "Ticket")
public class PublishableTicket extends AbstractPublishableEntity {
    private Ticket domainEntity;

    public PublishableTicket(Ticket domainEntity) {
        this.domainEntity = domainEntity;
    }

    @TalkProperty
    public String getInstrumentId() {
        return this.domainEntity.getInstrumentId();
    }

    @TalkProperty
    public String getCounterparty() {
        return this.domainEntity.getCounterparty();
    }

    @TalkProperty
    public String getBook() {
        return this.domainEntity.getBook();
    }

    @TalkProperty
    public String getContrabook() {
        return this.domainEntity.getContrabook();
    }
    @TalkProperty
    public String getStatus() {
        return domainEntity.getStatus().toString();
    }
    
}

/*
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
 * Trade bean object that is serialized on the MQ.
 * 
 * In this example it is just a delegate to the Trade.
 * 
 */

@TalkType(name = "Trade")
public class PublishableTrade extends AbstractPublishableEntity {

    private Trade domainEntity;

    public PublishableTrade(Trade domainEntity) {
        this.domainEntity = domainEntity;
    }

    @TalkProperty
    public String getInstrumentId() {
        return this.domainEntity.getInstrumentId();
    }

    @TalkProperty
    public double getValue() {
        return this.domainEntity.getValue();
    }

    @TalkProperty
    public double getQty() {
        return this.domainEntity.getQty();
    }

    @TalkProperty
    public String getVerb() {
        return this.domainEntity.getVerb();
    }

    @TalkProperty
    public String getParentTicket() {
        return this.domainEntity.getParentTicket().getIdentifier().getId();
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
    public String getContraBook() {
        return this.domainEntity.getContraBook();
    }
    
    @TalkProperty
    public String getStatus() {
        return domainEntity.getStatus().toString();
    }
    
}

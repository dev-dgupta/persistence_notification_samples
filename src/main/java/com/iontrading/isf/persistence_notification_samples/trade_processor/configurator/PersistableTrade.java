/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor.configurator;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.iontrading.isf.committer.spi.AbstractPersistablePublishableEntity;
import com.iontrading.talk.api.annotation.TalkProperty;
import com.iontrading.talk.api.annotation.TalkType;

/**
 * Hibernate annotated trade bean object that is serialized/deserialized to and from the database.
 * 
 * In this example it is just a delegate to the @{link DomainTrade}.
 * 
 */
@Entity
@Table(name = "TRADE")
@TalkType(name = "Trade")
public class PersistableTrade extends AbstractPersistablePublishableEntity {

    /**   */
    private static final long serialVersionUID = 443694950228200941L;

    private String externalId;
    private String instrumentId;
    private double value;
    private double qty;
    private String verb;

    public PersistableTrade() {
    }

    // TalkProperty makes the field available in publishing too
    @TalkProperty
    // Persistence attributes can be annotated with JPA annotations
    @Index(name = "EXTERNALID_IDX")
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @TalkProperty
    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    @TalkProperty
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @TalkProperty
    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    @TalkProperty
    public String getVerb() {
        return this.verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

}

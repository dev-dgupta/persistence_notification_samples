/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor.configurator;

import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.RevisionIdentifier;

/**
 * Domain trade entity.
 * It implements the trade from business point of view only.
 * 
 */
public class DomainTrade implements DomainEntity {
    private RevisionIdentifier id;
    
    private final DomainTrade previous;

    // example of trade fields:
    private String externalId;
    private String instrumentId;
    private double value;
    private double qty;
    private String verb;


    /*
     * Constructor for new entity
     */
    public DomainTrade() {
        this.previous = null;
    }

    /*
     * Constructor for amended entity
     */
    public DomainTrade(DomainTrade previous) {
        this.previous = previous;
    }

    // Required by the persistence and notification service to amend the trade.
    @Override
    public RevisionIdentifier getPreviousIdentifier() {
        return (previous != null) ? previous.getIdentifier() : null;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getQty() {
        return this.qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getVerb() {
        return this.verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    @Override
    public RevisionIdentifier getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(RevisionIdentifier newId) {
        id = newId;
    }

    public DomainTrade getPrevious() {
        return previous;
    }
}

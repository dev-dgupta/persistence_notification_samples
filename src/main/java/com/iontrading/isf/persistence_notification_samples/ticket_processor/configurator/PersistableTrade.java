/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iontrading.isf.committer.spi.AbstractPersistableEntity;

/**
 * Hibernate annotated trade bean object that is serialized/deserialized to and
 * from the database.
 * 
 * In this example it is just a delegate to the @{link Trade}.
 * 
 */
@Entity
@Table(name = "TRADE")
public class PersistableTrade extends AbstractPersistableEntity {

    /**   */
    private static final long serialVersionUID = 443694950228200941L;

    private final Trade domain;

    public PersistableTrade() {
        domain = new Trade();
    }

    public PersistableTrade(Trade domain) {
        this.domain = domain;
    }

    @Transient
    public Trade getDomain() {
        return domain;
    }

    public String getInstrumentId() {
        return this.domain.getInstrumentId();
    }

    public void setInstrumentId(String instrumentId) {
        this.domain.setInstrumentId(instrumentId);
    }

    public double getValue() {
        return this.domain.getValue();
    }

    public void setValue(double value) {
        this.domain.setValue(value);
    }

    public double getQty() {
        return this.domain.getQty();
    }

    public void setQty(double qty) {
        this.domain.setQty(qty);
    }

    public String getVerb() {
        return this.domain.getVerb();
    }

    public void setVerb(String verb) {
        this.domain.setVerb(verb);
    }

    public String getCounterparty() {
        return this.domain.getCounterparty();
    }

    public void setCounterparty(String counterparty) {
        this.domain.setCounterparty(counterparty);
    }

    public String getBook() {
        return this.domain.getBook();
    }

    public void setBook(String book) {
        this.domain.setBook(book);
    }

    public String getContraBook() {
        return this.domain.getContraBook();
    }

    public void setContraBook(String contraBook) {
        this.domain.setContraBook(contraBook);
    }

    public String getStatus() {
        return domain.getStatus().name();
    }

    public void setStatus(String newStatus) {
        domain.setStatus(BookStatus.valueOf(newStatus));
    }
}

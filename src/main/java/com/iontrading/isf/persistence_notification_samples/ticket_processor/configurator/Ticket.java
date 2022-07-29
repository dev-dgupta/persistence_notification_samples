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

import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.RevisionIdentifier;

/**
 * Domain ticket entity. 
 * It implements the ticket from business point of view only.
 * 
 */
public class Ticket implements DomainEntity {

    private Ticket previous;

    private RevisionIdentifier id;
    private String instrumentId;
    private String counterparty;
    private String book;
    private String contrabook;

    private BookStatus status = BookStatus.Pending;
    
    public Ticket() {
    }

    public Ticket(Ticket previous) {
        this.previous = previous;

        this.instrumentId = previous.getInstrumentId();
        this.counterparty = previous.getCounterparty();
        this.book = previous.getBook();
        this.contrabook = previous.getContrabook();        
        this.status = previous.getStatus();
    }

    @Override
    public RevisionIdentifier getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(RevisionIdentifier newId) {
        id = newId;
    }

    @Override
    public RevisionIdentifier getPreviousIdentifier() {
        return previous == null ? null : previous.getIdentifier();
    }

    public String getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getCounterparty() {
        return this.counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getBook() {
        return this.book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getContrabook() {
        return this.contrabook;
    }

    public void setContrabook(String contrabook) {
        this.contrabook = contrabook;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

}

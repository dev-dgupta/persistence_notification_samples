/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator;

import com.iontrading.isf.committer.annotation.Link;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.RevisionIdentifier;

/**
 * Domain trade entity. 
 * It implements the trade from business point of view only and defines the relation with the Ticket
 * (see getParentTicket method).
 * 
 */
public class Trade implements DomainEntity {

    private Trade previous;

    // example of trade fields:
    private String instrumentId;
    private double value;
    private double qty;
    private String verb;

    private String counterparty;
    private String book;
    private String contraBook;
    private Ticket parentTicket;
    private RevisionIdentifier id;
    private BookStatus status = BookStatus.Pending;
    /*
     * Constructor for new entity
     */
    public Trade() {
        previous = null;
    }

    /*
     * Constructor for amended entity
     */
    public Trade(Trade previous) {
        this.previous = previous;
        this.parentTicket = previous.parentTicket;
        this.instrumentId = previous.getInstrumentId();
        this.value = previous.getValue();
        this.qty = previous.getQty();
        this.verb = previous.getVerb();
        this.counterparty = previous.getCounterparty();
        this.book = previous.getBook();
        this.contraBook = previous.getContraBook();
        this.status = previous.getStatus();
        
    }

    // Required by the persistence and notification service to amend the trade.
    @Override
    public RevisionIdentifier getPreviousIdentifier() {
        return (previous != null) ? previous.getIdentifier() : null;
    }

    //declare a relation of type STRONG
    
    @Link 
    public Ticket getParentTicket() {
        return parentTicket;
    }

    public void setParentTicket(Ticket parent) {
        parentTicket = parent;
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

    public Trade getPrevious() {
        return previous;
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

    public String getContraBook() {
        return this.contraBook;
    }

    public void setContraBook(String contraBook) {
        this.contraBook = contraBook;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

}

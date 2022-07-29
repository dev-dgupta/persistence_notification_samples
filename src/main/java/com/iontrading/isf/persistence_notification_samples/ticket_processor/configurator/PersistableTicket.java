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

import javax.persistence.Transient;


/**
 * MINIMUM ONE-LINE DESCRIPTION IS REQUIRED.
 *
 */
/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */


import javax.persistence.Entity;
import javax.persistence.Table;
import com.iontrading.isf.committer.spi.AbstractPersistableEntity;

/**
 * Hibernate annotated trade bean object that is serialized/deserialized to and from the database.
 * 
 * In this example it is just a delegate to the @{link Ticket}.
 * 
 */
@Entity
@Table(name = "TICKET")
public class PersistableTicket extends AbstractPersistableEntity {


        /**   */
    private static final long serialVersionUID = -8589884252014711488L;
        /**   */
        
        private Ticket domain;
        
        public PersistableTicket() {
            domain = new Ticket();
        }
            
        public PersistableTicket(Ticket domain) {
            this.domain = domain;
        }
        
        @Transient
        public Ticket getDomain() {
            return domain;
        }

        public String getInstrumentId() {
            return this.domain.getInstrumentId();
        }

        public void setInstrumentId(String instrumentId) {
            this.domain.setInstrumentId(instrumentId);
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

        public String getContrabook() {
            return this.domain.getContrabook();
        }

        public void setContrabook(String contrabook) {
            this.domain.setContrabook(contrabook);
        }
        
        public String getStatus() {
            return domain.getStatus().name();
        }

        public void setStatus(String newStatus) {
            domain.setStatus(BookStatus.valueOf(newStatus));
        }
    }

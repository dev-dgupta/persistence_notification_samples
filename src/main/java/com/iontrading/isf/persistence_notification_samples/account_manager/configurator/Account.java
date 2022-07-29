/*
 * Created: Oct 14, 2013
 * 
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.account_manager.configurator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iontrading.isf.committer.spi.AbstractPersistablePublishableEntity;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.RevisionIdentifier;
import com.iontrading.talk.api.annotation.TalkProperty;
import com.iontrading.talk.api.annotation.TalkType;

/**
 * Sample account bean.
 * 
 */
@Entity
@Table(name = "ACCOUNT")
@TalkType(name = "Account")
public class Account extends AbstractPersistablePublishableEntity implements DomainEntity {

    /**   */
    private static final long serialVersionUID = 443694950228200941L;

    private Account previous;
    private RevisionIdentifier id;
    private String name = "";
    private String shortName = "";
    private String description = "";

    public Account() {
    }

    public Account(Account previous) {
        this.previous = previous;
    }

    @Transient
    @Override
    public RevisionIdentifier getIdentifier() {
        return id;
    }

    @Override
    public void setIdentifier(RevisionIdentifier newId) {
        this.id = newId;
    }

    @Transient
    @Override
    public RevisionIdentifier getPreviousIdentifier() {
        return previous != null ? previous.getIdentifier() : null;
    }

    @TalkProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @TalkProperty
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @TalkProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

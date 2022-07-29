/*
 * Created: Nov 12, 2013
 * 
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor.configurator;

import com.iontrading.isf.committer.annotation.ExternalId;
import com.iontrading.isf.committer.annotation.ExternalIdField;

/**
 * Bean that represents the external id fields.
 * Once it has been registered in the configuration phase (see {@link ApplicationPNConfiguration}) This object will be
 * used to lookup the trade.
 * 
 */
@ExternalId
public class TradeExternalId {
    private final String extId;

    
    public TradeExternalId(String extId) {
        this.extId = extId;
    }

    @ExternalIdField("externalId")
    public String getExternalId() {
        return extId;
    }

}

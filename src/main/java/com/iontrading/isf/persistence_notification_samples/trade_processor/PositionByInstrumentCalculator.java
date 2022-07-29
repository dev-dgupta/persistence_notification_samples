/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.trade_processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.iontrading.isf.boot.spi.IService;
import com.iontrading.isf.committer.annotation.CommitterEventBus;
import com.iontrading.isf.committer.spi.CommittedEntityInfo;
import com.iontrading.isf.committer.spi.EntityCommitConfirmedEvent;
import com.iontrading.isf.persistence_notification_samples.trade_processor.configurator.DomainTrade;
import com.iontrading.talk.api.Publisher;

/**
 * Calculate the position by instrument by using the internal notification
 * mechanism.
 * 
 * It subscribes the committer event bus to receive the notifications about all
 * committed entities that are actually written in the database (i.e.
 * EntityCommitConfirmedEvent).
 * 
 */
public class PositionByInstrumentCalculator implements IService {

    private final Map<String, Position> positions = new HashMap<String, Position>();

    private final EventBus eventBus;
    private final Publisher publisher;

    @Inject
    public PositionByInstrumentCalculator(@CommitterEventBus EventBus eventBus, Publisher publisher) {
        this.eventBus = eventBus;
        this.publisher = publisher;
    }

    public void start() {
        eventBus.register(this);

        publisher.publishToList(Position.class, new ArrayList<Position>(), "POSITIONS");
    }

    public void shutdown() {
        eventBus.unregister(this);
    }

    public String getName() {
        return "Position Calculator";
    }

    /**
     * Event bus handler, called when P&N confirms that the entities are safely
     * committed to the database.
     */
    @Subscribe
    public void onCommitConfirmedEvent(EntityCommitConfirmedEvent ev) {
        // We receive an EntityCommitConfirmedEvent: some trades have been
        // persisted to the database
        for (CommittedEntityInfo committedEntity : ev.getEntities()) {

            // Potentially we may receive any kind of domain entity here. We
            // need to check the type
            // of the entity that is provided.
            if (committedEntity.getDomainEntity() instanceof DomainTrade) {
                DomainTrade trade = (DomainTrade) committedEntity.getDomainEntity();

                DomainTrade previousRevision = trade.getPrevious() == null ? null : trade.getPrevious();

                Position position = calculatePosition(trade.getInstrumentId(), trade, previousRevision);
                publisher.publishToList(position, "POSITIONS");
            }
        }
    }

    private Position calculatePosition(String instrumentId, DomainTrade trade, DomainTrade previousRevision) {
        Position previousPosition = positions.get(instrumentId);

        if (previousPosition == null) {
            previousPosition = new Position();
            previousPosition.setInstrumentId(instrumentId);
        } else {
            if (previousRevision != null) {
                updatePosition(previousPosition, -previousRevision.getQty(), previousRevision.getValue(),
                        previousRevision.getVerb());
            }
        }

        updatePosition(previousPosition, trade.getQty(), trade.getValue(), trade.getVerb());

        positions.put(instrumentId, previousPosition);
        return previousPosition;
    }

    private void updatePosition(Position position, Double qty, Double value, String verb) {
        qty = (verb.equals("S") ? -qty : qty);
        position.setPosition(position.getPosition() + qty * value);
    }
}

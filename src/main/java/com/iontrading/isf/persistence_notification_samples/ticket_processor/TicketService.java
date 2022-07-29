/*
 * Copyright (c) 2013 ION Trading ltd.
 * All Rights reserved.
 * 
 * This software is proprietary and confidential to ION Trading ltd.
 * and is protected by copyright law as an unpublished work.
 * Unauthorized access and disclosure strictly forbidden.
 */
package com.iontrading.isf.persistence_notification_samples.ticket_processor;

import java.util.Collection;

import javax.inject.Inject;

import com.iontrading.isf.committer.spi.CommitterListener;
import com.iontrading.isf.committer.spi.CommitterTransaction;
import com.iontrading.isf.committer.spi.CommitterTransactionFactory;
import com.iontrading.isf.committer.spi.DomainEntity;
import com.iontrading.isf.committer.spi.Fetcher;
import com.iontrading.isf.committer.spi.TransactionCommitStrategy;
import com.iontrading.isf.commons.async.AsyncResult;
import com.iontrading.isf.commons.async.AsyncResultPromise;
import com.iontrading.isf.commons.async.AsyncResults;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.BookStatus;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Ticket;
import com.iontrading.isf.persistence_notification_samples.ticket_processor.configurator.Trade;
import com.iontrading.talk.api.annotation.TalkFunction;
import com.iontrading.talk.api.annotation.TalkParam;
import com.iontrading.talk.api.exception.TalkFunctionException;

/**
 * Provides the ticket services.
 */
public class TicketService {

    private final CommitterTransactionFactory committer;
    private final Fetcher fetcher;

    @Inject
    public TicketService(CommitterTransactionFactory committer, Fetcher fetcher) {
        this.committer = committer;
        this.fetcher = fetcher;
    }

    /**
     * Provides the ticket process service.
     * <p>
     * When the bus function is invoked:
     * <li>create a new Ticket (with status Pending) then create the following
     * DomainTrades:
     * <ul>
     * <li>a "sales to customer" trade with status "Pending"</li>
     * <li>a "sales to trader" trade with status "Validated"</li>
     * <li>a "trader to sales" trade with status "Validated"</li>
     * </ul>
     * 
     * The function replies to the caller with the identifier assigned to the
     * Ticket
     */
    @TalkFunction
    public AsyncResult<String> processTicket(@TalkParam(name = "InstrumentId") String instrumentId,
            @TalkParam(name = "Qty") double qty, @TalkParam(name = "Value") double value,
            @TalkParam(name = "Verb") String verb, @TalkParam(name = "Customer") String customer,
            @TalkParam(name = "Sales") String sales, @TalkParam(name = "Trader") String trader) {

        final AsyncResultPromise<String> asyncResult = AsyncResults.create();

        final Ticket ticket = new Ticket();
        ticket.setInstrumentId(instrumentId);
        ticket.setCounterparty(customer);
        ticket.setBook(sales);
        ticket.setContrabook(trader);

        // creates the trades and link them to the parent ticket
        final Trade sales2Custom = createSales2CustomerTrade(ticket, qty, value, verb);
        final Trade sales2trader = createSales2TraderTrade(ticket, qty, value, verb);
        final Trade trader2sales = createTrader2SalesTrade(ticket, qty, value, verb);

        // Create a transaction
        CommitterTransaction transaction = committer.create();
        // adds the new trade to the CommitterTransaction
        transaction.addOrUpdate(sales2Custom);
        transaction.addOrUpdate(sales2trader);
        transaction.addOrUpdate(trader2sales);

        // We are now ready to commit the transaction
        transaction.commit(new CommitterListener() {

            @Override
            public void onPending(Collection<? extends DomainEntity> entities) {

            }

            @Override
            public void onCommit(Collection<? extends DomainEntity> entities) {
                // Reply to the function with the id assigned by the
                // persistence_notification engine to the ticket
                asyncResult.success(ticket.getIdentifier().getId());
            }

            @Override
            public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                // A problem in committing the transaction: report it to the
                // caller.
                asyncResult.failure(t);
            }

        });

        return asyncResult;
    }

    /**
     * Provides the ValidateTicket service.
     * <p>
     * When the bus function is invoked:
     * <ul>
     * <li>it fetches the previous image of the ticket through the
     * <code>Fetcher</code></li>
     * <li>it creates a new <code>Ticket</code> by providing the previous ticket
     * image and set the new status to "Validated"</li>
     * <li>it fetches all domain trades related to the ticket (always through
     * the Fetcher) and create a new revision "sales to customer" with status
     * "Validated"</li>
     * <li>it links the new Ticket to the new Trade and commits the change</li>
     * <li>it replies to the function with the new revision assigned to the
     * ticket by the persistence_notification engine</li>
     * </ul>
     * 
     * <h3>Usage</h3>
     * A bus function - {@code <SOURCE>_ValidateTicket} - is published, having
     * the following (mandatory) argument:
     * <ol>
     * <li> {@code TicketId}: id of the existing <i>ticket</i> in pending status.
     * </ol>
     */
    @TalkFunction
    public AsyncResult<String> validateTicket(@TalkParam(name = "TicketId") String id) {
        final AsyncResultPromise<String> asyncResult = AsyncResults.create();

        // fetch the previous image of the ticket
        final Ticket prevTicket = fetcher.findById(Ticket.class, id);

        if (prevTicket == null) {
            throw new TalkFunctionException("Ticket not found");
        }

        if (prevTicket.getStatus() != BookStatus.Pending) {
            throw new TalkFunctionException("Ticket is not pending");
        }

        // create a new revision of the ticket
        final Ticket newTicket = new Ticket(prevTicket);
        newTicket.setStatus(BookStatus.Validated);

        // get the trades pointing to the previous version of the ticket.
        Collection<Trade> trades = fetcher.fetchIncomingLinks(prevTicket, Trade.class, "");

        // extract the customer trade from those that belong to the ticket
        Trade prevCustomerTrade = retrieveCustomTrade(trades);

        // create the new image of the the trade
        final Trade newTrade = new Trade(prevCustomerTrade);
        newTrade.setStatus(BookStatus.Validated);

        // link the new trade to the new ticket
        newTrade.setParentTicket(newTicket);

        // Create a transaction
        CommitterTransaction transaction = committer.create();
        try {
            // it is enough to commit just the amended trade; the new ticket
            // will be persisted as well as it will be reached while traversing
            // the hierarchy. Anyway it is possible to commit the ticket also.
            // NOTE: while ticket is optional, trade is mandatory because it is
            // not possible to reach
            // the trade though the ticket
            transaction.addOrUpdate(newTrade);
        } catch (Exception e) {
            // A problem in managing the transaction: report it to the caller
            asyncResult.failure(e);
        }
        CommitterListener lner = new CommitterListener() {
            @Override
            public void onPending(Collection<? extends DomainEntity> entities) {
            }

            @Override
            public void onRollback(Collection<? extends DomainEntity> entities, Throwable t) {
                // A problem occurred while committing
                // Report it to the caller.
                asyncResult.failure(t);
            }

            @Override
            public void onCommit(Collection<? extends DomainEntity> entities) {
                // Reply to the function with the id and the new
                // revision assigned by the persistence_notification engine
                asyncResult.success(newTicket.getIdentifier().getId()
                        + newTicket.getIdentifier().getRevision().toString());
            }
        };
        transaction.commit(lner, TransactionCommitStrategy.SINGLE_TRANSACTION);

        return asyncResult;
    }

    private Trade createSales2CustomerTrade(Ticket ticket, double qty, double value, String verb) {
        Trade domainTrade = new Trade();
        domainTrade.setInstrumentId(ticket.getInstrumentId());
        domainTrade.setQty(qty);
        domainTrade.setValue(value);
        domainTrade.setVerb(verb);
        domainTrade.setCounterparty(ticket.getCounterparty());
        domainTrade.setBook(ticket.getBook());
        domainTrade.setContraBook("");
        domainTrade.setParentTicket(ticket);
        domainTrade.setStatus(BookStatus.Pending);
        return domainTrade;
    }

    private Trade createSales2TraderTrade(Ticket ticket, double qty, double value, String verb) {
        Trade domainTrade = new Trade();
        domainTrade.setInstrumentId(ticket.getInstrumentId());
        domainTrade.setQty(qty);
        domainTrade.setValue(value);
        domainTrade.setVerb((verb == "B") ? "S" : "B");
        domainTrade.setCounterparty("");
        domainTrade.setBook(ticket.getBook());
        domainTrade.setContraBook(ticket.getContrabook());
        domainTrade.setParentTicket(ticket);
        domainTrade.setStatus(BookStatus.Validated);
        return domainTrade;
    }

    private Trade createTrader2SalesTrade(Ticket ticket, double qty, double value, String verb) {
        Trade domainTrade = new Trade();
        domainTrade.setInstrumentId(ticket.getInstrumentId());
        domainTrade.setQty(qty);
        domainTrade.setValue(value);
        domainTrade.setVerb(verb);
        domainTrade.setCounterparty("");
        domainTrade.setBook(ticket.getContrabook());
        domainTrade.setContraBook(ticket.getBook());
        domainTrade.setParentTicket(ticket);
        domainTrade.setStatus(BookStatus.Validated);
        return domainTrade;
    }

    /**
     * Retrieve the customer trade that belong to the retrieved collection.
     */
    private Trade retrieveCustomTrade(Collection<Trade> trades) {
        for (Trade trade : trades) {
            if (trade.getCounterparty() != null && !trade.getCounterparty().isEmpty()) {
                return trade;
            }
        }
        return null;
    }

}

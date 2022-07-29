package com.iontrading.isf.persistence_notification_samples.cash_transaction_manager.configurator;

import com.iontrading.isf.committer.impl.link.PNLink;
import com.iontrading.isf.commons.async.ResultToken;
import com.iontrading.isf.mapping.spi.AbstractMapper;
import com.iontrading.isf.mapping.spi.MappingRequest;

/**
 * The mapper used to obtain a publishable entity (
 * {@link PublishableCashTransactionMovement}), used for the joined notification
 * of linked {@link CashTransaction} and {@link CashMovement} entity, from the
 * corresponding {@link PNLink} entity representing the P&N Link between the two
 * entities.
 * 
 */
public class PublishableCashTransactionMovementMapper
        extends AbstractMapper<PNLink, PublishableCashTransactionMovement> {

    public PublishableCashTransactionMovementMapper() {
        super(PNLink.class, PublishableCashTransactionMovement.class);
    }

    @Override
    public void map(MappingRequest<PNLink, PublishableCashTransactionMovement> request,
            ResultToken<PublishableCashTransactionMovement> resultToken) {

        PublishableCashTransactionMovement result = new PublishableCashTransactionMovement();

        PNLink linkInfo = request.getSource();

        /*
         * Check if the Link source is present: since the joined notification
         * stream is recoverable, it will contain also notifications
         * corresponding to unlinked CashTransaction or CashMovement entities
         */
        if (linkInfo.getSource().isPresent()) {
            PersistableCashTransaction source = linkInfo.<PersistableCashTransaction> getSource().get();
            result.setCashTransactionId(source.getTransId());
            result.setCashTransactionRev(source.getRevision());
        }

        /*
         * Check if the Link destination is present (see above)
         */
        if (linkInfo.getDestination().isPresent()) {
            PersistableCashMovement target = linkInfo.<PersistableCashMovement> getDestination().get();
            result.setCashMovementId(target.getMovId());
            result.setCashMovementRev(target.getRevision());
        }

        resultToken.success(result);
    }

}

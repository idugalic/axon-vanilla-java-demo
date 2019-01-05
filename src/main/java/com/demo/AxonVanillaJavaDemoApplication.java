package com.demo;

import com.demo.api.FindGiftCardQry;
import com.demo.api.GiftCardRecord;
import com.demo.api.IssueCmd;
import com.demo.command.GiftCard;
import com.demo.query.GiftCardEventHandler;
import com.demo.query.GiftCardQueryHandler;
import org.axonframework.config.Configuration;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;


public class AxonVanillaJavaDemoApplication {

    private final static Logger log = LoggerFactory.getLogger(AxonVanillaJavaDemoApplication.class);


    public static void main(String[] args) {

        /* Simple DB for the query side */
        DB querySideDB = DBMaker.memoryDB().make();
        ConcurrentMap querySideDBMap = querySideDB.hashMap("querySideDBMap").createOrOpen();

        /* Axon configuration */
        Configuration config = DefaultConfigurer.defaultConfiguration()
                .configureAggregate(GiftCard.class)
                .configureEmbeddedEventStore(c -> new InMemoryEventStorageEngine())
                .eventProcessing(ep -> ep.registerEventHandler(c -> new GiftCardEventHandler(c.queryUpdateEmitter(), querySideDBMap)))
                .registerQueryHandler(c -> new GiftCardQueryHandler(querySideDBMap))
                .start();

        /* Random aggregate id */
        String randomId = UUID.randomUUID().toString();

        /* Subscription query */
        SubscriptionQueryResult<GiftCardRecord, GiftCardRecord> queryResult = config.queryGateway().subscriptionQuery(new FindGiftCardQry(randomId), ResponseTypes.instanceOf(GiftCardRecord.class), ResponseTypes.instanceOf(GiftCardRecord.class));

        /* Sending the command */
        config.commandGateway().send(new IssueCmd(randomId, Integer.valueOf(1000)));

        /* Returning the first update sent to our subscription query. */
        GiftCardRecord giftCardRecordUpdate = queryResult.updates().blockFirst();
        log.info("First update sent to our `find card query`: " + giftCardRecordUpdate);

        /* Making an explicit query to the read model */
        GiftCardRecord giftCardRecord = config.queryGateway().query(new FindGiftCardQry(randomId), GiftCardRecord.class).getNow(new GiftCardRecord("0", Integer.MIN_VALUE, Integer.MIN_VALUE));
        log.info("Result from `find card query`: " + giftCardRecord);

        config.shutdown();
    }
}

package com.demo.query;

import com.demo.api.FindGiftCardQry;
import com.demo.api.GiftCardRecord;
import com.demo.api.IssuedEvt;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentMap;

public class GiftCardEventHandler {

    private final static Logger log = LoggerFactory.getLogger(GiftCardEventHandler.class);
    private final QueryUpdateEmitter queryUpdateEmitter;
    private final ConcurrentMap<String, GiftCardRecord> querySideMap;

    public GiftCardEventHandler(QueryUpdateEmitter queryUpdateEmitter, ConcurrentMap querySideMap) {
        this.queryUpdateEmitter = queryUpdateEmitter;
        this.querySideMap = querySideMap;
    }

    @EventHandler
    void on(IssuedEvt event) {
        log.info("handling {}", event);

        /* Save the record */
        GiftCardRecord record = new GiftCardRecord(event.getId(), event.getAmount(), event.getAmount());
        querySideMap.put(event.getId(), record);

        /* Send it to subscription queries of type FindGiftCardQry, but only if the gift card id matches. */
        queryUpdateEmitter.emit(FindGiftCardQry.class, findGiftCardQry -> Objects.equals(event.getId(), findGiftCardQry.getId()), record);
    }
}

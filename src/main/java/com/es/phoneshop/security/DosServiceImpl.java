package com.es.phoneshop.security;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosServiceImpl implements DefaultDosService {

    private static final long THRESHOLD = 10;
    private Map<String, Long> countMap = new ConcurrentHashMap<>();
    private static volatile DosServiceImpl INSTANCE;

    private volatile Date lastResetDate = new Date();
    private static final long HISTORY_LIFESPAN = 60_000;

    private DosServiceImpl() {
    }

    public static DosServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (DosServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DosServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public boolean isAllowed(String ip) {
        resetHistoryAfterTimeExpired();
        Long count = countMap.get(ip);
        if (count == null) {
            count = 1L;
        } else {
            count++;
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        countMap.put(ip, count);
        return true;
    }

    private void resetHistoryAfterTimeExpired() {
        Date date = new Date();
        if ((date.getTime() - lastResetDate.getTime()) > HISTORY_LIFESPAN) {
            lastResetDate = date;
            countMap.clear();
        }
    }

}

package com.es.phoneshop.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosServiceImpl implements DefaultDosService {

    private static final long THRESHOLD = 10;
    private Map<String, Long> countMap = new ConcurrentHashMap<>();
    private static volatile DosServiceImpl INSTANCE;

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
}

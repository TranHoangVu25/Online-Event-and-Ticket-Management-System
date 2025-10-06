package com.ticketsystem.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SpamProtectionService {

    private static final int MAX_REQUESTS = 3;
    private static final int COOLDOWN_MINUTES = 15;

    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();

    private final Map<String, LocalDateTime> blockedIPs = new ConcurrentHashMap<>();


    public boolean isBlocked(String ipAddress) {
        LocalDateTime blockedTimestamp = blockedIPs.get(ipAddress);
        if (blockedTimestamp != null) {
            if (LocalDateTime.now().isAfter(blockedTimestamp.plusMinutes(COOLDOWN_MINUTES))) {
                blockedIPs.remove(ipAddress);
                requestCounts.remove(ipAddress);
            } else {
                return true;
            }
        }

        int count = requestCounts.getOrDefault(ipAddress, 0);

        if (count >= MAX_REQUESTS) {
            blockedIPs.put(ipAddress, LocalDateTime.now());
            return true;
        }

        return false;
    }

    public void recordRequest(String ipAddress) {
        int count = requestCounts.getOrDefault(ipAddress, 0);
        requestCounts.put(ipAddress, count + 1);
    }
}
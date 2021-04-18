package com.harkka.livegreen.entry;

import java.time.LocalDateTime;
import java.util.UUID;

public class Entry {

    private LocalDateTime entryDateTime;
    private UUID userGuid;
    private UUID entryGuid;

    public Entry(UUID uGuid) {
        entryDateTime = LocalDateTime.now();
        userGuid = uGuid;
        entryGuid = getGuid();

        System.out.println("In Entry: " + entryDateTime + " " + userGuid + " " + entryGuid);
    }

    private UUID getGuid() {

        UUID guid = UUID.randomUUID();
        System.out.println("GetGuid()/Guid: " + guid);

        return guid;
    }
}

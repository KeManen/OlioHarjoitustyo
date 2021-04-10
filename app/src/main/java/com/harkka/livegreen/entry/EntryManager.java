package com.harkka.livegreen.entry;

public class EntryManager {

    public static EntryManager entryManager = new EntryManager(); // Singleton!!!

    public static EntryManager getInstance() {
        return entryManager;
    } // Singleton!!!

    private EntryManager() {}

    //Todo: Necessary methods for Main - to be decided
}

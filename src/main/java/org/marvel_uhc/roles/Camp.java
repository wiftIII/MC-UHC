package org.marvel_uhc.roles;

public enum Camp {
    AVENGERS("§9Avengers"),
    MUTANTS("§eMutants"),
    VILLAINS("§cSuper-Vilains"),
    ANTI_HEROES("§6Anti-Héros");

    private final String displayName;

    Camp(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
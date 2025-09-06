package library.player;

/**
 * A constrained class to allow the storage of player's sorting preferences, pertaining to the
 * sorting methods in {@link library.container.Hand}.
 */
public enum SortingPreference {
    NONE(0), BY_COLOUR(1), BY_VALUE(2);

    private final int value;

    SortingPreference(int value) {
        this.value = value;
    }

    /**
     * @return indexed value of the SortingPreference.
     */
    public int getValue() { return value; }

    /**
     * @param value numeric representation of the sorting preference.
     * @return {@code SortingPreference} the corresponding sorting preference.
     * Allows for numeric representation for easier selection during prompts.
     */
    public static SortingPreference fromValue(int value) {
        for (SortingPreference sortingPreference : SortingPreference.values()) {
            if (sortingPreference.getValue() == value) {
                return sortingPreference;
            }
        }
        return null;
    }
}

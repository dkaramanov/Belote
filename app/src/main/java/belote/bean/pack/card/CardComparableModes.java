package belote.bean.pack.card;

public final class CardComparableModes {
    private CardComparableModes() {
    }

    /**
     * CardCompare objects.
     */
    public static final CardComparableMode Standard = new CardComparableModeStandard();

    public static final CardComparableMode NotTrump = new CardComparableModeNotTrump();

    public static final CardComparableMode AllTrump = new CardComparableModeAllTrump();
}

package belote.bean.announce.suit;

public final class AnnounceSuits {

    /**
     * Pass announce constant.
     */
    public static final AnnounceSuit Pass = new Pass();

    /**
     * Club announce constant.
     */
    public static final AnnounceSuit Club = new Club();

    /**
     * Diamond announce constant.
     */
    public static final AnnounceSuit Diamond = new Diamond();

    /**
     * Heart announce constant.
     */
    public static final AnnounceSuit Heart = new Heart();

    /**
     * Spade announce constant.
     */
    public static final AnnounceSuit Spade = new Spade();

    /**
     * Not trump announce constant.
     */
    public static final AnnounceSuit NotTrump = new NotTrump();

    /**
     * All trump announce constant.
     */
    public static final AnnounceSuit AllTrump = new AllTrump();


    private AnnounceSuits() {
    }
}

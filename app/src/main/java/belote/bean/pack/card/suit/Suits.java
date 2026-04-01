package belote.bean.pack.card.suit;

import java.util.List;

public final class Suits {

    /**
     * Club suit constant.
     */
    public final static Suit Club = new Club();

    /**
     * Diamond suit constant.
     */
    public final static Suit Diamond = new Diamond();

    /**
     * Heart suit constant.
     */
    public final static Suit Heart = new Heart();

    /**
     * Spade suit constant.
     */
    public final static Suit Spade = new Spade();

    /**
     * Suit list used for iterations.
     */
    private final static List<Suit> suitList = List.of(Club, Diamond, Heart, Spade);

    private Suits() {
    }

    public static List<Suit> list() {
        return suitList;
    }

    /**
     * Returns suit's count (4).
     *
     * @return suit's count.
     */
    public static int getSuitCount() {
        return suitList.size();
    }

}

package belote.bean.pack.card.rank.comparator;

public final class RankComparators {

    /**
     * All trump comparator.
     */
    public final static RankComparator atComparator = new AllTrumpRankComparator();

    /**
     * Not trump comparator.
     */
    public final static RankComparator ntComparator = new NotTrumpRankComparator();

    /**
     * Standard comparator.
     */
    public final static RankComparator stComparator = new StandardRankComparator();

    private RankComparators() {
    }
}

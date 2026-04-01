package belote.bean.pack.card.rank;

import java.util.List;

import belote.bean.pack.card.rank.comparator.RankComparator;
import belote.bean.pack.card.rank.comparator.RankComparators;

public final class Ranks {

    /**
     * Seven ranks objects.
     */
    public static final Rank Seven = new Seven();

    /**
     * Eight ranks objects.
     */
    public static final Rank Eight = new Eight();

    /**
     * Nine ranks objects.
     */
    public static final Rank Nine = new Nine();

    /**
     * Ten ranks objects.
     */
    public static final Rank Ten = new Ten();

    /**
     * Jack ranks objects.
     */
    public static final Rank Jack = new Jack();

    /**
     * Queen ranks objects.
     */
    public static final Rank Queen = new Queen();

    /**
     * King ranks objects.
     */
    public static final Rank King = new King();

    /**
     * Ace ranks objects.
     */
    public static final Rank Ace = new Ace();

    /**
     * Ranks list (Used for iterating).
     */
    private final static List<Rank> ranklist = List.of(Seven, Eight, Nine, Jack, Queen, King, Ten, Ace);

    private Ranks() {
    }

    /**
     * Returns rank count (8).
     *
     * @return int rank count.
     */
    public static int getRankCount() {
        return ranklist.size();
    }

    /**
     * Returns next rank using provided RankComparator.
     *
     * @param rank           specified rank.
     * @param rankComparator used for finding rank after.
     * @return Rank next standard rank.
     */
    private static Rank getRankAfter(final Rank rank, final RankComparator rankComparator) {
        for (final Rank compRank : ranklist) {
            if (rankComparator.compare(compRank, rank) == 1) {
                return compRank;
            }
        }
        return rank;
    }

    /**
     * Returns previous rank using provided RankComparator.
     *
     * @param rank           specified rank.
     * @param rankComparator used for finding rank before.
     * @return Rank previous standard rank.
     */
    private static Rank getRankBefore(final Rank rank, final RankComparator rankComparator) {
        for (final Rank compRank : ranklist) {
            if (rankComparator.compare(compRank, rank) == -1) {
                return compRank;
            }
        }
        return rank;
    }

    /**
     * Returns next rank using standard rank order.
     *
     * @param rank specified rank.
     * @return Rank next standard rank.
     */
    public static Rank getSTRankAfter(final Rank rank) {
        return getRankAfter(rank, RankComparators.stComparator);
    }

    /**
     * Returns next rank using NT rank order.
     *
     * @param rank specified rank.
     * @return Rank next NT rank.
     */
    public static Rank getNTRankAfter(final Rank rank) {
        return getRankAfter(rank, RankComparators.ntComparator);
    }

    /**
     * Returns next rank using AT rank order.
     *
     * @param rank specified rank.
     * @return Rank next AT rank.
     */
    public static Rank getATRankAfter(final Rank rank) {
        return getRankAfter(rank, RankComparators.atComparator);
    }

    /**
     * Returns previous rank using standard rank order.
     *
     * @param rank specified rank.
     * @return Rank previous standard rank.
     */
    public static Rank getSTRankBefore(final Rank rank) {
        return getRankBefore(rank, RankComparators.stComparator);
    }

    /**
     * Returns previous rank using NT rank order.
     *
     * @param rank specified rank.
     * @return Rank previous NT rank.
     */
    public static Rank getNTRankBefore(final Rank rank) {
        return getRankBefore(rank, RankComparators.ntComparator);
    }

    /**
     * Returns previous rank using AT rank order.
     *
     * @param rank specified rank.
     * @return Rank previous AT rank.
     */
    public static Rank getATRankBefore(final Rank rank) {
        return getRankBefore(rank, RankComparators.atComparator);
    }

    public static List<Rank> list() {
        return ranklist;
    }

}

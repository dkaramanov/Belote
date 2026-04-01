package belote.logic.announce.factory.automat.methods.suitDeterminants.base;

import java.util.ArrayList;

import belote.bean.pack.card.rank.Rank;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.Suits;
import belote.bean.player.Player;

public abstract class RankSuitDeterminant implements SuitDeterminant {

    private final ArrayList<Rank> ranks = new ArrayList<>();

    @Override
    public final Suit determineSuit(Player player) {
        for (Suit suit : Suits.list()) {
            if (containRanks(player, suit)) {
                return suit;
            }
        }
        return null;
    }

    public final void addRank(final Rank rank) {
        ranks.add(rank);
    }

    private boolean containRanks(Player player, Suit suit) {
        for (final Rank rank : ranks) {
            if (player.getCards().findCard(rank, suit) == null) {
                return false;
            }
        }
        return true;
    }
}

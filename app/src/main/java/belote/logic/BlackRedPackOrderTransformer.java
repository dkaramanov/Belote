package belote.logic;

import belote.bean.pack.Pack;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.Suits;

public final class BlackRedPackOrderTransformer {

    private final Pack pack;

    public BlackRedPackOrderTransformer(Pack pack) {
        this.pack = pack;
    }

    public Pack transform() {
        final Pack result = new Pack();
        final Suit[] order = getSuitsOrder();

        for (final Suit suit : order) {
            for (final Card card : pack.list()) {
                if (card.getSuit().equals(suit)) {
                    result.add(card);
                }
            }
        }

        return result;
    }

    private Suit[] getSuitsOrder() {
        final int blackSuits = (pack.hasSuitCard(Suits.Spade) ? 1 : 0) + (pack.hasSuitCard(Suits.Club) ? 1 : 0);
        final int redSuits = (pack.hasSuitCard(Suits.Heart) ? 1 : 0) + (pack.hasSuitCard(Suits.Diamond) ? 1 : 0);
        if (blackSuits > redSuits) {
            return new Suit[]{Suits.Spade, Suits.Heart, Suits.Diamond, Suits.Club};
        } else if (blackSuits < redSuits) {
            return new Suit[]{Suits.Heart, Suits.Spade, Suits.Club, Suits.Diamond};
        } else {
            return new Suit[]{Suits.Spade, Suits.Heart, Suits.Club, Suits.Diamond};
        }
    }
}

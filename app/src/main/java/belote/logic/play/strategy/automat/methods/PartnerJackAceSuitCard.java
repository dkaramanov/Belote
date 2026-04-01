package belote.logic.play.strategy.automat.methods;

import belote.bean.Game;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.bean.player.Player;
import belote.logic.play.strategy.automat.base.method.BaseMethod;

public class PartnerJackAceSuitCard extends BaseMethod {

    /**
     * Constructor.
     *
     * @param game BelotGame instance class.
     */
    public PartnerJackAceSuitCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     *
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    protected Card getPlayMethodCard(final Player player) {
        final Player partner = player.getPartner();
        // Prefer suits
        for (final Suit suit : partner.getJackAceSuits()) {
            final Card card = player.getCards().findMinSuitCard(suit);
            if (card != null) {
                return card;
            }
        }
        return null;
    }
}
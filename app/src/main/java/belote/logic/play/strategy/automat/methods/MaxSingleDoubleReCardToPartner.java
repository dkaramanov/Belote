package belote.logic.play.strategy.automat.methods;

import belote.bean.Game;
import belote.bean.Player;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.logic.play.strategy.automat.base.method.BaseMethod;

/**
 * SingleCardToPartner class. PlayCardMethod which implements the logic of playing single card to partner if he is the best card player and have played the
 * maximum left suit card.
 * @author Dimitar Karamanov
 */
public class MaxSingleDoubleReCardToPartner extends BaseMethod {

    /**
     * Constructor.
     * @param game BelotGame instance class.
     */
    public MaxSingleDoubleReCardToPartner(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player) {
        Suit trump = getTrump();
        final Card handAttackSuitCard = game.getTrickCards().getHandAttackSuitCard(trump);
        if (handAttackSuitCard != null) {
            final Player partner = player.getPartner();
            final Player handPlayer = game.getPlayerByCard(handAttackSuitCard);
            if (handPlayer != null) {
                if (handPlayer.equals(partner) && isMaxSuitCardLeft(handAttackSuitCard, false) && (game.getAnnounceList().getRedoubleAnnounce() != null || game.getAnnounceList().getDoubleAnnounce() != null)) {
                    return player.getCards().findMaxAllCard();
                }
            }
        }
        return null;
    }
}
/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.methods.trumpsLess.allTrump;

import belote.bean.Game;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Ranks;
import belote.bean.pack.card.suit.Suit;
import belote.bean.pack.card.suit.Suits;
import belote.bean.player.Player;
import belote.logic.play.strategy.automat.base.method.BaseMethod;

/**
 * AllTrumpMakePowerNineCard class. PlayCardMethod which implements the logic of playing a card from suit that the player has Rank.Nine card. The aim is to make
 * it power one by eliminating the Rank.Jack card.
 *
 * @author Dimitar Karamanov
 */
public final class PromoteNineRankCard extends BaseMethod {

    /**
     * Constructor.
     *
     * @param game BelotGame instance class.
     */
    public PromoteNineRankCard(final Game game) {
        super(game);
    }

    /**
     * Returns player's card.
     *
     * @param player who is on turn.
     * @return Card object instance or null.
     */
    public Card getPlayMethodCard(final Player player) {
        for (final Suit suit : Suits.list()) {
            final int count = player.getCards().getSuitCount(suit);
            final Card jack = player.getCards().findCard(Ranks.Jack, suit);
            final Card nine = player.getCards().findCard(Ranks.Nine, suit);

            if (jack == null && nine != null && count > SINGLE_CARD_COUNT && !isMaxSuitCardLeft(nine, false)) {
                Player thirdDefencePlayer = getThirdDefencePlayer();
                if (!isPlayerSuitAnnounce(thirdDefencePlayer, suit)) {
                    return player.getCards().findMinSuitCard(suit);
                }
            }
        }
        return null;
    }
}
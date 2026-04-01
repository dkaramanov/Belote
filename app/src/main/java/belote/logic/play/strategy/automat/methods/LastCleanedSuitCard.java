/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.play.strategy.automat.methods;

import belote.bean.Game;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.bean.player.Player;
import belote.logic.play.strategy.automat.base.method.BaseMethod;

/**
 * LastCleanedSuitCard class. PlayCardMethod which implements the logic of playing a card from the last cleaned suit. (Called usually after trying to play from
 * not cleared suit).
 *
 * @author Dimitar Karamanov
 */
public final class LastCleanedSuitCard extends BaseMethod {

    /**
     * Constructor.
     *
     * @param game BelotGame instance class.
     */
    public LastCleanedSuitCard(final Game game) {
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

        int count = 0;
        Suit lastSuit = null;
        for (Suit suit : partner.getUnwantedSuits()) {
            lastSuit = suit;
            count++;
        }

        if (lastSuit != null && count > TWO_CARDS_COUNT && !partner.getMissedSuits().contains(lastSuit)) {
            final Suit trump = getTrump();
            if (!lastSuit.equals(trump)) {
                final Card card = player.getCards().findMinSuitCard(lastSuit);
                if (card != null) {
                    return card;
                }
            }
        }

        return null;
    }
}
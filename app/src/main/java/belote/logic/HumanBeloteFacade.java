/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic;

import belote.bean.pack.Pack;
import belote.bean.pack.card.Card;
import belote.bean.player.Player;
import belote.bean.player.Players;

/**
 * HumanBelotGame class.
 *
 * @author Dimitar Karamanov
 */
public final class HumanBeloteFacade extends BeloteFacade {

    private boolean blackRedCardOrder = false;

    private boolean playerIsAnnouncing = false;

    /**
     * Human player array index.
     */
    public static final Player HUMAN_PLAYER = Players.SOUTH;

    /**
     * Constructor
     */
    public HumanBeloteFacade() {
        super();
    }

    public final void setBlackRedCardOrder(boolean blackRedCardOrder) {
        this.blackRedCardOrder = blackRedCardOrder;
    }

    /**
     * Returns the human player.
     *
     * @return Player human player.
     */
    public Player getHumanPlayer() {
        getGameLock().readLock().lock();
        try {
            return HUMAN_PLAYER;
        } finally {
            getGameLock().readLock().unlock();
        }
    }

    /**
     * Returns the position of the first right card.
     *
     * @return int the position of the first right card.
     */
    private int getFirstRightCardIndex() {
        getGameLock().readLock().lock();
        try {
            if (getHumanPlayer().getCards().getSize() > 0) {
                return getHumanPlayer().getCards().getSize() - 1;
            }
        } finally {
            getGameLock().readLock().unlock();
        }
        return -1;
    }

    /**
     * Returns the position of the first left card.
     *
     * @return int the position of the first left card.
     */
    private int getFirstLeftCardIndex() {
        getGameLock().readLock().lock();
        try {
            if (getHumanPlayer().getCards().getSize() > 0) {
                return 0;
            }
        } finally {
            getGameLock().readLock().unlock();
        }
        return -1;
    }

    /**
     * Returns human selected card index.
     *
     * @return human selected card index.
     */
    private int getHumanSelectedCardIndex() {
        getGameLock().readLock().lock();
        try {
            if (getHumanPlayer().getSelectedCard() != null) {
                for (int i = 0; i < getHumanPlayer().getCards().getSize(); i++) {
                    final Card card = getHumanPlayer().getCards().getCard(i);

                    if (getHumanPlayer().getSelectedCard().equals(card)) {
                        return i;
                    }
                }
            }
        } finally {
            getGameLock().readLock().unlock();
        }
        return -1;
    }

    /**
     * Returns next left card index.
     *
     * @param current card index.
     * @return next left card index.
     */
    private int getNextLeftCardIndex(int current) {
        if (current < 1) {
            return getFirstRightCardIndex();
        } else {
            return current - 1;
        }
    }

    /**
     * Selects next left card.
     *
     * @return the selected card.
     */
    public Card selectNextLeftCard() {
        int index = getHumanSelectedCardIndex();
        Card card;
        do {
            index = getNextLeftCardIndex(index);
            if (index == -1 || index >= getHumanPlayer().getCards().getSize()) {
                return null;
            }
            card = getHumanPlayer().getCards().getCard(index);
        } while (!validatePlayerCard(getHumanPlayer(), card));

        return card;
    }

    /**
     * Returns next right card index.
     *
     * @param current card index.
     * @return next right card index.
     */
    private int getNextRightCardIndex(int current) {
        if (current < 0) {
            return getFirstLeftCardIndex();
        } else {
            if (current == getHumanPlayer().getCards().getSize() - 1) {
                return getFirstLeftCardIndex();
            }
            return current + 1;
        }
    }

    /**
     * Selects next right card.
     *
     * @return the selected card.
     */
    public Card selectNextRightCard() {
        int index = getHumanSelectedCardIndex();
        Card card;
        do {
            index = getNextRightCardIndex(index);
            getGameLock().readLock().lock();
            try {
                if (index == -1 || index >= getHumanPlayer().getCards().getSize()) {
                    return null;
                }
                card = getHumanPlayer().getCards().getCard(index);
            } finally {
                getGameLock().readLock().unlock();
            }
        } while (!validatePlayerCard(getHumanPlayer(), card));

        return card;
    }

    /**
     * Returns if human player's trick card or null if hasn't played yet.
     *
     * @return Card instance or null.
     */
    public Card getHumanTrickCard() {
        return getPlayerTrickCard(getHumanPlayer());
    }

    /**
     * Returns if the human player is on trick turn or not.
     *
     * @return boolean true or false.
     */
    public boolean isHumanTrickOrder() {
        return isPlayerTrickOrder(getHumanPlayer());
    }

    /**
     * Arranges players cards.
     */
    public void arrangePlayersCards() {
        super.arrangePlayersCards();
        getGameLock().writeLock().lock();
        try {
            if (blackRedCardOrder) {
                Player player = getHumanPlayer();
                Pack pack = player.getCards();
                BlackRedPackOrderTransformer transformer = new BlackRedPackOrderTransformer(pack);
                Pack blackRedPack = transformer.transform();
                pack.clear();
                pack.addAll(blackRedPack);
            }
        } finally {
            getGameLock().writeLock().unlock();
        }
    }

    public void setPlayerIsAnnouncing(boolean mode) {
        getGameLock().writeLock().lock();
        try {
            playerIsAnnouncing = mode;
        } finally {
            getGameLock().writeLock().unlock();
        }
    }

    public boolean isPlayerIsAnnoincing() {
        return playerIsAnnouncing;
    }

    public Card getHumanSelectedCard() {
        getGameLock().readLock().lock();
        try {
            return getHumanPlayer().getSelectedCard();
        } finally {
            getGameLock().readLock().unlock();
        }
    }

    public void setHumanPlayerCard(Card card) {
        getGameLock().writeLock().lock();
        try {
            getHumanPlayer().setSelectedCard(card);
        } finally {
            getGameLock().writeLock().unlock();
        }
    }
}

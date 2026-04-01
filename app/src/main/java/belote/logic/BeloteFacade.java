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

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import belote.base.BelotException;
import belote.bean.Game;
import belote.bean.GameMode;
import belote.bean.Team;
import belote.bean.announce.Announce;
import belote.bean.announce.AnnounceUnit;
import belote.bean.announce.suit.AnnounceSuits;
import belote.bean.pack.TrickPack;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Ranks;
import belote.bean.pack.card.suit.Suit;
import belote.bean.player.Player;
import belote.bean.player.Players;
import belote.bean.trick.Trick;
import belote.logic.announce.AnnounceGameLogic;
import belote.logic.play.PlayGameLogic;

/**
 * BeloteGame class. Facade and proxy class which is the base point for the
 * logic game.
 *
 * @author Dimitar Karamanov
 */
public class BeloteFacade {

    /**
     * Announce card number constant.
     */
    public final static int ANNOUNCE_CARD_COUNT = 5;

    /**
     * Announce factory.
     */
    private AnnounceGameLogic announceFactory;

    /**
     * Play card strategy facade.
     */
    private PlayGameLogic playGameLogic;

    /**
     * Game bean object.
     */
    protected Game game;

    private final ReentrantReadWriteLock gameLock = new ReentrantReadWriteLock();

    /**
     * Constructor the only one.
     *
     */
    public BeloteFacade() {
        this(new Game());
    }

    /**
     * Constructor the only one.
     *
     */
    public BeloteFacade(Game game) {
        setGame(game);
    }

    public Announce getContractAnnounce() {
        gameLock.readLock().lock();
        try {
            return game.getAnnounceList().getContractAnnounce();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public final void setGame(@NonNull Game game) {
        gameLock.writeLock().lock();
        try {
            this.game = game;
            playGameLogic = new PlayGameLogic(game, gameLock);
            announceFactory = new AnnounceGameLogic(game, gameLock);
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    public final Game getGame() {
        return game;
    }

    public final ReentrantReadWriteLock getGameLock() {
        return this.gameLock;
    }

    /**
     * Clears teams data.
     */
    private void clearTeamsData() {
        for (final Team team : game.teams()) {
            team.clearData();
        }
    }

    /**
     * Clears teams Belote game data.
     */
    private void clearTeamsBeloteGameData() {
        for (final Team team : game.teams()) {
            team.clearBeloteGameData();
        }
    }

    /**
     * Process players extra announces.
     */
    private void processExtraAnnounces() {
        for (final Player player : game.players()) {
            player.getCards().processExtraAnnounces();
        }
    }

    /**
     * Arranges players cards.
     */
    public void arrangePlayersCards() {
        gameLock.writeLock().lock();
        try {
            Announce announce = game.getAnnounceList().getContractAnnounce();
            for (final Player player : game.players()) {
                arrangePlayerCards(player, announce);
            }
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Arranges player' cards.
     */
    private void arrangePlayerCards(final Player player, final Announce announce) {
        if (announce == null) {
            player.getCards().arrange();
        } else if (announce.getAnnounceSuit().equals(AnnounceSuits.AllTrump)) {
            player.getCards().arrangeAT();
        } else if (announce.getAnnounceSuit().equals(AnnounceSuits.NotTrump)) {
            player.getCards().arrangeNT();
        } else {
            final Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
            player.getCards().arrangeCL(suit);
        }
    }

    /**
     * Clears players data.
     */
    private void clearPlayersData() {
        for (final Player player : game.players()) {
            player.clearData();
        }
    }

    /**
     * Adds players cards.
     */
    private void dealAnnounceCards() {
        game.initPack();
        game.addPlayersCards(ANNOUNCE_CARD_COUNT);
    }

    /**
     * Adds 3 more cards to the players cards.
     */
    private void dealRestCards() {
        final int restCount = game.getPackSize() / game.getPlayersCount();
        game.addPlayersCards(restCount);
    }

    /**
     * Checks for new big game.
     */
    private void checkBeloteGameEnd() {
        final Team team = game.getWinnerTeam();
        if (team != null) {
            clearTeamsBeloteGameData();
            team.increaseWinBelotGames();
            game.clearHangedPoints();
        }
    }

    /**
     * Begin new game (initialization).
     */
    public final void newGame() {
        gameLock.writeLock().lock();
        try {
            checkBeloteGameEnd();
            game.setGameMode(GameMode.AnnounceGameMode);
            game.getAnnounceList().clear();
            game.getTrickList().clear();
            clearTeamsData();
            clearPlayersData();
            dealAnnounceCards();
            arrangePlayersCards();
            processExtraAnnounces();
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Sets next deal attack player.
     */
    public final void setNextDealAttackPlayer() {
        gameLock.writeLock().lock();
        try {
            final Player nextGameAttackPlayer = getPlayerAfter(game.getDealAttackPlayer());
            game.setDealAttackPlayer(nextGameAttackPlayer);
            game.setTrickAttackPlayer(nextGameAttackPlayer);
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Returns true if is announce game mode false otherwise.
     *
     * @return boolean true if is announce game mode false otherwise.
     */
    public final boolean isAnnounceGameMode() {
        gameLock.readLock().lock();
        try {
            return game.getGameMode().equals(GameMode.AnnounceGameMode);
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Returns true if is playing game mode false otherwise.
     *
     * @return boolean true if is playing game mode false otherwise.
     */
    public final boolean isPlayingGameMode() {
        gameLock.readLock().lock();
        try {
            return game.getGameMode().equals(GameMode.PlayGameMode);
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Returns next player (iterates in cycle).
     *
     * @param player current player.
     * @return Player next player.
     */
    public final Player getPlayerAfter(final Player player) {
        gameLock.readLock().lock();
        try {
            return Players.getPlayerAfter(player);
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Checks for announce end.
     *
     * @return boolean true if is announce end false otherwise.
     */
    public final boolean canDeal() {
        gameLock.readLock().lock();
        try {
            if (game.getGameMode().equals(GameMode.AnnounceGameMode)) {
                return game.getAnnounceList().canDeal();
            }
            return false;
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Process next announce.
     */
    public final void processNextAnnounce() {
        announceFactory.processNextAnnounce();
    }

    /**
     * Returns next orderer player.
     *
     * @return Player the next announce player which have to order.
     */
    public final Player getNextAnnouncePlayer() {
        gameLock.readLock().lock();
        try {
            return announceFactory.getAnnounceOrderPlayer();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Deals and arranges rest cards.
     */
    public final void manageRestCards() {
        gameLock.writeLock().lock();
        try {
            dealRestCards();
            processExtraAnnounces();
            arrangePlayersCards();
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Returns if the provided player has couple.
     *
     * @param player provided player.
     * @param card   provided card.
     * @return boolean true if has a couple false otherwise.
     */
    public final boolean hasPlayerCouple(Player player, Card card) {
        return playGameLogic.hasPlayerCouple(player, card);
    }

    /**
     * Sets player's couple for the provided suit.
     *
     * @param player provided player.
     * @param suit   provided suit.
     */
    public final void setPlayerCouple(Player player, Suit suit) {
        gameLock.writeLock().lock();
        try {
            player.getTeam().getCouples().setCouple(suit);
            game.setTrickCouplePlayer(player);
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Returns played card for the provided player.
     *
     * @param player provided player.
     * @return Card played card for the provided player.
     */
    public final Card playSingleHand(Player player) throws BelotException {
        final Card card = playGameLogic.getPlayerCard(player);
        if (card != null) {
            removePlayerCard(player, card);
        }
        return card;
    }

    private void removePlayerCard(Player player, Card card) {
        gameLock.writeLock().lock();
        try {
            final Card attackCard = game.getTrickCards().getAttackCard();

            player.getCards().remove(card);
            game.getTrickCards().add(card);

            if (hasPlayerCouple(player, card)) {
                setPlayerCouple(player, card.getSuit());
            }

            if (attackCard == null && game.getTrickList().getAttackCount(player) < 3) {
                player.getPreferredSuits().add(card.getSuit());
            }

            if (attackCard != null && !attackCard.getSuit().equals(card.getSuit())) {
                player.getUnwantedSuits().add(card.getSuit());
                player.getMissedSuits().add(attackCard.getSuit());
            }
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Returns true if is end hand and clears hand.
     *
     * @return boolean true if is end hand and clears hand.
     */
    public final boolean isTrickEnd() {
        gameLock.readLock().lock();
        try {
            return game.getTrickCards().getSize() == game.getPlayersCount();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Returns null or next attack player.
     *
     * @return null or Player instance.
     */
    public final Player getNextTrickAttackPlayer() {
        if (isTrickEnd()) {
            return playGameLogic.getNextTrickAttackPlayer();
        }
        return null;
    }

    /**
     * Process trick data.
     */
    public final void processTrickData() {
        if (isTrickEnd()) {
            gameLock.writeLock().lock();
            try {
                final Player attackPlayer = game.getTrickAttackPlayer();
                game.setTrickAttackPlayer(playGameLogic.getNextTrickAttackPlayer());

                final Trick trick = new Trick(attackPlayer, game.getTrickAttackPlayer(), game.getTrickCouplePlayer(),
                        game.getTrickCards());
                game.getTrickList().add(trick);

                if (game.getTrickAttackPlayer() != null) {
                    game.getTrickAttackPlayer().getTeam().getHands().addAll(game.getTrickCards());
                }

                game.setTrickCouplePlayer(null);
                game.getTrickCards().clear();

                for (final Player player : game.players()) {
                    player.setSelectedCard(null);
                }
            } finally {
                gameLock.writeLock().unlock();
            }
        }
    }

    /**
     * Returns true if the game ended false otherwise.
     *
     * @return boolean true if the game ended false otherwise.
     */
    public final boolean checkGameEnd() {
        gameLock.readLock().lock();
        try {
            for (final Player player : game.players()) {
                if (player.getCards().getSize() != 0) {
                    return false;
                }
            }

            game.setGameMode(GameMode.InfoGameMode);
            return true;
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Calculates team's points.
     */
    public final void calculateTeamsPoints() {
        playGameLogic.calculateTeamsPoints();
        playGameLogic.distributeTeamsPoints();
    }

    /**
     * Validates player card.
     *
     * @param player provided player.
     * @param card   provided card.
     * @return boolean true if the card is valid, false otherwise.
     */
    public final boolean validatePlayerCard(Player player, Card card) {
        if (player != null && card != null) {
            return playGameLogic.validatePlayerCard(player, card);
        }
        return false;
    }

    /**
     * Process human played card. (Checks for couple, preferred, unwanted and
     * missed suits information)
     *
     * @param player human player.
     * @param card   played card.
     */
    public final void processHumanPlayerCard(Player player, Card card) {
        gameLock.writeLock().lock();
        try {
            removePlayerCard(player, card);

            Announce announce = game.getAnnounceList().getContractAnnounce();
            if (announce != null && announce.getAnnounceSuit().equals(AnnounceSuits.AllTrump)
                    && Ranks.Jack.equals(card.getRank())) {
                player.getJackAceSuits().add(card.getSuit());
            }

            if (announce != null && announce.getAnnounceSuit().equals(AnnounceSuits.NotTrump)
                    && Ranks.Ace.equals(card.getRank())) {
                player.getJackAceSuits().add(card.getSuit());
            }
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Set a new game mode.
     *
     * @param gameMode new value.
     */
    public final void setGameMode(final GameMode gameMode) {
        gameLock.readLock().lock();
        try {
            game.setGameMode(gameMode);
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Returns player's trick card or null if hasn't played yet.
     *
     * @param player which trick card is looking for.
     * @return Card instance or null.
     */
    public final Card getPlayerTrickCard(final Player player) {
        gameLock.readLock().lock();
        try {
            TrickPack trickCards = game.getTrickCards();
            final Iterator<Card> iterator = trickCards.list().iterator();
            final Player startPlayer = game.getTrickAttackPlayer();
            Player currentPlayer = startPlayer;

            do {
                Card card = iterator.hasNext() ? iterator.next() : null;
                if (currentPlayer.equals(player)) {
                    return card;
                }
                currentPlayer = getPlayerAfter(currentPlayer);
            } while (!startPlayer.equals(currentPlayer));

            return null;
        } finally {
            gameLock.readLock().unlock();
        }
    }

    /**
     * Returns if the provided player is on trick turn or not.
     *
     * @param player which trick turn is checked
     * @return boolean true or false.
     */
    public final boolean isPlayerTrickOrder(Player player) {
        gameLock.readLock().lock();
        try {
            TrickPack trickCards = game.getTrickCards();
            final Iterator<Card> iterator = trickCards.list().iterator();
            Card card = null;

            final Player startPlayer = game.getTrickAttackPlayer();
            Player currentPlayer = game.getTrickAttackPlayer();

            do {
                card = iterator.hasNext() ? iterator.next() : null;
                if (currentPlayer.equals(player)) {
                    return card == null;
                } else if (card == null) {
                    return false;
                }
                currentPlayer = getPlayerAfter(currentPlayer);
            } while (!startPlayer.equals(currentPlayer));

            return false;
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public Player getTrickAttackPlayer() {
        gameLock.readLock().lock();
        try {
            return game.getTrickAttackPlayer();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public Player getTrickCouplePlayer() {
        gameLock.readLock().lock();
        try {
            return game.getTrickCouplePlayer();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public Announce getOpenContractAnnounce() {
        gameLock.readLock().lock();
        try {
            return game.getAnnounceList().getOpenContractAnnounce();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public boolean isTrickListEmpty() {
        gameLock.readLock().lock();
        try {
            return game.getTrickList().isEmpty();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public void writeGame(ObjectOutputStream oos) throws IOException {
        gameLock.writeLock().lock();
        try {
            oos.writeObject(game);
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    public boolean hasDealtAnnounce() {
        gameLock.readLock().lock();
        try {
            return game.getAnnounceList().getCount() > 0;
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public void reset() {
        gameLock.writeLock().lock();
        try {
            game.getAnnounceList().clear();
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    public int getPlayersCount() {
        gameLock.readLock().lock();
        try {
            return game.getPlayersCount();
        } finally {
            gameLock.readLock().unlock();
        }
    }

    public int getAnnonceCount() {
        gameLock.readLock().lock();
        try {
            return game.getAnnounceList().getCount();
        } finally {
            gameLock.readLock().unlock();
        }
    }
}

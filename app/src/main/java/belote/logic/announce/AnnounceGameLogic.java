/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import belote.bean.Game;
import belote.bean.announce.Announce;
import belote.bean.player.Player;
import belote.bean.player.Players;
import belote.logic.announce.factory.automat.executors.BeloteGameAnnounceFactory;
import belote.logic.announce.factory.automat.executors.base.AnnounceExecutor;

/**
 * AnnounceFactoryFacade class. Facade for methods related with game announce.
 *
 * @author Dimitar Karamanov
 */
public final class AnnounceGameLogic {

    /**
     * Belote game instance.
     */
    private final Game game;

    /**
     * Announce factory (executor).
     */
    private final AnnounceExecutor announceFactory;

    private final ReentrantReadWriteLock gameLock;

    /**
     * Constructor.
     *
     * @param game Instance of BelotGame for which the AnnounceFactory will create announce.
     */
    public AnnounceGameLogic(final Game game, final ReentrantReadWriteLock gameLock) {
        this.game = game;
        this.gameLock = gameLock;
        announceFactory = new BeloteGameAnnounceFactory(game, gameLock);
    }

    /**
     * Add one more announce to the announce list.
     */
    public void processNextAnnounce() {
        gameLock.writeLock().lock();
        try {
            final Player player = getAnnounceOrderPlayer();
            final Announce announce = announceFactory.getAnnounce(player);
            game.getAnnounceList().add(announce);
        } finally {
            gameLock.writeLock().unlock();
        }
    }

    /**
     * Returns next orderer player.
     *
     * @return Player the next announce player which have to order.
     */
    public Player getAnnounceOrderPlayer() {
        gameLock.readLock().lock();
        try {
            final int announceCount = game.getAnnounceList().getCount();
            if (announceCount == 0) {
                return game.getDealAttackPlayer();
            } else {
                final Announce announce = game.getAnnounceList().getAnnounce(announceCount - 1);
                return Players.getPlayerAfter(announce.getPlayer());
            }
        } finally {
            gameLock.readLock().unlock();
        }
    }
}

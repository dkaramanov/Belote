/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.executors;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import belote.bean.Game;
import belote.logic.announce.factory.automat.executors.base.AnnounceExecutor;
import belote.logic.announce.factory.automat.methods.EndGameOpenEnemyTeamEndGameZoneAnnounce;
import belote.logic.announce.factory.automat.methods.EndGameOpenPlayerTeamEndGameZoneAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.HasContractAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.base.NotCondition;

/**
 * EndGameOpenAnnounce class.
 *
 * @author Dimitar Karamanov
 */
public final class EndGameOpenAnnounce extends AnnounceExecutor {

    /**
     * Constructor.
     *
     * @param game     BelotGame instance class.
     * @param gameLock game lock.
     */
    public EndGameOpenAnnounce(final Game game, final ReentrantReadWriteLock gameLock) {
        super(game, gameLock);
        // Precondition
        addPreCondition(new NotCondition(new HasContractAnnounce(game)));
        // Methods
        register(new EndGameOpenEnemyTeamEndGameZoneAnnounce(game, gameLock));
        register(new EndGameOpenPlayerTeamEndGameZoneAnnounce(game, gameLock));
    }
}

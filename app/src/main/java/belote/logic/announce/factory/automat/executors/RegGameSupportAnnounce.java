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
import belote.logic.announce.factory.automat.methods.RegGameSupportAllTrumpAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.PartnerTrumpAnnounce;

/**
 * RegGameSupportAnnounce class.
 *
 * @author Dimitar Karamanov
 */
public final class RegGameSupportAnnounce extends AnnounceExecutor {

    /**
     * Constructor.
     *
     * @param game     BelotGame instance class.
     * @param gameLock game lock.
     */
    public RegGameSupportAnnounce(final Game game, final ReentrantReadWriteLock gameLock) {
        super(game, gameLock);
        // Preconditions
        addPreCondition(new PartnerTrumpAnnounce(game));
        // Methods
        register(new RegGameSupportTrumpAnnounce(game, gameLock));
        register(new RegGameSupportAllTrumpAnnounce(game));
        //register(new RegGameSupportNoTrumpAnnounce(game));
    }
}

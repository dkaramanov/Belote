/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package belote.logic.announce.factory.automat.methods;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import belote.bean.Game;
import belote.bean.announce.Announce;
import belote.bean.player.Player;
import belote.logic.announce.factory.automat.base.AnnounceMethod;
import belote.logic.announce.factory.automat.executors.EndGameNormalNormalAnnounce;
import belote.logic.announce.factory.automat.executors.RegGameNormalAnnounce;
import belote.logic.announce.factory.automat.methods.base.ConditionListMethod;
import belote.logic.announce.factory.automat.methods.conditions.PlayerTeamEndGameZone;
import belote.logic.announce.factory.automat.methods.conditions.TeamCanNormalAnnounce;
import belote.logic.announce.factory.automat.methods.conditions.base.AnnounceCondition;

/**
 * EndGameNormalPlayerTeamEndGameZoneAnnounce class. Announce factory method which creates announce when player team has reached end game zone.
 *
 * @author Dimitar Karamanov
 */
public final class EndGameNormalPlayerTeamEndGameZoneAnnounce extends ConditionListMethod {

    /**
     * Regular game normal announce factory helper.
     */
    private final AnnounceMethod regGameNormalAnnounce;

    /**
     * Normal announce factory helper.
     */
    private final AnnounceMethod endGameNormalNormalAnnounce;

    /**
     * Condition helper.
     */
    private final AnnounceCondition teamCanNormalAnnounce;

    /**
     * Constructor.
     *
     * @param game     BelotGame instance class.
     * @param gameLock game lock.
     */
    public EndGameNormalPlayerTeamEndGameZoneAnnounce(final Game game, final ReentrantReadWriteLock gameLock) {
        super(game, gameLock);
        addAnnounceCondition(new PlayerTeamEndGameZone());

        teamCanNormalAnnounce = new TeamCanNormalAnnounce(game);
        regGameNormalAnnounce = new RegGameNormalAnnounce(game, gameLock);
        endGameNormalNormalAnnounce = new EndGameNormalNormalAnnounce(game, gameLock);
    }

    /**
     * Returns the proper Announce when conditions match.
     *
     * @param player who is on turn.
     * @return an Announce instance.
     */
    protected Announce createAnnounce(Player player) {
        if (teamCanNormalAnnounce.process(player)) {
            return regGameNormalAnnounce.getAnnounce(player);
        } else {
            return endGameNormalNormalAnnounce.getAnnounce(player);
        }
    }
}

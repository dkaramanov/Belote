package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.karamanov.beloteGame.Belote;
import com.karamanov.framework.graphics.Rectangle;

import belote.bean.player.Player;
import belote.logic.HumanBeloteFacade;

public final class EastPainter extends EastWestBasePainter {

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    EastPainter(Context context) {
        super(context);
    }

    @Override
    public void drawPlayerName(Canvas canvas, HumanBeloteFacade beloteFacade, Player player) {
        Paint paint = getPlayerPaint();

        boolean active = player.equals(beloteFacade.getTrickAttackPlayer());

        int x = 1;
        drawVerticalPlayerName(canvas, paint, player, x, active);
    }

    @Override
    public Rectangle getPlayerCardRectangle(Canvas canvas, HumanBeloteFacade game, int index, Player player) {
        int y = getFirstVerticalCardPos(canvas, game) + index * (getDeskWidth(canvas) - EastWestCardsOverlapped);
        return new Rectangle(GAME_FONT_HEIGHT, y, getDeskHeight(canvas), getDeskWidth(canvas));
    }

    @Override
    public Rectangle getPlayerPlayedCardRectangle(Canvas canvas, HumanBeloteFacade game, Player player) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);

        int x = rect.x + dip4 + rect.width;
        int y = (canvas.getHeight() - getCardHeight(canvas)) / 2;

        return new Rectangle(x, y, getCardWidth(canvas), getCardHeight(canvas));
    }

    @Override
    public Point getPlayerAnnounceLeftUpperCorner(Canvas canvas, HumanBeloteFacade game, Player player, int width, int height) {
        Rectangle rect = getPlayerCardRectangle(canvas, game, 0, player);
        int x = rect.x + rect.width + dip10 + dip2;
        int y = (canvas.getHeight() - height) / 2;
        return new Point(x, y);
    }

}

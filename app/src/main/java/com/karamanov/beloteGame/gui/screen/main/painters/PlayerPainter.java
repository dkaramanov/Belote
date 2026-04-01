package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.gui.screen.base.BasePainter;
import com.karamanov.beloteGame.gui.screen.main.BeloteView;
import com.karamanov.beloteGame.text.PlayerNameDecorator;
import com.karamanov.framework.graphics.Color;
import com.karamanov.framework.graphics.Rectangle;

import belote.bean.pack.card.Card;
import belote.bean.pack.card.rank.Ranks;
import belote.bean.player.Player;
import belote.logic.BeloteFacade;
import belote.logic.HumanBeloteFacade;

public abstract class PlayerPainter extends BasePainter {

    /**
     * Game font height.
     */
    protected final int GAME_FONT_HEIGHT;

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    protected PlayerPainter(Context context) {
        super(context);
        GAME_FONT_HEIGHT = Belote.fromPixelToDip(context, 14);
    }

    public abstract void drawPlayerName(Canvas canvas, HumanBeloteFacade beloteFacade, Player player);

    public abstract Rectangle getPlayerCardRectangle(Canvas canvas, HumanBeloteFacade game, final int index, Player player);

    public abstract Rectangle getPlayerPlayedCardRectangle(Canvas canvas, HumanBeloteFacade game, Player player);

    public abstract Point getPlayerAnnounceLeftUpperCorner(Canvas canvas, HumanBeloteFacade game, Player player, int width, int height);

    /**
     * Draws horizontal player name.
     *
     * @param canvas Canvas
     * @param paint  Paint
     * @param player which name is vertical drawn.
     * @param y      position.
     * @param active status.
     */
    protected final void drawHorizontalPlayerName(Canvas canvas, Paint paint, Player player, int y, boolean active) {
        Rect bounds = new Rect();
        PlayerNameDecorator playerDecorator = new PlayerNameDecorator(player);
        String name = playerDecorator.decorate(context).toUpperCase();
        paint.getTextBounds(name, 0, name.length(), bounds);
        final int x = (canvas.getWidth() - bounds.width()) / 2;
        paint.setColor(active ? Color.clDKGold.getRGB() : Color.clLightYellow.getRGB());
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setLinearText(true);
        canvas.drawText(name, x, y, paint);
    }

    /**
     * Draws vertical player name.
     *
     * @param canvas Canvas
     * @param paint  Paint
     * @param player which name is vertical drawn.
     * @param x      position.
     * @param active status.
     */
    protected void drawVerticalPlayerName(Canvas canvas, Paint paint, Player player, int x, boolean active) {
        int theta = 0;

        if (player.getID() == 1) {
            theta = 90;
        }

        if (player.getID() == 3) {
            theta = -90;
        }

        Rect bounds = new Rect();

        PlayerNameDecorator playerDecorator = new PlayerNameDecorator(player);
        String name = playerDecorator.decorate(context).toUpperCase();

        paint.getTextBounds(name, 0, name.length(), bounds);
        int y = (canvas.getHeight() - bounds.width()) / 2;
        paint.setColor(active ? Color.clDKGold.getRGB() : Color.clLightYellow.getRGB());

        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setLinearText(true);

        canvas.save();
        try {
            canvas.rotate(theta);
            if (player.getID() == 1) {
                canvas.drawText(name.toUpperCase(), y, -x, paint);
            } else {
                canvas.drawText(name.toUpperCase(), -y - bounds.width(), canvas.getWidth() - 1, paint);
            }
        } finally {
            canvas.restore();
        }
    }

    protected final int getCardsCount(BeloteFacade game) {
        return (game.isAnnounceGameMode()) ? 5 : 8;
    }

    /**
     * Draws player cards. Moved here the method because uses Canvas methods - flushGraphics() which is preffered to be used only from this class.
     *
     * @param canvas Graphics object.
     * @param player player which cards are drawn.
     * @param delay  between drawing cards.
     */
    protected final void drawPlayerCards(Canvas canvas, final BeloteView view, HumanBeloteFacade game, Player player, long delay) {
        int nCards = (game.isAnnounceGameMode()) ? BeloteFacade.ANNOUNCE_CARD_COUNT : Ranks.getRankCount();
        for (int i = 0; i < nCards; i++) {

            if (i < player.getCards().getSize()) {
                Rectangle rec = getPlayerCardRectangle(canvas, game, i, player);
                Card card = player.getCards().getCard(i);

                drowPlayerCard(game, canvas, card, rec);

                if (delay > 0) {
                    view.refresh();
                    sleep(delay);
                }
            }
        }
    }

    protected abstract void drowPlayerCard(HumanBeloteFacade game, Canvas canvas, Card card, Rectangle rect);


    @NonNull
    protected final Paint getPlayerPaint() {
        Paint paint = new Paint();
        paint.setTextSize(dipF12);
        paint.setColor(Color.clCream.getRGB());
        return paint;
    }

    /**
     * Sleeps for provided millisecond.
     *
     * @param ms provided millisecond.
     */
    private void sleep(final long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException ex) {
                // D.N.
            }
        }
    }

}

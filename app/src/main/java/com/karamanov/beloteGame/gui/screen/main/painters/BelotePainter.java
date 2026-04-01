/*
 * Copyright (c) Dimitar Karamanov 2008-2014. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the source code must retain
 * the above copyright notice and the following disclaimer.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 */
package com.karamanov.beloteGame.gui.screen.main.painters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;

import com.karamanov.beloteGame.Belote;
import com.karamanov.beloteGame.R;
import com.karamanov.beloteGame.gui.screen.base.BasePainter;
import com.karamanov.beloteGame.gui.screen.main.BeloteView;
import com.karamanov.beloteGame.text.ShortPlayerNameDecorator;
import com.karamanov.framework.graphics.Color;
import com.karamanov.framework.graphics.ImageUtil;
import com.karamanov.framework.graphics.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import belote.bean.Team;
import belote.bean.announce.Announce;
import belote.bean.announce.AnnounceList;
import belote.bean.announce.AnnounceUnit;
import belote.bean.announce.suit.AnnounceSuits;
import belote.bean.announce.type.AnnounceTypes;
import belote.bean.pack.card.Card;
import belote.bean.pack.card.suit.Suit;
import belote.bean.player.Player;
import belote.bean.player.Players;
import belote.logic.BeloteFacade;
import belote.logic.HumanBeloteFacade;

/**
 * BelotPainter class.
 *
 * @author Dimitar Karamanov
 */
public final class BelotePainter extends BasePainter {

    /**
     * Game font height.
     */
    private final int GAME_FONT_HEIGHT;

    private Map<Player, PlayerPainter> playerPainters = new HashMap<>();

    /**
     * Constructor.
     *
     * @param context Context object.
     */
    public BelotePainter(Context context) {
        super(context);

        GAME_FONT_HEIGHT = Belote.fromPixelToDip(context, 14);

        playerPainters = new HashMap<>();
        playerPainters.put(Players.NORTH, new NorthPainter(context));
        playerPainters.put(Players.EAST, new EastPainter(context));
        playerPainters.put(Players.SOUTH, new SouthPainter(context));
        playerPainters.put(Players.WEST, new WestPainter(context));
    }

    public int getFontHeight() {
        return GAME_FONT_HEIGHT;
    }

    /**
     * Draws player name.
     *
     * @param beloteFacade BelotGame instance.
     * @param player       which name is vertical drawn.
     */
    private void drawPlayerName(Canvas canvas, HumanBeloteFacade beloteFacade, Player player) {
        Objects.requireNonNull(playerPainters.get(player)).drawPlayerName(canvas, beloteFacade, player);
    }

    public Rectangle getPlayerCardRectangle(Canvas canvas, HumanBeloteFacade game, final int index, Player player) {
        game.getGameLock().readLock().lock();
        try {
            return Objects.requireNonNull(playerPainters.get(player)).getPlayerCardRectangle(canvas, game, index, player);
        } finally {
            game.getGameLock().readLock().unlock();
        }
    }

    /**
     * Returns announce text.
     *
     * @param announce which text is retrieves.
     * @return String announce text.
     */
    private String getAnnounceText(Announce announce) {
        String result;
        if (announce.isTrumpAnnounce()) {
            if (announce.getType().equals(AnnounceTypes.Double)) {
                result = textDecorator.getAnnounceType(AnnounceTypes.Double);
            } else if (announce.getType().equals(AnnounceTypes.Redouble)) {
                result = textDecorator.getAnnounceType(AnnounceTypes.Redouble);
            } else {
                result = textDecorator.getAnnounceSuit(announce.getAnnounceSuit());
            }
        } else {
            if (announce.getType().equals(AnnounceTypes.Double)) {
                result = textDecorator.getAnnounceSuitShort(announce.getAnnounceSuit()) + " " + textDecorator.getAnnounceType(AnnounceTypes.Double);
            } else if (announce.getType().equals(AnnounceTypes.Redouble)) {
                result = textDecorator.getAnnounceSuitShort(announce.getAnnounceSuit()) + " " + textDecorator.getAnnounceType(AnnounceTypes.Redouble);
            } else {
                result = textDecorator.getAnnounceSuit(announce.getAnnounceSuit());
            }
        }

        return result;
    }

    /**
     * Returns announce rectangle.
     *
     * @param game     BelotGame instance.
     * @param announce to be drawn.
     * @return Rectangle of the announcement.
     */
    private Rectangle getAnnounceRectangle(Canvas canvas, HumanBeloteFacade game, Announce announce, Paint paint) {
        int w;
        int h;
        String str = getAnnounceText(announce);

        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);

        if (announce.isTrumpAnnounce()) {
            Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
            Bitmap image = getSuitImage(suit);
            w = bounds.width() + dip6 + image.getScaledWidth(canvas) + dip4;
            h = Math.max(image.getScaledHeight(canvas), bounds.height());
        } else {
            w = bounds.width() + dip6;
            h = bounds.height();
        }

        Point point = Objects.requireNonNull(playerPainters.get(announce.getPlayer())).getPlayerAnnounceLeftUpperCorner(canvas, game, announce.getPlayer(), w, h);

        return new Rectangle(point.x - dip2, point.y, w + dip4, h);
    }

    /**
     * Draws last announce.
     *
     * @param game BelotGame instance.
     */
    private void drawLastAnnounce(Canvas canvas, HumanBeloteFacade game) {
        if (game.isAnnounceGameMode()) {
            final int count = game.getGame().getAnnounceList().getCount();
            if (count > 0) {
                if (!game.isPlayerIsAnnoincing()) {
                    drawAnnounce(canvas, game, game.getGame().getAnnounceList().getAnnounce(count - 1));
                }
            }
        }
    }

    /**
     * Draws announce.
     *
     * @param canvas   Canvas
     * @param game     BelotGame instance.
     * @param announce to be drawn.
     */
    private void drawAnnounce(Canvas canvas, HumanBeloteFacade game, Announce announce) {
        Paint paint = new Paint();
        paint.setColor(Color.clDarkRed.getRGB());
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setLinearText(true);
        paint.setTextSize(dipF14);

        NinePatchDrawable bubble = announce.getPlayer().getTeam().equals(Team.N_S) ? pictureDecorator.getBubbleLeft() : pictureDecorator
                .getBubbleRight();

        String str = getAnnounceText(announce);
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);

        Rect dest = getBubbleAnnounceRectangle(canvas, game, announce);
        bubble.setBounds(dest);
        bubble.draw(canvas);

        if (announce.isTrumpAnnounce()) {
            Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
            Bitmap image = getSuitImage(suit);
            canvas.drawBitmap(image, dest.left + dip10, 2 * dip10 + dest.top - bounds.height() + (float) (bounds.height() - image.getScaledHeight(canvas)) / 2, paint);
            canvas.drawText(str, dest.left + dip10 + dip2 + image.getScaledWidth(canvas), 2 * dip10 + dest.top, paint);
        } else {
            canvas.drawText(str, dest.left + dip10, 2 * dip10 + dest.top, paint);
        }
    }

    private Rect getBubbleAnnounceRectangle(Canvas canvas, HumanBeloteFacade game, Announce announce) {
        Paint paint = initBubblePaint();

        String str = getAnnounceText(announce);
        Rect bounds = new Rect();
        paint.getTextBounds(str, 0, str.length(), bounds);

        Rect dest = new Rect();
        Rectangle rect = getAnnounceRectangle(canvas, game, announce, paint);

        int x = rect.x + dip3;
        int y = rect.y;

        if (Players.NORTH.equals(announce.getPlayer())) {
            y += dip1;
        }

        if (Players.SOUTH.equals(announce.getPlayer())) {
            y -= dip1;
        }

        dest.left = x - dip10;
        dest.top = y - dip10;

        if (announce.isTrumpAnnounce()) {
            Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
            Bitmap image = getSuitImage(suit);
            dest.bottom = dest.top + Math.max(image.getScaledHeight(canvas), bounds.height()) + dip10 * 2;
            dest.right = dest.left + image.getScaledWidth(canvas) + dip2 + bounds.width() + dip10 * 2;
        } else {
            dest.bottom = dest.top + bounds.height() + 2 * dip10;
            dest.right = dest.left + bounds.width() + 2 * dip10;
        }

        return dest;
    }

    private Paint initBubblePaint() {
        Paint paint = new Paint();
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setLinearText(true);
        paint.setTextSize(dipF14);
        paint.setColor(Color.clDarkGreen.getRGB());
        paint.setStyle(Style.FILL);
        return paint;
    }

    /**
     * Draws score.
     *
     * @param canvas Canvas
     * @param game BelotGame instance.
     */
    private void drawAnnounce(Canvas canvas, BeloteFacade game) {
        if (game.isPlayingGameMode()) {
            Paint paint = new Paint();
            paint.setTextSize(dipF12);
            paint.setFakeBoldText(true);
            paint.setAntiAlias(true);

            Rect bounds = new Rect();
            paint.getTextBounds("|", 0, 1, bounds);
            final int maxY = bounds.height();

            Announce announce = game.getGame().getAnnounceList().getOpenContractAnnounce();
            bounds = getPlayerNameBounds(announce.getPlayer(), paint);
            drawPlayerShortName(canvas, announce.getPlayer(), paint, maxY, bounds.width());

            int x = canvas.getWidth() - bounds.width() - dip1;
            if (announce.isColorless()) {
                drawColorlessAnnounce(canvas, announce, paint, x, maxY);
            } else if (announce.isTrumpAnnounce()) {
                drawTrumpAnnounce(canvas, announce, paint, x, bounds);
            }

            drawContraAnnounces(canvas, game, paint, maxY);
        }
    }

    private void drawContraAnnounces(Canvas canvas, BeloteFacade game, Paint paint, int maxY) {
        Announce announce = game.getGame().getAnnounceList().getOpenContractAnnounce();
        AnnounceList announces = game.getGame().getAnnounceList().getSuitAnnounces(announce.getAnnounceSuit());
        Announce dbl = announces.getDoubleAnnounce();
        Announce redbl = announces.getRedoubleAnnounce();

        int maxNameWidth = 0;
        int maxAnnounceWidth = 0;

        if (dbl != null) {
            Rect bounds = getPlayerNameBounds(dbl.getPlayer(), paint);
            maxNameWidth = Math.max(maxNameWidth, bounds.width());

            bounds = new Rect();
            paint.getTextBounds(context.getString(R.string.DoubleAnnounce), 0, 1, bounds);
            maxAnnounceWidth = Math.max(maxAnnounceWidth, bounds.width());
        }

        if (redbl != null) {
            Rect bounds = getPlayerNameBounds(redbl.getPlayer(), paint);
            maxNameWidth = Math.max(maxNameWidth, bounds.width());

            bounds = new Rect();
            paint.getTextBounds(context.getString(R.string.RedoubleAnnounce), 0, 1, bounds);
            maxAnnounceWidth = Math.max(maxAnnounceWidth, bounds.width());
        }

        int y = maxY;
        if (dbl != null) {
            y += maxY;
            drawPlayerNameContra(context.getString(R.string.DoubleAnnounce), canvas, dbl.getPlayer(), paint, y, Color.clRed, maxNameWidth, maxAnnounceWidth);
        }

        if (redbl != null) {
            y += maxY;
            drawPlayerNameContra(context.getString(R.string.RedoubleAnnounce), canvas, redbl.getPlayer(), paint, y, Color.clDKGold, maxNameWidth, maxAnnounceWidth);
        }
    }

    private void drawTrumpAnnounce(Canvas canvas, Announce announce, Paint paint, int x, Rect bounds) {
        Suit suit = AnnounceUnit.transformFromAnnounceSuitToSuit(announce.getAnnounceSuit());
        Bitmap image = getSuitImage(suit);
        image = ImageUtil.transformToMixedColorImage(image, new Color(192));
        x -= image.getScaledWidth(canvas) + dip5;
        int y = dip1;
        if (bounds.height() - image.getScaledHeight(canvas) > 0) {
            y = (bounds.height() - image.getScaledHeight(canvas)) / 2;
        }
        canvas.drawBitmap(image, x, y, paint);
    }

    private void drawColorlessAnnounce(Canvas canvas, Announce announce, Paint paint, int x, int y) {
        String announceShort = announce.getAnnounceSuit().equals(AnnounceSuits.AllTrump) ? context.getString(R.string.AllTrumpsAnnounceShort) : context
                .getString(R.string.NotTrumpsAnnounceShort);
        Rect bounds = new Rect();
        paint.getTextBounds(announceShort, 0, announceShort.length(), bounds);
        x -= bounds.width() + dip5;
        paint.setColor(Color.clLightGreen.getRGB());
        canvas.drawText(announceShort, x, y, paint);
    }

    private Rect getPlayerNameBounds(Player player, Paint paint) {
        ShortPlayerNameDecorator decorator = new ShortPlayerNameDecorator(player);
        String playerShort = decorator.decorate(context);

        Rect bounds = new Rect();
        paint.getTextBounds(playerShort, 0, playerShort.length(), bounds);
        return bounds;
    }

    private void drawPlayerNameContra(String announceShort, Canvas canvas, Player player, Paint paint, int y, Color color, int maxNameWidth, int maxAnnounceWidth) {
        drawPlayerShortName(canvas, player, paint, y, maxNameWidth);

        final int x = canvas.getWidth() - maxNameWidth - dip1 - maxAnnounceWidth - dip5;
        paint.setColor(color.getRGB());
        canvas.drawText(String.valueOf(announceShort.charAt(0)), x, y, paint);
    }

    private void drawPlayerShortName(Canvas canvas, Player player, Paint paint, int y, int maxNameWidth) {
        ShortPlayerNameDecorator decorator = new ShortPlayerNameDecorator(player);
        String playerShort = decorator.decorate(context);

        final int x = canvas.getWidth() - maxNameWidth - dip1;
        paint.setColor(Color.clCream.getRGB());
        canvas.drawText(playerShort, x, y, paint);
    }

    /**
     * Draws score.
     *
     * @param beloteFacade BelotGame instance.
     */
    private void drawScore(Canvas canvas, BeloteFacade beloteFacade) {
        float dip12 = Belote.fromPixelToDipF(context, 12);
        Paint paint = new Paint();
        paint.setTextSize(dip12);
        paint.setColor(Color.clLightGreen.getRGB());
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);
        paint.setDither(true);

        int maxWidth = 0;
        int maxHeight = 0;

        Rect boundsDigids = new Rect();
        String maxScore = " 99999";
        paint.getTextBounds(maxScore, 0, maxScore.length(), boundsDigids);

        int i = 0;
        for (final Team t : beloteFacade.getGame().teams()) {
            StringBuilder team = new StringBuilder();
            for (final Player player: t.players()) {
                if (team.length() > 0) {
                    team.append("-");
                }

                team.append(new ShortPlayerNameDecorator(player).decorate(context));
            }

            String score = team.toString();
            Rect bounds = new Rect();
            paint.getTextBounds(score + "|", 0, score.length() + 1, bounds);

            maxHeight = Math.max(maxHeight, bounds.height());
            maxWidth = Math.max(maxWidth, bounds.width());

            canvas.drawText(score, i * (boundsDigids.width() + maxWidth), bounds.height(), paint);
            i++;
        }

        i = 0;
        paint.setColor(Color.clCream.getRGB());
        for (final Team team : beloteFacade.getGame().teams()) {
            String score = " " + team.getPoints().getAllPoints();
            canvas.drawText(score, maxWidth + i * (maxWidth + boundsDigids.width()), maxHeight, paint);
            i++;
        }
    }

    /**
     * Draws played hand cards.
     *
     * @param game BelotGame instance.
     */
    private void drawCurrentTrickCards(Canvas canvas, HumanBeloteFacade game) {
        Player player = game.getGame().getTrickAttackPlayer();
        for (final Card card : game.getGame().getTrickCards().list()) {
            drawPlayedCardByPlayer(canvas, game, player, card);
            player = game.getPlayerAfter(player);
        }
    }

    /**
     * Draws player hand.
     *
     * @param game   BelotGame instance.
     * @param player which rectangle is retrieved.
     * @param card   played card.
     */
    private void drawPlayedCardByPlayer(Canvas canvas, HumanBeloteFacade game, Player player, Card card) {
        final Rectangle rec = getPlayerPlayedCardRectangle(canvas, game, player);

        final Player winner = game.getNextTrickAttackPlayer();
        if (player.equals(winner)) {
            drawMixedColorCard(canvas, card, rec.x, rec.y, Color.clPureYellow);
        } else {
            drawCard(canvas, card, rec.x, rec.y);
        }
    }

    /**
     * Returns player' played card rectangle.
     *
     * @param game   BelotGame instance.
     * @param player which rectangle is retrieved.
     * @return Rectangle instance.
     */
    private Rectangle getPlayerPlayedCardRectangle(Canvas canvas, HumanBeloteFacade game, Player player) {
        return Objects.requireNonNull(playerPainters.get(player)).getPlayerPlayedCardRectangle(canvas, game, player);
    }

    /**
     * Draws players cards. Moved here the method because uses Canvas methods - flushGraphics() which is preffered to be used only from this class.
     */
    private void drawPlayersNames(Canvas canvas, BeloteView view, HumanBeloteFacade beloteFacade) {
        for (final Player player : beloteFacade.getGame().players()) {
            drawPlayerName(canvas, beloteFacade, player);
        }
    }

    /**
     * Draws players cards. Moved here the method because uses Canvas methods - flushGraphics() which is preffered to be used only from this class.
     *
     * @param canvas Graphics object.
     * @param delay  between drawing cards.
     */
    private void drawPlayersCards(Canvas canvas, BeloteView view, HumanBeloteFacade game, long delay) {
        Player player = game.getGame().getDealAttackPlayer();
        for (int i = 0; i < game.getGame().getPlayersCount(); i++) {
            drawPlayerCards(canvas, view, game, player, delay);
            player = game.getPlayerAfter(player);
        }
    }

    /**
     * Draws player cards. Moved here the method because uses Canvas methods - flushGraphics() which is preffered to be used only from this class.
     *
     * @param canvas Graphics object.
     * @param player player which cards are drawn.
     * @param delay  between drawing cards.
     */
    private void drawPlayerCards(Canvas canvas, final BeloteView view, HumanBeloteFacade game, Player player, long delay) {
        Objects.requireNonNull(playerPainters.get(player)).drawPlayerCards(canvas, view, game, player, delay);
    }

    private void clearBackground(Canvas graphics) {
        GradientDrawable bkg = pictureDecorator.getMainBKG();
        bkg.setDither(true);
        bkg.setBounds(new Rect(0, 0, graphics.getWidth(), graphics.getHeight()));
        bkg.draw(graphics);
    }

    public void drawGame(Canvas graphics, HumanBeloteFacade game, BeloteView view, long delay) {
        game.getGameLock().readLock().lock();
        try {
            clearBackground(graphics);
            drawScore(graphics, game);
            drawAnnounce(graphics, game);
            drawPlayersNames(graphics, view, game);
            drawPlayersCards(graphics, view, game, delay);
            drawCurrentTrickCards(graphics, game);
            drawLastAnnounce(graphics, game);
        } finally {
            game.getGameLock().readLock().unlock();
        }
    }
}

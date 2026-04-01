package belote.bean.player;

import java.util.List;

import belote.bean.Team;

public final class Players {

    public final static Player NORTH = new NorthPlayer(Team.N_S, 0);
    public final static Player EAST = new EastPlayer(Team.E_W, 1);
    public final static Player SOUTH = new SouthPlayer(Team.N_S, 2);
    public final static Player WEST = new WestPlayer(Team.E_W, 3);

    private final static List<Player> players = List.of(NORTH, EAST, SOUTH, WEST);

    private Players() {
    }

    public static List<Player> initaPlayers() {
        for (final Player player : players) {
            player.clearData();
        }
        return players;
    }

    /**
     * Returns next player (iterates in cycle).
     *
     * @param player current player.
     * @return Player next player.
     */
    public static Player getPlayerAfter(final Player player) {
        if (player.getID() < players.size() - 1) {
            return players.get(player.getID() + 1);
        }
        return players.get(0);
    }


    /**
     * Returns previous player (iterates in cycle).
     *
     * @param player current player.
     * @return Player previous player.
     */
    public static Player getPlayerBefore(final Player player) {
        if (player.getID() > 0) {
            return players.get(player.getID() - 1);
        }
        return players.get(players.size() - 1);
    }
}

package it.polimi.game.model;

public class Game {
    private static Game instance = null;
    private GameHandler gh;

    protected Game() {
        this.gh=null;
    }


    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public GameHandler getGh() {
        return gh;
    }

    public void setGh(GameHandler gh) {
        this.gh = gh;
    }
}

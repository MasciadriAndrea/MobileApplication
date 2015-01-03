package it.polimi.game.state;

import it.polimi.game.model.Game;

public class TurnRunner implements Runnable{
    Integer sb;
    public TurnRunner(Integer sb){
        this.sb=sb;
    }
    public void run ()
    {
        Game.getInstance().getGh().playTurn(sb);
    }
}
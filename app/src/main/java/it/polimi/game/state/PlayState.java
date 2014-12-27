package it.polimi.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import it.polimi.core.Assets;
import it.polimi.framework.util.Painter;
import it.polimi.core.GameMainActivity;
import it.polimi.framework.util.UIButton;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

public class PlayState extends State{
    private List<UIButton> bp1;
    private List<UIButton> bp2;
    private UIButton t1,t2;
    private Player p1,p2;
    private GameHandler gh;

    public void init(){
        gh=Game.getInstance().getGh();
        bp1=new ArrayList<UIButton>();
        bp2=new ArrayList<UIButton>();
        for (int i=0;i<6;i++){
            bp1.add(new UIButton(10+(100*i), 270, 110+(100*i), 370, Assets.bowl, Assets.bowl));
            bp2.add(new UIButton(610-(100*i), 10, 710-(100*i), 110, Assets.bowl, Assets.bowl));
        }
        t1=new UIButton(610, 140, 710, 240, Assets.tray, Assets.tray);
        t2=new UIButton(10, 140, 110, 240, Assets.tray, Assets.tray);
    };

    public void update(float delta){
        if(gh.getIsGameFinished()){
            setCurrentState(new ScoreState());
        }
    };

    public void render(Painter g){
        g.drawImage(Assets.background, 0, 0);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 25);
        Integer[] bs=gh.getBoard().getBoardStatus();
        int i=0;
        for(UIButton ub:bp1) {
            g.drawString(bs[i].toString(), 60+(100*i), 260);
            ub.render(g);
            i++;
        }
        if(gh.getActivePlayer().equals(gh.getP1())){
            g.setColor(Color.BLUE);
        }
        g.drawString(gh.getP1().getName(),510, 250);
        g.setColor(Color.WHITE);
        g.drawString(bs[i].toString(),120, 190);
        t1.render(g);
        i++;
        for(UIButton ub:bp2) {
            g.drawString(bs[i].toString(),660-(100*(i-7)), 120);
            ub.render(g);
            i++;
        }
        g.drawString(bs[i].toString(), 510, 190);
        if(gh.getActivePlayer().equals(gh.getP2())){
            g.setColor(Color.BLUE);
        }
        g.drawString(gh.getP2().getName(),510, 250);
        g.setColor(Color.WHITE);
        t2.render(g);
    };

    public boolean onTouch(MotionEvent e, int scaledX, int scaledY){
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
           for(UIButton u:bp1) {
                u.onTouchDown(scaledX, scaledY);
            }
            for(UIButton ub:bp2) {
                ub.onTouchDown(scaledX, scaledY);
            }
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            int i=0;
            for(UIButton ub:bp1) {
                i++;
                if (ub.isPressed(scaledX, scaledY)) {
                    ub.cancel();
                    Game.getInstance().getGh().playTurn(i);
                    Log.v("play state","selected bowl id ->"+i);
                }
            }
            for(UIButton ub:bp2) {
                i++;
                if (ub.isPressed(scaledX, scaledY)) {
                    ub.cancel();
                    Game.getInstance().getGh().playTurn(i);
                    Log.v("play state","selected bowl id ->"+i);
                }
            }
               for(UIButton u:bp1) {
                    u.cancel();
                 }
                for(UIButton ub:bp2) {
                    ub.cancel();
                }
        }
        return true;
    }
}
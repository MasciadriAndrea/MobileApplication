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
import it.polimi.game.model.Bee;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

public class PlayState extends State{
    private List<UIButton> bp1;
    private List<UIButton> bp2;
    private UIButton t1,t2;
    Bee bee1,bee2;
    private GameHandler gh;

    public void init(){
        gh=Game.getInstance().getGh();
        bp1=new ArrayList<UIButton>();
        bp2=new ArrayList<UIButton>();
        for (int i=0;i<6;i++){
            bp1.add(new UIButton(85+(250*i), 865, 335+(250*i), 1115, Assets.bowl, Assets.bowl));
            bp2.add(new UIButton(1585-(250*i), 85, 1835-(250*i), 335, Assets.bowl, Assets.bowl));
        }
        bee1=Game.getInstance().getBee1();
        bee2=Game.getInstance().getBee2();
        t1=new UIButton(1585, 475, 1835, 725, Assets.tray, Assets.tray);
        t2=new UIButton(85, 475, 335, 725, Assets.tray, Assets.tray);
    };

    public void update(float delta){
        checkPlayability();
        if((bee1.getX()==Game.getInstance().getxBee1())&&(bee1.getY()==Game.getInstance().getyBee1())){
            Game.getInstance().setxBee1((int) bee1.getxHome());
            Game.getInstance().setyBee1((int) bee1.getyHome());
        }
        if((bee2.getX()==Game.getInstance().getxBee2())&&(bee2.getY()==Game.getInstance().getyBee2())){
            Game.getInstance().setxBee2((int) bee2.getxHome());
            Game.getInstance().setyBee2((int) bee2.getyHome());
        }
        if(gh.getIsGameFinished()){
            setCurrentState(new ScoreState());
        }
    }

    private void checkPlayability() {
        if(bee2.atHome()&&bee1.atHome()){
            //playable=true;
        }
    }

    public void render(Painter g){
        g.drawImage(Assets.background, 0, 0);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.MONOSPACE, 50);
        Integer[] bs=gh.getBoard().getBoardStatus();
        int i=0;
        for(UIButton ub:bp1) {
            g.drawString(bs[i].toString(), 210+(250*i), 835);
            ub.render(g);
            i++;
        }
        if(gh.getActivePlayer().equals(gh.getP1())){
            g.setColor(Color.BLUE);
        }
        g.drawString(gh.getP1().getName(),1085, 600);
        g.setColor(Color.WHITE);
        g.drawString(bs[i].toString(),1485, 600);
        t1.render(g);
        i++;
        for(UIButton ub:bp2) {
            g.drawString(bs[i].toString(),1710-(250*(i-7)), 385);
            ub.render(g);
            i++;
        }
        g.drawString(bs[i].toString(), 385, 600);
        if(gh.getActivePlayer().equals(gh.getP2())){
            g.setColor(Color.BLUE);
        }
        g.drawString(gh.getP2().getName(),585, 600);
        g.setColor(Color.WHITE);
        t2.render(g);
        renderBees(g);
    };

    public void renderBees(Painter g){
        float xBee1=Game.getInstance().getxBee1();
        float xBee2=Game.getInstance().getxBee2();
        float yBee1=Game.getInstance().getyBee1();
        float yBee2=Game.getInstance().getyBee2();
        bee1.fly(xBee1,yBee1);
        g.drawImage(Assets.rotate(Assets.bee_green, (float)bee1.getAng()), (int) bee1.getX(), (int) bee1.getY(), 250,250);
        bee2.fly(xBee2,yBee2);
        g.drawImage(Assets.rotate(Assets.bee_red, (float) bee2.getAng()), (int) bee2.getX(), (int) bee2.getY(), 250, 250);

    }

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
                if ((ub.isPressed(scaledX, scaledY))&&(Game.getInstance().isPlayable())&&(!Game.getInstance().getGh().getP1().getId().equals(1))) {
                    ub.cancel();
                    Game.getInstance().getGh().playTurn(i);
                    Log.v("play state","selected bowl id ->"+i);
                }
            }
            for(UIButton ub:bp2) {
                i++;
                if ((ub.isPressed(scaledX, scaledY))&&(Game.getInstance().isPlayable())&&(!Game.getInstance().getGh().getP1().getId().equals(1))) {
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
package it.polimi.game.state;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import it.polimi.activities.MenuActivity;
import it.polimi.core.Assets;
import it.polimi.framework.util.Painter;
import it.polimi.core.GameMainActivity;
import it.polimi.framework.util.RandomNumberGenerator;
import it.polimi.framework.util.UIButton;
import it.polimi.game.model.Bee;
import it.polimi.game.model.Bowl;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;
import it.polimi.game.model.Seed;
import it.polimi.game.model.Tray;

public class PlayState extends State{
    private UIButton pauseBtn;
    private List<UIButton> bp1;
    private List<UIButton> bp2;
    private Bitmap p1Bee,p2Bee,bowlP1,bowlP2,bowlInactive;
    Bee bee1,bee2;
    private GameHandler gh;
    private static int color1=Color.rgb(33,0,0);
    private Bitmap[] seedImg;

    public void init(){
        Game.getInstance().makePlayable();
        gh=Game.getInstance().getGh();
        seedImg=new Bitmap[]{Assets.seed0,Assets.seed45,Assets.seed90,Assets.seed135};
        bp1=new ArrayList<UIButton>();
        bp2=new ArrayList<UIButton>();
        bowlP1=Assets.bowl_green;
        bowlP2=Assets.bowl_red;
        bowlInactive=Assets.bowl;
        for (int i=0;i<6;i++){
            bp1.add(new UIButton(85+(250*i), 865, 335+(250*i), 1115, bowlP1, bowlP1));
            bp2.add(new UIButton(1585-(250*i), 85, 1835-(250*i), 335, bowlP2, bowlP2));
        }
        bee1=Game.getInstance().getBee1();
        bee2=Game.getInstance().getBee2();
        p1Bee=Assets.bee_green;
        if(Game.getInstance().getGh().getP1().getId().equals(1)){p1Bee=Assets.bee_megabrain;}
        p2Bee=Assets.bee_red;
        if(Game.getInstance().getGh().getP2().getId().equals(1)){p2Bee=Assets.bee_megabrain;}
        createSeeds();

        pauseBtn = new UIButton(900,540,1020,660,Assets.menu,Assets.menu);
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
        g.setColor(color1);
        g.setFont(Typeface.DEFAULT_BOLD, 50);

        //render pauseBtn
        pauseBtn.render(g);

        Integer[] bs=gh.getBoard().getBoardStatus();
        int i=0;
        if(gh.getActivePlayer().equals(gh.getP1())){
            for (UIButton ub : bp1) {
                g.drawString(bs[i].toString(), 210 + (250 * i), 835);
                ub.render(g);
                i++;
            }
        }else{
            for (int j=0;j<6;j++){
                g.drawString(bs[i].toString(), 210 + (250 * i), 835);
                g.drawImage(bowlInactive, 85+(250*j), 865, 250,250);
                i++;
            }
        }

        g.drawString(gh.getP1().getName(),1085, 600);
        g.drawString(bs[i].toString(),1485, 600);
        g.drawImage(Assets.tray, 1585,475, 250,250);//t1
        i++;
        if(gh.getActivePlayer().equals(gh.getP2())){
            for(UIButton ub:bp2) {
                g.drawString(bs[i].toString(),1710-(250*(i-7)), 385);
                ub.render(g);
                i++;
            }
          }else{
            for (int j=0;j<6;j++){
                g.drawString(bs[i].toString(),1710-(250*(i-7)), 385);
                g.drawImage(bowlInactive, 1585-(250*j), 85, 250,250);
                i++;
            }
        }
        g.drawString(bs[i].toString(), 385, 600);
        g.drawString(gh.getP2().getName(),585, 600);
        g.drawImage(Assets.tray, 85,475, 250,250);//t2
        renderSeeds(g);
        renderBees(g);
    };

    public void renderSeeds(Painter g){
        for(Seed s:Game.getInstance().getSeeds()){
            if(s.isVisible()) {
                g.drawImage(s.getBm(), (int) s.getX(), (int) s.getY(), 50, 50);
            }
        }
    }

    public void createSeeds(){
        GameHandler gh=Game.getInstance().getGh();
        List<Bowl> bowlP1=gh.getBoard().getSemiBoardByPlayer(gh.getP1()).getBowls();
        List<Bowl> bowlP2=gh.getBoard().getSemiBoardByPlayer(gh.getP2()).getBowls();
        List<Seed> seeds=new ArrayList<Seed>();
        for(Bowl b:bowlP1){
            for(int i=1;i<=b.getSeeds();i++){
                int j=RandomNumberGenerator.getRandIntBetween(0, 3);
                seeds.add(new Seed(b.getId()-1,seedImg[j]));
            }
        }
        for(Bowl b:bowlP2){
            for(int i=1;i<=b.getSeeds();i++){
                int j=RandomNumberGenerator.getRandIntBetween(0, 3);
                seeds.add(new Seed(b.getId(),seedImg[j]));
            }
        }
        Game.getInstance().setSeeds(seeds);
    }

    public void renderBees(Painter g){
        float xBee1=Game.getInstance().getxBee1();
        float xBee2=Game.getInstance().getxBee2();
        float yBee1=Game.getInstance().getyBee1();
        float yBee2=Game.getInstance().getyBee2();
        bee1.fly(xBee1,yBee1);
        g.drawImage(Assets.rotate(p1Bee, (float)bee1.getAng()), (int) bee1.getX(), (int) bee1.getY(), 250,250);
        bee2.fly(xBee2,yBee2);
        g.drawImage(Assets.rotate(p2Bee, (float) bee2.getAng()), (int) bee2.getX(), (int) bee2.getY(), 250, 250);

    }

    public boolean onTouch(MotionEvent e, int scaledX, int scaledY){
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
           for(UIButton u:bp1) {
              // if(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP1())){
               if(Game.getInstance().isPlayable()){
                    u.onTouchDown(scaledX, scaledY);
               }
            }
            for(UIButton ub:bp2) {
                //if(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP2())){
                if(Game.getInstance().isPlayable()){
                    ub.onTouchDown(scaledX, scaledY);
                }
            }
            pauseBtn.onTouchDown(scaledX,scaledY);
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

            if (pauseBtn.isPressed(scaledX,scaledY)){
                //pauseBtn.cancel();
                //TODO open a dialog instead lunch activity
                //TODO Menage game pause and resume
                Intent intent = new Intent(Game.getInstance().getGameActivity(), MenuActivity.class);
                Game.getInstance().getGameActivity().startActivity(intent);


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
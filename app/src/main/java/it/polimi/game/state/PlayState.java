package it.polimi.game.state;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import it.polimi.activities.PauseActivity;
import it.polimi.core.Assets;
import it.polimi.framework.util.Painter;
import it.polimi.framework.util.RandomNumberGenerator;
import it.polimi.framework.util.UIButton;
import it.polimi.game.model.Bee;
import it.polimi.game.model.Bowl;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Seed;

public class PlayState extends State{
    private UIButton pauseBtn;
    private List<UIButton> bp1;
    private List<UIButton> bp2;
    private Boolean isMegabrainFirst,isRendered;
    private Bitmap p1Bee,p2Bee,bowlP1,bowlP2,bowlInactive,seed;
    Bee bee1,bee2;
    private GameHandler gh;
    private static int color1=Color.rgb(61,33,13);

    public void init(){
        isRendered=false;
        isMegabrainFirst=false;
        Game.getInstance().makePlayable();
        Game game=Game.getInstance();
        gh=game.getGh();
        seed=Assets.seed;
        bp1=new ArrayList<UIButton>();
        bp2=new ArrayList<UIButton>();
        bowlP1=Assets.bowl_1;
        bowlP2=Assets.bowl_2;
        bowlInactive=Assets.bowl;
        initializeButton();
        bee1=Game.getInstance().getBee1();
        bee2=Game.getInstance().getBee2();
        p1Bee=Assets.bee;
        p2Bee=Assets.bee;
        createSeeds();
        pauseBtn = new UIButton(900,520,1020,680,Assets.menu,Assets.menu);
        GameHandler gh=Game.getInstance().getGh();
        if(gh.getActivePlayer().getId().equals(1)){//1 is megabrain
            Game.getInstance().makeUnPlayable();
            this.isMegabrainFirst=true;
        }
        Game.getInstance().setAngp1(0);
        if(Game.getInstance().getGh().getIsHH()){
            if(Game.getInstance().isTableMode()) {
                Game.getInstance().setAngp2(180);
            }else{
                Game.getInstance().setAngp2(0);
            }
        }else{
            Game.getInstance().setAngp2(0);
        }
    };


    public void update(float delta){
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
        if((Game.getInstance().getSwitchPlayer())&&(bee1.atHome())&&(bee2.atHome())) {
            Game.getInstance().switchPositions();
            initializeButton();
            this.redistributeSeeds();
            Game.getInstance().setSwitchPlayer(false);
        }
        if((isRendered)&&(isMegabrainFirst)){
            TurnRunner r = new TurnRunner(gh.megabrainSelectBowlId());
            Thread t = new Thread(r);
            t.start();
            isMegabrainFirst=false;
        }
    }

    public void render(Painter g){
        g.setColor(Color.rgb(170,205,255));//background
        g.fillRect(0,0,1920,1200);
        g.setColor(color1);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        Game game=Game.getInstance();
        //render pauseBtn
        pauseBtn.render(g);

        Integer[] bs=gh.getBoard().getBoardStatus();
        int i=0;
        if(gh.getActivePlayer().equals(gh.getP1())){
            for (UIButton ub : bp1) {
                g.drawString(bs[i].toString(), game.getxLabel()[i], game.getyLabel()[i], game.getAngp1());//string bowl p1
                ub.render(g);
                i++;
            }
        }else{
            for (int j=0;j<6;j++){
                g.drawString(bs[i].toString(), game.getxLabel()[j], game.getyLabel()[j],game.getAngp1());//string bowl p1
                g.drawImage(bowlInactive, game.getxBowl()[j], game.getyBowl()[j], game.getSizeBowl(),game.getSizeBowl());
                i++;
            }
        }
        int yT1=game.getyTray()[0];
        if ((game.getAngp2()!=0)&&(!Game.getInstance().isTableMode())){
            yT1-=75;
        }
        g.drawImage(Assets.rotate(Assets.tray_1, (float)game.getAngp1()), game.getxTray()[0],game.getyTray()[0], 184,200);//t1
        g.drawString(gh.getP1().getName(),game.getxNames()[0],game.getyNames()[0], game.getAngp1());//name p1
        g.drawString(bs[i].toString(),game.getxTray()[0]+70, yT1+150, game.getAngp1());//string tray p1
        i++;
        if(gh.getActivePlayer().equals(gh.getP2())){
            for(UIButton ub:bp2) {
                g.drawString(bs[i].toString(),game.getxLabel()[i-1], game.getyLabel()[i-1], game.getAngp2());//string bowl p2
                ub.render(g);
                i++;
            }
          }else{
            for (int j=0;j<6;j++){
                g.drawString(bs[i].toString(),game.getxLabel()[j+6], game.getyLabel()[j+6], game.getAngp2());//string bowl p2
                g.drawImage(bowlInactive, game.getxBowl()[j+6], game.getyBowl()[j+6], game.getSizeBowl(),game.getSizeBowl());
                i++;
            }
        }
        int yT2=game.getyTray()[1];
        int xT2=game.getxTray()[1];
        if ((game.getAngp2()!=0)){
            yT2-=75;xT2+=20;
        }
        g.drawImage(Assets.rotate(Assets.tray_2, (float)game.getAngp2()), game.getxTray()[1],game.getyTray()[1],  184,200);//t2
        g.drawString(bs[i].toString(),xT2+70, yT2+150, game.getAngp2());//string tray p2
        g.drawString(gh.getP2().getName(),game.getxNames()[1], game.getyNames()[1], game.getAngp2());//name p2
        renderSeeds(g);
        renderBees(g);
        isRendered=true;
    };

    public void renderSeeds(Painter g){
        for(Seed s:Game.getInstance().getSeeds()){
            if((s.isVisible())&&(!s.inTray())) {
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
                seeds.add(new Seed(b.getId()-1,seed));
            }
        }
        for(Bowl b:bowlP2){
            for(int i=1;i<=b.getSeeds();i++){
                seeds.add(new Seed(b.getId(),seed));
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
        if(Game.getInstance().isPlayable()){
           /*
           TODO
            if(Game.getInstance().getAngleScreen()==180){
            scaledX=1920-scaledX;
            scaledY=1200-scaledY;}*/
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
           for(UIButton u:bp1) {
              if(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP1())){
                    u.onTouchDown(scaledX, scaledY);
               }
            }
            for(UIButton ub:bp2) {
                if(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP2())){
                    ub.onTouchDown(scaledX, scaledY);
                }
            }
            pauseBtn.onTouchDown(scaledX,scaledY);
        }
        if (e.getAction() == MotionEvent.ACTION_UP) {
            int i=0;
            for(UIButton ub:bp1) {
                i++;
                if ((ub.isPressed(scaledX, scaledY))&&(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP1()))&&(!Game.getInstance().getGh().getP1().getId().equals(1))) {
                    ub.cancel();
                    TurnRunner r = new TurnRunner(i);
                    Thread t = new Thread(r);
                    t.start();

                }
            }
            for(UIButton ub:bp2) {
                i++;
                if ((ub.isPressed(scaledX, scaledY))&&(Game.getInstance().getGh().getActivePlayer().equals(Game.getInstance().getGh().getP2()))&&(!Game.getInstance().getGh().getP1().getId().equals(1))) {
                    ub.cancel();
                    TurnRunner r = new TurnRunner(i);
                    Thread t = new Thread(r);
                    t.start();
                    }
            }

            if (pauseBtn.isPressed(scaledX,scaledY)){
                pauseBtn.cancel();
                Intent intent = new Intent(Game.getInstance().getGameActivity(), PauseActivity.class);
                Game.getInstance().getGameActivity().startActivity(intent);
            }
               for(UIButton u:bp1) {
                    u.cancel();
                 }
                for(UIButton ub:bp2) {
                    ub.cancel();
                }
        }
        return true;}else{
            return false;
        }
    }


    private void initializeButton(){
        Game game=Game.getInstance();
        bp1.clear();bp2.clear();
        for (int i=0;i<6;i++){
            bp1.add(new UIButton(game.getxBowl()[i],game.getyBowl()[i], game.getxBowl()[i]+game.getSizeBowl(), game.getyBowl()[i]+game.getSizeBowl(), bowlP1, bowlP1));
            bp2.add(new UIButton(game.getxBowl()[i+6],game.getyBowl()[i+6], game.getxBowl()[i+6]+game.getSizeBowl(), game.getyBowl()[i+6]+game.getSizeBowl(), bowlP2, bowlP2));
        }
    }

    private void redistributeSeeds(){
        List<Seed> sds=Game.getInstance().getSeeds();
        for(Seed seed:sds){
            seed.updatePosition(seed.getPosition());
        }
    }

}
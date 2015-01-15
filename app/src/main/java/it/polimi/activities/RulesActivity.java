package it.polimi.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.polimi.core.R;

import it.polimi.game.model.Game;

public class RulesActivity extends Activity {
    private ViewFlipper vf;
    private Button gotIt;
    private float lastX;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rules);
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        gotIt=(Button) findViewById(R.id.btn_got);
        Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/ahronbd.ttf");
        gotIt.setTypeface(type);}
        catch(Exception e){
            this.finish();
        }
    }

    public void gotIt(View v){
        this.finish();
    }

    public boolean onTouchEvent(MotionEvent touchevent) {

        switch (touchevent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                if (lastX < currentX)
                {
                    if (vf.getDisplayedChild()==0)
                        break;

                    vf.setInAnimation(this, R.anim.slide_in_from_left);
                    vf.setOutAnimation(this, R.anim.slide_out_to_right);
                    vf.showPrevious();
                }

                if (lastX > currentX)
                {
                    if (vf.getDisplayedChild()==vf.getChildCount()-1)
                        break;

                    vf.setInAnimation(this, R.anim.slide_in_from_right);
                    vf.setOutAnimation(this, R.anim.slide_out_to_left);
                    vf.showNext();
                }

                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                float tempX = touchevent.getX();
                int scrollX = (int) (tempX - lastX);
                break;
            }

        }

        return false;

    }

}

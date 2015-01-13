package it.polimi.activities;

import android.app.Activity;
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
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rules);
        vf = (ViewFlipper) findViewById(R.id.viewFlipper);
        gotIt=(Button) findViewById(R.id.btn_got);
        Typeface type = Typeface.createFromAsset(this.getAssets(),"fonts/ahronbd.ttf");
        gotIt.setTypeface(type);
    }

    public void gotIt(View v){
        this.finish();
    }

    /*
    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();

                // Handling left to right screen swap.
                if (lastX < currentX) {

                    // If there aren't any other children, just break.
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // Next screen comes in from left.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
                    // Current screen goes out from right.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

                    // Display next screen.
                    viewFlipper.showNext();
                }

                // Handling right to left screen swap.
                if (lastX > currentX) {

                    // If there is a child (to the left), kust break.
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;

                    // Next screen comes in from right.
                    viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
                    // Current screen goes out from left.
                    viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

                    // Display previous screen.
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }*/

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
//                    vf.showNext();
                    vf.showPrevious();
                }

                if (lastX > currentX)
                {
//                    if (vf.getDisplayedChild()==1)
                    if (vf.getDisplayedChild()==vf.getChildCount()-1)
                        break;

                    vf.setInAnimation(this, R.anim.slide_in_from_right);
                    vf.setOutAnimation(this, R.anim.slide_out_to_left);
//                    vf.showPrevious();
                    vf.showNext();
                }

                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                float tempX = touchevent.getX();
                int scrollX = (int) (tempX - lastX);

                //vf.scrollBy(scrollX, 0);

                break;
            }

        }

        return false;

    }

}

package com.adafruit.bluefruit.le.connect.app;

import android.app.*;
import android.app.AlertDialog.*;
import android.content.*;
import com.adafruit.bluefruit.le.connect.R;
import android.os.*;
import android.view.*;

public class StarWarsMainActivity extends Activity {

	static MyGameView mGameView;
	boolean isPaused = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        
    	mGameView = (MyGameView) findViewById(R.id.mGameView); 
    }
    
	//-------------------------------------
	//  Option Menu
	//-------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Quit Game");
		menu.add(0, 2, 0, "Pause Game");
		menu.add(0, 3, 0, "Music On");
		menu.add(0, 4, 0, "Sound On");
		menu.add(0, 5, 0, "Vibrator Off");
		return true;
	}

	//-------------------------------------
	//  onOptions ItemSelected
	//-------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case 1:  		// QuitGame
        	MyGameView.StopGame();
			startActivity(new Intent(StarWarsMainActivity.this, StartGame.class));
        	finish();
             break;
        case 2:   		// PauseGame
        	isPaused = !isPaused;
        	if (isPaused) {
        		MyGameView.PauseGame();
        		item.setTitle("Resume Game");
        	} else {
        		MyGameView.ResumeGame();
        		item.setTitle("Pause Game");
        	}	
             break;
        case 3:   		// Music On/off
        	MyGameView.isMusic = !MyGameView.isMusic;
        	if (MyGameView.isMusic) {
        		MyGameView.player.stop();
        		item.setTitle("Music Off");
        	}
        	else {
        		MyGameView.player.start();
        		item.setTitle("Music On");
        	}
            break;
        case 4:    		// Sound On/Off
        	MyGameView.isSound = !MyGameView.isSound;
        	if (MyGameView.isSound) {
        		item.setTitle("Sound Off");
        	}
        	else {
        		item.setTitle("Sound On");
        	}
            break;
        case 5:    		// Vibrator On/Off
        	MyGameView.isVibe = !MyGameView.isVibe;
        	if (MyGameView.isVibe) {
        		item.setTitle("Vibrator Off");
        	}
        	else {
        		item.setTitle("Vibrator On");
        	}
        }
        return true;
	}
	
	public void onBackPressed() {

		// TODO Auto-generated method stub

		// super.onBackPressed(); //지워야 실행됨




		Builder d = new AlertDialog.Builder(this);

		d.setMessage("정말 종료하시겠습니까?");

		d.setPositiveButton("예", new DialogInterface.OnClickListener() {


 

			public void onClick(DialogInterface dialog, int which) {

				// process전체 종료

				finish();

			}

		});

		d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}

		});

		d.show();
	} 

}


package com.adafruit.bluefruit.le.connect.app;

import android.app.*;
import android.app.AlertDialog.*;
import android.content.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.adafruit.bluefruit.le.connect.R;

public class StartGame extends Activity {
	
	MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.startgame);
       
        mPlayer = MediaPlayer.create(this, R.raw.rondo);   	// ���� �б� green�� ���ϸ�
  	  	mPlayer.setVolume(0.7f, 0.7f); 			      		// ���� ����
  	  	mPlayer.setLooping(true);              				// �ݺ� ����
 	  	mPlayer.start();   
 	  	
        findViewById(R.id.imgStart).setOnClickListener(OnMyClick);
        findViewById(R.id.imgQuit).setOnClickListener(OnMyClick);
        findViewById(R.id.imgOpts).setOnClickListener(OnMyClick);
        findViewById(R.id.imgAbout).setOnClickListener(OnMyClick);
    }

    //---------------------------------
    // OnClick Listener
    //---------------------------------
    Button.OnClickListener OnMyClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgStart:
				startActivity(new Intent(StartGame.this, StarWarsMainActivity.class));
		  	  	mPlayer.stop();
		  	  	finish();
				break;
			case R.id.imgQuit:
		  	  	mPlayer.stop();
				finish();
				break;
			case R.id.imgOpts:
				startActivity(new Intent(StartGame.this, Options.class));
				break;
			
			
			case R.id.imgAbout:
				startActivity(new Intent(StartGame.this, About.class));
			}
		}
    };
    
    public void onBackPressed() {

		// TODO Auto-generated method stub

		// super.onBackPressed(); //지워야 실행됨




		Builder d = new AlertDialog.Builder(this);

		d.setMessage("정말 종료하시겠습니까?");

		d.setPositiveButton("예", new DialogInterface.OnClickListener() {

   
			public void onClick(DialogInterface dialog, int which) {

				// process전체 종료

				finish();
                mPlayer.pause();
			}

		});

		d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				dialog.cancel();

			}

		});

		d.show();
	} 
    
    protected void onUserLeaveHint() {
 	   
 	  mPlayer.start();
 	   super.onUserLeaveHint();
 	  }
}


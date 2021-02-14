package com.adafruit.bluefruit.le.connect.app;

import java.util.*;
import com.adafruit.bluefruit.le.connect.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.*;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

public class MyGameView extends SurfaceView implements Callback, SensorEventListener{
	
	SensorManager sensorManager;
	String m_str = "";
	int x;
	int y;
	MediaPlayer mPlayer;
 
	long LastRegenEnemy = System.currentTimeMillis();
	Random randEnem = new Random();
	// ����׿� ���� ���
	boolean DEBUG = false;						// ����� ��

	// ���α׷� ���¿� ���� ���	
	final static int PROCESS = 1;				// ���� ��
	final static int STAGE_CLEAR = 2;			// Stage Clear
	final static int GAMEOVER = 3;				// Game Over
	final static int ALL_CLEAR = 4;				// All Clear
	
	// ��ü �������� ���� ���� ���� �� ��� 
	final static int MAX_STAGE = 6;				// ��ü �������� ��
	final static int BOSS_COUNT = 3;			// Boss ���� ��

	// ���� ���̵� - ���� �޴���
	final static int EASY = 0;					// ���� ���̵�
	final static int MEDIUM = 1;
	final static int HARD = 2;
	static int difficult = EASY;				// ���� ���̵�
	
	// SurfaceView�� ������
	static GameThread mThread;					// GameThread
	static SurfaceHolder mHolder;				// SurfaceHolder 
	static Context mContext;					// Context 

	// ���� �޴��� ������
	static boolean isMusic = false;				// ��� ����
	static boolean isSound = false;				// ȿ����
	static boolean isVibe = false;				// ����
	
	// Class ������
	static MapTable mMap;						// �� ���̺�
	static GunShip mShip;						// �Ʊ� ���ּ�
	static EnemyBoss mBoss;						// ���� Boss
	static ArrayList<People> mPeople;
	static ArrayList<Item> mItem;
	static ArrayList<Missile> mMissile;			// ���� �̻��� 
	static ArrayList<Child> mChild;
	static ArrayList<Wreck> mWreck;
	static ArrayList<FireGun> mGun; 			// �Ʊ� �̻���  
	static ArrayList<Explosion> mExp;			// ���� �Ҳ� 
	static ArrayList<Bonus> mBonus;	 			// Bonus
	static ArrayList<BossMissile> mBsMissile;	// Boss Missile 
	static Sprite mEnemy[][] = new Sprite[6][8];// ����
	static AttackEnemy mAttack;					// ���� ���� Class 
	Collision mCollision;						// �浹 ���� Class
	static GameOver mGameOver; 					// ���ӿ���&All Clear Class
	StageClear mClear;							// Stage Clear Class 

	// Game�� ������
	static int Width, Height;					// View
	static int stageNum;	  					// �������� ��ȣ 
	static int shipCnt = 3;						// ���� ���ּ� ��
	static int score = 0;						// ����
	static int gunDelay = 15;					// �̻��� �߻� ���� �ð�
    static int people = 0;
	
	// Game ���࿡ ����  flag ������
	static boolean isPower = false;				// ��ȭ�� �̻���
	static boolean isDouble = false;				// �̻��� 2���� �߻�
	static boolean isAutoFire = false;			// �̻��� �ڵ� �߻�
	static boolean isBoss = false;				// ���� ����
	static int status = PROCESS;				// ���� ���� ����

	// ��Ʈ�ʿ� ���� ������
	static Bitmap imgBack;						// ��� �̹���
	static int sw[] = new int[6];				// ������ ���� ���� 
	static int sh[] = new int[6];
	Bitmap imgMiniShip;							// ���� ���ּ� ��
	
	// ���� ���� ������
	static SoundPool sdPool;
	static int sdFire, sdExp0, sdExp1, sdExp2, sdExp3, sdExp4;
	static Vibrator vibe;
	static MediaPlayer player;
        
	boolean canRun = true;			// Thread �����
	boolean isWait = false;	
	
	int width, height;
	//-------------------------------------
	//  ������
	//-------------------------------------
	

	

	public MyGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		
		mHolder = holder;		// holder�� Context ����
		mContext = context;
		mThread = new GameThread(holder, context);
		
		InitGame();				// ���� �ʱ�ȭ
		MakeStage();			// �������� �����
		setFocusable(true);		// View�� Focus�ޱ�
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
		
	}
	
	//-------------------------------------
    //  InitGame
    //-------------------------------------
	private void InitGame() {
		// ȭ�� �ػ� ���ϱ�
		mThread = new GameThread(mHolder, mContext); 
		Display display = ((WindowManager) mContext.getSystemService (Context.WINDOW_SERVICE)).getDefaultDisplay();
		Width = display.getWidth();			
	    Height = display.getHeight();
		width = display.getWidth();
		height = display.getHeight() - 50;
	    
		// Class ���� �ʱ�ȭ		
		mMap = new MapTable();						// �� ���̺�
		mAttack = new AttackEnemy();				// ���� ���� Class
		mMissile = new ArrayList<Missile>();		// ���� �̻���	
		mPeople = new ArrayList<People>();
		mItem = new ArrayList<Item>();
	    mChild = new ArrayList<Child>();
		mWreck = new ArrayList<Wreck>();
	    
		mGun = new ArrayList<FireGun>();			// �Ʊ� �̻���
		mBsMissile = new ArrayList<BossMissile>();	// Boss Missile
		mExp = new ArrayList<Explosion>();			// ���� �Ҳ�
		mBonus = new ArrayList<Bonus>();			// Bonus
		mCollision = new Collision();				// �浹 ���� Class
		
		mClear = new StageClear();					// �������� �� ó���� 
		mGameOver = new GameOver();					// ���� ���� ó����
		mBoss = new EnemyBoss();					// Boss

		// �Ʊ��� ������ ���� ����
		mShip = new GunShip(Width / 2, Height - 60, null);// ���ּ�	
		shipCnt = 3;								// ���� ���ּ� ��
		stageNum = 1;  								// �������� ��ȣ 
		status = PROCESS;							// ���� ���� ����
		
		// ���� ĳ���� �迭
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
					mEnemy[i][j] = new Sprite();
				    
			}
		}
		
		// Score ǥ�ÿ� Minimap
		imgMiniShip = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.miniship);
		
		// Options Menu ������ �б�
		difficult = ((GlobalVars) mContext.getApplicationContext()).getDifficult();
		isMusic = ((GlobalVars) mContext.getApplicationContext()).getIsMusic();
		isSound = ((GlobalVars) mContext.getApplicationContext()).getIsSound();
		isVibe = ((GlobalVars) mContext.getApplicationContext()).getIsVibe();
		
		// ȿ����
		sdPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    sdFire = sdPool.load(mContext, R.raw.fire, 1);
	    sdExp0 = sdPool.load(mContext, R.raw.exp0, 2);
	    sdExp1 = sdPool.load(mContext, R.raw.exp1, 3);
	    sdExp2 = sdPool.load(mContext, R.raw.exp2, 4);
	    sdExp3 = sdPool.load(mContext, R.raw.exp3, 5);
	    sdExp4 = sdPool.load(mContext, R.raw.exp4, 6);
	    // ����
	    vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

	    // ��� ����
  	    player = MediaPlayer.create(mContext, R.raw.green);   	// ���� �б� green�� ���ϸ�
	    player.setVolume(0.7f, 0.7f); 			      			// ���� ����
	    player.setLooping(true);              					// �ݺ� ����
	    if (isMusic) player.stop();
	    player.start();
	    
	} 
	
	//-------------------------------------
    //  MakeStage
    //-------------------------------------
	public static void MakeStage() {
		mMap.ReadMap(stageNum);
		imgBack = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ocean_1);
		imgBack = Bitmap.createScaledBitmap(imgBack, Width, Height, true);

		// �迭�� ���� ĳ���� �����
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j].MakeSprite(i, j);
			}
			sw[i] = mEnemy[i][2].w;	
			sh[i] = mEnemy[i][2].h;
		}

		mShip.y = Height - 36;				// �Ʊ��� ��ġ
		mAttack.ResetAttack();				// ���� ���ݽð� �ʱ�ȭ
	}
	
	//-------------------------------------
    //  Make Boss Stage
    //-------------------------------------
	public static void MakeBossStage() {
		// ���� ĳ���� ����
		for (int i = 2; i <= 4; i++) {
			for (int j = 0; j < 8; j++) {
				mEnemy[i][j].ResetSprite();
			}
		}

		mMap.enemyCnt = 24;			// �� ī����
		mBoss.InitBoss();			// Boss �ʱ�ȭ
		isBoss = true;				// Thread�� ����
		status = PROCESS;			// Thread�� ����
		mShip.y = Height - 36;		// �Ʊ��� ��ġ
		mAttack.ResetAttack();		// ���� ���� �ð� �ʱ�ȭ
	}
	
	//-------------------------------------
    //  QuitGame - ���ӳ����� StartGame���� ����
    //-------------------------------------
	public static void GameOver() {
		StopGame();				// Thread ����
		// StartGame Activity ����
		mContext.startActivity(new Intent(mContext, StartGame.class));
		// �ڽ�(MainActuivity)�� ����
		((Activity) mContext).finish(); 		
	}
	
	//-------------------------------------
    //  SurfaceView�� ������ �� ����Ǵ� �κ�
    //-------------------------------------
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mThread.start();
		} catch (Exception e) {
			RestartGame();
			if (isMusic) player.start();
		}
	}

	//-------------------------------------
    //  SurfaceView�� �ٲ� �� ����Ǵ� �κ�
    //-------------------------------------
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int width, int Height) {

	}

	//-------------------------------------
    //  SurfaceView�� ������ �� ����Ǵ� �κ�
    //-------------------------------------
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		StopGame();
		player.stop();
	}
	
	//-------------------------------------
	//  ������ ���� ����
	//-------------------------------------
	public static void StopGame() {
		mThread.StopThread(); 
	}

	//-------------------------------------
	//  ������ �Ͻ� ����
	//-------------------------------------
	public static void PauseGame() {
		mThread.PauseNResume(true); 
	}

	//-------------------------------------
	//  ������ ��⵿
	//-------------------------------------
	public static void ResumeGame() {
		mThread.PauseNResume(false); 
	}

	//-------------------------------------
	//  ���� �ʱ�ȭ
	//-------------------------------------
	public void RestartGame() {
		mThread.StopThread();		// ������ ����

		// ������ �����带 ���� �ٽ� ����
	    mThread = null;	  
		mThread = new GameThread(mHolder, mContext); 
		mThread.start(); 
	}

//----------------------------------------------------------------
	
	//-------------------------------------
	//  GameThread Class
	//-------------------------------------
	class GameThread extends Thread {		// Thread �����
		
		int loop;						// ���� ī���� - �Ʊ� �̻��� �߻� ���� �����
		Paint paint = new Paint();		// Score ǥ�ÿ� 
		
		//-------------------------------------
		//  ������ 
		//-------------------------------------
		public GameThread(SurfaceHolder holder, Context context) {
			// Score ǥ�ÿ� 
			paint.setColor(Color.WHITE);
			paint.setAntiAlias(true);
			paint.setTextSize(20);
			paint.setTypeface(Typeface.create("", Typeface.BOLD));
		}
		
		//-------------------------------------
		//  �浹 ����
		//-------------------------------------
		public void CheckCollision() {
			mCollision.CheckCollision();
		}
		
		//-------------------------------------
		//  ���� ����
		//-------------------------------------
		public void AttackSprite() {
			mAttack.Attack();
		}
		
		//-------------------------------------
		//  �Ʊ� �̻��� �߻�
		//-------------------------------------
		public void FireGunship() {
			
		}
		
		//-------------------------------------
		//  Move All
		//-------------------------------------
		public void MoveAll() {
			loop++;
			// Boss Mode�ΰ�?
			if (isBoss) {
				mBoss.Move();
				// Boss Missile
				for (int i = mBsMissile.size() - 1; i >= 0; i--)
					if (mBsMissile.get(i).Move())
						mBsMissile.remove(i);
			}
			// ����
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < 8; j++)
					mEnemy[i][j].Move();		// 
			}
			// ���� �̻���
			for (int i = mMissile.size() - 1; i >= 0; i--) {
				if (mMissile.get(i).Move())
					mMissile.remove(i);
			}
			
			// �Ʊ� �̻��� 
			for (int i = mGun.size() - 1; i >= 0; i--) {
				if (mGun.get(i).Move())
					mGun.remove(i);
			}
			// ���ʽ� 
			for (int i = mBonus.size() - 1; i >= 0; i--) {
				if (mBonus.get(i).Move())
					mBonus.remove(i);
			}	
			// ���� �Ҳ� 
			for (int i = mExp.size() - 1; i >= 0; i--) {
				if (mExp.get(i).Explode())
					mExp.remove(i);
			}
			// �Ʊ���
			if (!mShip.isDead)
				mShip.Move();
		

		for(int i = mPeople.size() - 1; i >= 0; i--) {
		    mPeople.get(i).MovePeople();
		    if(mPeople.get(i).dead == true) mPeople.remove(i);
		}
		
		for(int i = mChild.size() - 1; i >= 0; i--) {
		    mChild.get(i).MovePeople2();
		    if(mChild.get(i).dead == true) mChild.remove(i);
		}
		for(int i = mWreck.size() - 1; i >= 0; i--) {
		    mWreck.get(i).MoveWreck();
		    if(mWreck.get(i).dead == true) mWreck.remove(i);
		}
		for(int i = mItem.size() - 1; i >= 0; i--) {
		    mItem.get(i).MoveItem();
		    if(mItem.get(i).dead == true) mItem.remove(i);
		}
		}
		//-------------------------------------
		//  ���� ǥ��
		//-------------------------------------
		public void DrawScore(Canvas canvas) {
			int x, x1, x2, y = 30;
			x1 = 134;							// HP ��ġ
			x2 = x1 + mShip.shield * 8 + 4;		// undead ��ġ
			x = mShip.undeadCnt / 2;

			for (int i = 0; i < shipCnt; i++)
				canvas.drawBitmap(imgMiniShip, i * 20 + 10, y - 15, null);
			
			// HP
			canvas.drawText("HP", 100, y, paint); 
			paint.setColor(0xFF00A0F0);
			for (int i = 0; i < mShip.shield; i++)
				canvas.drawRect(i * 8 + x1, y - 10, i * 8 + x1 + 6 , y - 4, paint);
			
			// undead
			paint.setColor(Color.RED);
			canvas.drawRect(x2, y - 10, x2 + x, y - 4, paint);

			// Score
			paint.setColor(Color.BLACK);
			canvas.drawText("Score " + score, 220, y, paint); 
			canvas.drawText("Stage " + stageNum, 440, y, paint); 
			canvas.drawText("People" + people, 680, y, paint);
			
			paint.setTextSize(40);
		}
		
		//-------------------------------------
		//  DrawAll
		//-------------------------------------
		public void DrawAll(Canvas canvas) {
			// ���ȭ��
			canvas.drawBitmap(imgBack, 0, 0, null);	
			
			// ����
			
			// Boss Mode�ΰ�?
			if (isBoss) {
				// Boss Missile
				for (BossMissile tmp : mBsMissile)
					canvas.drawBitmap(tmp.imgMissile, tmp.x - tmp.w, tmp.y - tmp.h, null);
				// Boss
				canvas.drawBitmap(mBoss.imgBoss, mBoss.x - mBoss.w, mBoss.y - mBoss.h, null);
			}
			// ���� �̻��� 
			
			// �Ʊ� �̻���
			for (FireGun tmp : mGun)
				canvas.drawBitmap(tmp.imgGun, tmp.x - tmp.w, tmp.y - tmp.h, null);
			// ���ʽ�
			for (Bonus tmp : mBonus) 
					canvas.drawBitmap(tmp.imgBonus, tmp.x - tmp.w, tmp.y - tmp.h, null);
			// �Ʊ���
			if (!mShip.isDead)
					canvas.drawBitmap(mShip.imgShip, mShip.x - mShip.w, mShip.y - mShip.h, null);  
			// ���� �Ҳ�
			for (Explosion tmp : mExp)
					canvas.drawBitmap(tmp.imgExp, tmp.x - tmp.w, tmp.y - tmp.h, null);
			for(People tmp : mPeople)
				canvas.drawBitmap(tmp.imgBall, tmp.x - tmp.rad, tmp.y - tmp.rad, null);
			for(Child tmp : mChild)
				canvas.drawBitmap(tmp.imgBall2, tmp.x - tmp.rad2, tmp.y - tmp.rad2, null);
			for(Item tmp : mItem)
				canvas.drawBitmap(tmp.imgBall4, tmp.x - tmp.rad4, tmp.y - tmp.rad4, null);
			// Score
			for(Wreck tmp : mWreck)
				canvas.drawBitmap(tmp.imgBall3, tmp.x - tmp.rad3 , tmp.y - tmp.rad3, null);
			DrawScore(canvas);
			
		}
		
		//-------------------------------------
		//  ������ ��ü
		//-------------------------------------
		public void run() {
			Canvas canvas = null; 				
			while (canRun) {
				canvas = mHolder.lockCanvas();	
				try {
					synchronized (mHolder) {	
						switch (status) {
						case PROCESS :
							if (isAutoFire) FireGunship();
							CheckCollision();			// �浹 ����
							MoveAll();					// ��� ĳ���� �̵�
							AttackSprite();				// ���� ����
							DrawAll(canvas);			// Canvas�� �׸���
							MakePeople();
							MakePeople2();
							MakeItem();
							MakeWreck();
							break;
						case STAGE_CLEAR:
							mClear.SetClear(canvas);
							break;
						case ALL_CLEAR:
						case GAMEOVER:
							mGameOver.SetOver(canvas);
							break;
						}
					} // sync
				} finally {						 
					if (canvas != null)			
						mHolder.unlockCanvasAndPost(canvas);
				} // try

				// ������ �Ͻ� ���� 
				synchronized (this) {
            		if (isWait)				// Pause ����̸�
            			try {
            				wait();			// ������ ���
            			} catch (Exception e) {
							// nothing
						}
    			} // sync
				
			} // while
		} // run
	
		//-------------------------------------
		//  ������ ���� ����
		//-------------------------------------
		public void StopThread() {
			canRun = false;
        	synchronized (this) {
        		this.notify();
			}
		}
		
		//-------------------------------------
		//  ������ �Ͻ����� / ��⵿
		//-------------------------------------
		public void PauseNResume(boolean wait) { 
			isWait = wait;
        	synchronized (this) {
        		this.notify();
			}
		}
	} // GameThread ��
	
	//-------------------------------------
	//  onTouch Event
	//-------------------------------------
	
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_DOWN)
			return true;
		synchronized (mHolder) {
			int x = (int) event.getX();
			int y = (int) event.getY();

			// GameOver�� Y/N ���� 
			if (status == GAMEOVER || status == ALL_CLEAR) {
				return mGameOver.TouchEvent(x, y);
			}

			if (!mShip.isDead) { 
				mShip.dir = 0;
				// �Ʊ��� Touch�̸� �̻��� �߻�
				if (Math.abs(x - mShip.x) < mShip.w * 2 &&
						Math.abs(y - mShip.y) < mShip.h * 2) {
					mThread.FireGunship();
				} else if (x < mShip.x - mShip.w) {
					mShip.dir = 1;
				} else if (x > mShip.x + mShip.w) {
					mShip.dir = 2;
				}
			} // if
			if (DEBUG)		// ����� ��忡�� �� ���� �ľ�
				CheckEnemyStatus(x, y);
		} // sync
		return true;
	}
	//-------------------------------------
	//  onKeyDown
	//-------------------------------------
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mShip.isDead) return false;
		synchronized (mHolder) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_LEFT :
				mShip.dir = 1;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT :
				mShip.dir = 2;
				break;
			case KeyEvent.KEYCODE_DPAD_UP:
				mThread.FireGunship();
				break;
			default:	
				mShip.dir = 0;
			}
		}
		return false;
	}
	
	//-------------------------------------
	//   ���� ���� �ľ� - DEBUG ��忡�� ȣ���
	//-------------------------------------
	private void CheckEnemyStatus(int x, int y) {
		int x1, y1, w;
		
		for (int i = 0; i < 6; i++ ) {
			for (int j = 0; j < 8; j++ ) {
				if (mEnemy[i][j].isDead) continue;
				x1 = mEnemy[i][j].x;
				y1 = mEnemy[i][j].y;
				w = mEnemy[i][j].w;
				if (Math.abs(x - x1) < w &&	Math.abs(y - y1) < w) {
					Log.v("Sprite", "i=" + i + ", j=" + j + "  " + mEnemy[i][j].status);
					return;
				}
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized(mHolder) {
		
		
			switch(event.sensor.getType()){
			case Sensor.TYPE_ORIENTATION:
				float Heading = event.values[0];
				float Pitch = event.values[1];
				float Roll = event.values[2];
				
				
				m_str = "Orientation :";
				m_str += "Heading :"+Float.toString(Heading);
				m_str += "Pitch :"+Float.toString(Pitch);
				m_str += "Roll :"+Float.toString(Roll);
			
			   
			    mShip.x -= Roll;
			    mShip.y -= Pitch;
			    
			    if(mShip.x <= 0)
			    	mShip.x = 0;
			    if(mShip.y <= 0)
			    	mShip.y = 0;
			    if(mShip.x >= getWidth()-0)
			    	mShip.x = getWidth();
			    if(mShip.y >= getHeight()-0)
				    	mShip.y = getHeight();
			
			
			}
		
		
		invalidate();
	
		 CheckEnemyStatus(x,y);
} // SurfaceView 
	
}
	
	public void MakePeople() {
		Random rnd = new Random();
		if(mPeople.size() > 9 || rnd.nextInt(40) < 38) return;
		int x = -50;
	    int y = rnd.nextInt(201) + 50;	
	    mPeople.add(new People(mContext, x, y, width, height));
	 
	}
	
	public void MakePeople2() {
		Random rnd = new Random();
		if(mChild.size() > 9 || rnd.nextInt(40) < 38) return;
		int x = -50;
	    int y = rnd.nextInt(201) + 50;	
	    mChild.add(new Child(mContext, x, y, width, height));
	 
	}
	
	public void MakeItem() {
		Random rnd = new Random();
		if(mItem.size() > 9 || rnd.nextInt(40) < 38) return;
		int x = -50;
	    int y = rnd.nextInt(201) + 50;	
	    mPeople.add(new People(mContext, x, y, width, height));
	 
	}
	public void MakeWreck() {
		Random rnd = new Random();
		if(mChild.size() > 9 || rnd.nextInt(40) < 38) return;
		int x = -50;
	    int y = rnd.nextInt(201) + 50;	
	    mWreck.add(new Wreck(mContext, x, y, width, height));
	 
	}

	
}


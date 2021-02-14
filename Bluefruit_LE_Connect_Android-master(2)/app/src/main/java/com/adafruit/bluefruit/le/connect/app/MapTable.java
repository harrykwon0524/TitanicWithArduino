package com.adafruit.bluefruit.le.connect.app;

import java.io.IOException;
import java.io.InputStream;
import com.adafruit.bluefruit.le.connect.R;
import android.graphics.*;

//--------------------------------------
// Map Table
//--------------------------------------
public class MapTable {
	
	// 16 ���⿡ ���� �̵� �Ÿ� �ﰢ�Լ�ǥ
	public float sx[] = {0f, 0.39f, 0.75f, 0.93f, 1, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f, -1f, -0.93f, -0.75f, -0.39f};
	public float sy[] = {-1f, -0.93f, -0.75f, -0.39f, 0f, 0.39f, 0.75f, 0.93f, 1f, 0.93f, 0.75f, 0.39f, 0f, -0.39f, -0.75f, -0.93f}; 

	public int syncCnt = 0;		// ��ũ ����
	public int dirLen = 52;		// ���� ���� ���� �� ó������ �̵��� �Ÿ� 
	public int dir = 4;	  		// ���� ����
	public int dirCnt = 0;		// ���� �δ밡 �̵��� �Ÿ�	
	
	private Path mPath;			// Path
	private Selection mSelect;	// Selection
	private DelayTime mDelay;	// Delay
	private Position mPos;		// Pisition
	private Shield mShield;		// Shield
	protected int		m_x;
	protected int		m_y;
	public int enemyCnt;		// �������������� ������ ������ ��
	public int attackTime;		// ���� ���� �ð�
	
	static final float SCROLL_SPEED = 0.1f;
	private float m_scroll = -2000 + 480;
	
	Bitmap m_layer2;
	
	static final float SCROLL_SPEED_2 = 0.2f;
	private float m_scroll_2 = -2000 + 480;
	
	//--------------------------------------
	// Constructor
	//--------------------------------------
	public MapTable() {
		// ������ ���� ����
		m_layer2 = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.wave_noback1);
		SetPosition(0,(int)m_scroll);
	}
	
	public void SetPosition(int x,int y){
		m_x = x;
		m_y = y;

	}
	//--------------------------------------
	//  Read File
	//--------------------------------------
	public void ReadMap(int num) {
		num--;
		// ���������� �ش��ϴ� �� ���� �б�
		InputStream fi = MyGameView.mContext.getResources().openRawResource(R.raw.stage01 + num);
		try {
			byte[] data = new byte[fi.available()];
			fi.read(data);
			fi.close();
			String s = new String(data, "EUC-KR");	// ���� ���ڵ�
			MakeMap(s);								// ���ڵ��� ���ڿ��� �м��Ϸ� ������
		} catch (IOException e) {
			// 
		}	
	}
	
	//--------------------------------------
	//  Make Map
	//--------------------------------------
	public void MakeMap(String str) {
		int n1 = str.indexOf("selection");					// selection ������ ���� ��ġ  
		mPath = new Path(str.substring(0, n1));				// path
		
		int n2 = str.indexOf("delay");
		mSelect = new Selection(str.substring(n1, n2));		// Selection
		enemyCnt = mSelect.GetEnemyCount();					// ������ ��
		
		n1 = str.indexOf("position");  
		mDelay = new DelayTime(str.substring(n2, n1));		// Delay
		attackTime = mDelay.GetDelay(0, 5);					// ������ ĳ���� 
		
		n2 = str.indexOf("shield");  
		mPos = new Position(str.substring(n1, n2));			// Position

		mShield = new Shield(str.substring(n2));			// Shield
	}
	
	//--------------------------------------
	//  Get Path
	//--------------------------------------
	public SinglePath GetPath(int num) {
		return mPath.GetPath(num);
	}
	
	//--------------------------------------
	//  Get Delay
	//--------------------------------------
	public int GetDelay(int kind, int num) {
		return mDelay.GetDelay(kind, num);
	}
	
	//--------------------------------------
	//  Get Selection
	//--------------------------------------
	public int GetSelection(int kind, int num) {
		return mSelect.GetSelection(kind, num);
	}
	
	//--------------------------------------
	//  Get X position
	//--------------------------------------
	public int GetPosX(int kind, int num) {
		return mPos.GetPosX(kind, num);
	}
	
	//--------------------------------------
	//  Get Y position
	//--------------------------------------
	public int GetPosY(int kind, int num) {
		return mPos.GetPosY(kind, num);
	}
	
	//--------------------------------------
	//  Get Enemy Num
	//--------------------------------------
	public int GetEnemyNum(int kind, int num) {
		return mPos.GetEnemyNum(kind, num);
	}
	
	//--------------------------------------
	//  Get Shield
	//--------------------------------------
	public int GetShield(int kind, int num) {
		return mShield.GetShield(kind, num);
	}
    
	void Update(long GameTime) {
		m_scroll = m_scroll + SCROLL_SPEED;
		if(m_scroll >= 0)
			m_scroll = 0;
		SetPosition(0,(int)m_scroll); 
		m_scroll_2 = m_scroll_2 + SCROLL_SPEED_2;
		if(m_scroll_2 >= 0)
			m_scroll_2 = 0;
	
	}
	public void Draw(Canvas canvas){
		
	
	canvas.drawBitmap(m_layer2,m_x,m_scroll_2,null);	
	
	}
 }

package com.adafruit.bluefruit.le.connect.app;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.hardware.*;
import com.adafruit.bluefruit.le.connect.R;

public class GunShip {
	public int x, y;				// ��ġ
	public int w, h;				// ���� ����
	public int shield;				// ��ȣ��
	public int dir;					// �̵� ����(1:����, 2:������, 3, ����, 0:����)
	public boolean isDead;			// ���
	public boolean undead;			// ���� ���
	public int undeadCnt;			// ���� ���� �ð�
	public Bitmap imgShip;			// ���ּ� �̹���

	private Bitmap imgTemp[] = new Bitmap[8];

	private int sx[] = {0, -8, 8, 0};
	private int sy[] = {0, 0, 0, -8};
	               
	private int imgNum = 0;				// �̹��� ��ȣ

	SensorManager sensorManager;
	String m_str = "";
	int m_x = 0;
	int m_y = 0;
	
	//--------------------------------
	// ������
	//--------------------------------
	public GunShip(int x, int y, Context context) {
		this.x = x;
		this.y = y;
		for (int i = 0; i < 8; i++)
			imgTemp[i] =BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.boat_2);
		
	
		w = imgTemp[0].getWidth() / 2;
		h = imgTemp[0].getHeight() / 2;
		
		ResetShip();
		
	}
	
	//--------------------------------
	// �ǽ� �ʱ�ȭ
	//--------------------------------
	public void ResetShip() {
		x = MyGameView.Width / 2;
		y = MyGameView.Height - 36;
		shield = 3;
		isDead = false;
		undeadCnt = 50;			// ���� �ð�
		undead = true;
		dir = 0;
		imgShip = imgTemp[0];
	}
	
	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		imgNum++;
		if (imgNum > 3) imgNum = 0;
		
		// ���ּ� ���
		if (undead) {
			imgShip = imgTemp[imgNum + 4];	
			undeadCnt--;
			if (undeadCnt < 0) undead = false;
		} else {
			imgShip = imgTemp[imgNum];		
		}	

		// ���ּ� �̵�
		x += sx[dir];
		y += sy[dir];
		
		if (x < w) {						// View�� ���� ��
			x = w;
			dir = 0;
		} else if (x > MyGameView.Width - w) {
			x = MyGameView.Width - w;
			dir = 0;
		}
		return (y < -32);		// Stage Clear��
	}

}

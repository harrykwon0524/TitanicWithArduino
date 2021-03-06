package com.adafruit.bluefruit.le.connect.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.adafruit.bluefruit.le.connect.R;

//--------------------------------
// ���� �̻���
//--------------------------------
public class Missile {
	public int x, y, dir;		// ��ǥ, �߻� ����
	public boolean isDead;		// ���
	public Bitmap imgMissile;	// �̻��� �̹���
	public Bitmap imgMissile2;
	
	private float sx, sy;		// �̻��� �̵� �ӵ�
	
	//--------------------------------
	// ������
	//--------------------------------
	public Missile(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		imgMissile = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.people_noback);
		imgMissile2 = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.child_noback);
		sx = MyGameView.mMap.sx[dir];	// ���⿡ ���� �̵� �ӵ� ���
		sy = MyGameView.mMap.sy[dir];
		Move();
	}

	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		x += (int) (sx * 10);		// �̻��� �̵�
		y += (int) (sy * 10);
		
		return (x < 0 || x > MyGameView.Width || y > MyGameView.Height);
	}
}


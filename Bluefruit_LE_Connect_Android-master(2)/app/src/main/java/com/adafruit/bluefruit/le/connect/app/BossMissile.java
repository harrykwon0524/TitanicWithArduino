package com.adafruit.bluefruit.le.connect.app;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.adafruit.bluefruit.le.connect.R;

//--------------------------------
//Boss Missile - ���� �̻���
//--------------------------------
public class BossMissile {
	public int x, y;			// ��ǥ
	public int w, h;			// ���� ����
	public boolean isDead;		// ���
	public Bitmap imgMissile;	// �̻��� �̹���

	private int sx, sy;			// �̵� �ӵ�, ����
	
	//--------------------------------
	// ������
	//--------------------------------
	public BossMissile(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		
		imgMissile = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.boss_missile);
		w = imgMissile.getWidth() / 2;
		h = imgMissile.getHeight() / 2;
		
		sy = 10;
		sx = 0;
		if (dir == EnemyBoss.LEFT)
			sx = -2;
		if (dir == EnemyBoss.RIGHT)
			sx = 2;
	}

	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		x += sx;
		y += sy;
		return (x < w || x > MyGameView.Width + w || y > MyGameView.Height + h);
	}
}	

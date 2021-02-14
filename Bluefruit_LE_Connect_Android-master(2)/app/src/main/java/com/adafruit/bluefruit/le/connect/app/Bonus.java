package com.adafruit.bluefruit.le.connect.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;

import com.adafruit.bluefruit.le.connect.R;
//--------------------------------
// Bonus - ���Ⱑ �ı��Ǹ� ���´�
//--------------------------------
public class Bonus {
	public int x, y;			// ��ǥ
	public int w, h;			// ���� ����
	public int kind;			// ���ʽ� ����
	public boolean isDead;		// ���
	public Bitmap imgBonus;		// �̹���

	private int sy;				// �̵� �ӵ�
	private int imgNum;			// �̹��� ��ȣ
	public Bitmap imgTemp[] = new Bitmap[16];
		
	//--------------------------------
	// ������
	//--------------------------------
	public Bonus(int x, int y, int kind) {
		this.x = x;
		this.y = y;
		this.kind = kind;
			
		imgTemp[0] = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.bonus0 + (kind - 1)); 
		int sw = imgTemp[0].getWidth();
		int sh = imgTemp[0].getHeight();
		w = sw / 2;
		h = sh / 2;
		
		// 16 �������� ȸ���� �̹��� �����
		Canvas canvas = new Canvas();
		for (int i = 1; i < 16; i++) {
			imgTemp[i] = Bitmap.createBitmap(sw, sh, Config.ARGB_8888);
			canvas.setBitmap(imgTemp[i]);
			canvas.rotate(22.5f, w, h);
			canvas.drawBitmap(imgTemp[0], 0, 0, null);
		}
			
		sy = 2;			// �ӵ�
		imgNum = 0;		// ���� �̹��� ��ȣ
		Move();
		
	}
		
	//--------------------------------
	// Move
	//--------------------------------
	public boolean Move() {
		imgBonus = imgTemp[imgNum];		// �̹��� ��ȣ
		imgNum++;
		if (imgNum > 15) imgNum = 0;
			
		y += sy;
		return (y > MyGameView.Height + h);
	}
}


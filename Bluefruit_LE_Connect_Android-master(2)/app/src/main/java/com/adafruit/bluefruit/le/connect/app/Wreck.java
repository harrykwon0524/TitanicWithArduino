package com.adafruit.bluefruit.le.connect.app;

import java.util.*;

import android.content.*;
import android.graphics.*;
import com.adafruit.bluefruit.le.connect.R;

public class Wreck {
	public int x, y, rad3;			// ��ǥ, ������
	public  Bitmap imgBall3;			// ��Ʈ�� �̹���	
	public  boolean dead = false; 	// �Ͷ߸� ����
	
	private int _rad3;
	private int sx, sy;				// �̵� ���� �� �ӵ�
	private int width, height;		// View�� ũ��
	private Bitmap Child[] = new Bitmap[6];
	private int imgNum = 0;
	private int loop = 0;
				// ���� ī����
	
	
	public Wreck(Context context, int _x, int _y, int _width, int _height) {
	    width = _width;
	    height = _height;
	    x = _x;
	    y = _y;
	
	    imgBall3 = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.wrecked1);
       
	    Random rnd = new Random();
	    _rad3 = rnd.nextInt(30) + 50;
	    rad3 = _rad3;
	    
	    for(int i = 0; i <= 3; i++)
	    	Child[i] = Bitmap.createScaledBitmap(imgBall3,  _rad3 * 2 + i * 2, 
	    			_rad3 * 2 + i * 2, false);
	    Child[4] = Child[2];
	    Child[5] = Child[1];
	    imgBall3 = Child[0];
	    sx = 2;
	    sy = rnd.nextInt(3) + 5;
	    
	   
		MoveWreck();
	}
	
	public void MoveWreck() {
		loop ++;
		if(loop % 3 == 0) {
			imgNum ++;
			if(imgNum > 5) imgNum = 0;
			imgBall3 = Child[imgNum];
			
					}
		x += sx;
		y += sy;
		
       if(x >= height + rad3)
		x = -rad3;
				
		if(y <= rad3 || y >= 3200 ) {
        sy = -sy;
        	y += sy;
        }
	
	}
	
}
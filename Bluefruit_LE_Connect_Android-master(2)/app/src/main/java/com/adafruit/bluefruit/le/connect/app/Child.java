package com.adafruit.bluefruit.le.connect.app;

import java.util.*;

import android.content.*;
import android.graphics.*;
import com.adafruit.bluefruit.le.connect.R;

public class Child {
	public int x, y, rad2;			// ��ǥ, ������
	public  Bitmap imgBall2;			// ��Ʈ�� �̹���	
	public  boolean dead = false; 	// �Ͷ߸� ����
	
	
	private int _rad2;
	private int sx, sy;				// �̵� ���� �� �ӵ�
	private int width, height;		// View�� ũ��
	private Bitmap Child[] = new Bitmap[6];
	private int imgNum = 0;
	private int loop = 0;
				// ���� ī����
	
	public Child(Context context, int _x, int _y, int _width, int _height) {
	    width = _width;
	    height = _height;
	    x = _x;
	    y = _y;
	
        imgBall2 = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.child_noback);
        
	    Random rnd = new Random();
	    _rad2 = rnd.nextInt(11) + 50;
	    rad2 = _rad2;
	    
	    for(int i = 0; i <= 3; i++)
	    	Child[i] = Bitmap.createScaledBitmap(imgBall2,  _rad2 * 2 + i * 2, 
	    			_rad2 * 2 + i * 2, false);
	    Child[4] = Child[2];
	    Child[5] = Child[1];
	    imgBall2 = Child[0];
	    sx = 2;
	    sy = rnd.nextInt(3) + 2;
	    
	   
		MovePeople2();
	}
	
	
	public void MovePeople2() {
		loop ++;
		if(loop % 3 == 0) {
			imgNum ++;
			if(imgNum > 5) imgNum = 0;
			imgBall2 = Child[imgNum];
			
					}
		x += sx;
		y += sy;
       if(x >= height + rad2)
		x = -rad2;
				
		if(y <= rad2 || y >=  6400) {
        	sy = -sy;
        	y += sy;
        }
	
	
	}
	


}
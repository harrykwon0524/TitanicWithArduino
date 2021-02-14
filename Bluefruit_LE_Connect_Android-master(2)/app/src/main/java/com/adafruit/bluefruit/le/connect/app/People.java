package com.adafruit.bluefruit.le.connect.app;

import java.util.*;
import com.adafruit.bluefruit.le.connect.R;
import android.content.*;
import android.graphics.*;

public class People {
	public int x, y, rad;			// ��ǥ, ������
	public  Bitmap imgBall;			// ��Ʈ�� �̹���	
	public  boolean dead = false; 	// �Ͷ߸� ����
	
	
	private int _rad;
	private int sx, sy;				// �̵� ���� �� �ӵ�
	private int width, height;		// View�� ũ��
	private Bitmap Bubbles[] = new Bitmap[6];
	private int imgNum = 0;
	private int loop = 0;
				// ���� ī����
	
	public People(Context context, int _x, int _y, int _width, int _height) {
	    width = _width;
	    height = _height;
	    x = _x;
	    y = _y;
	
        imgBall = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.people_noback);
        
	    Random rnd = new Random();
	    _rad = rnd.nextInt(11) + 50;
	    rad = _rad;
	    
	    for(int i = 0; i <= 3; i++)
	    	Bubbles[i] = Bitmap.createScaledBitmap(imgBall,  _rad * 2 + i * 2, 
	    			_rad * 2 + i * 2, false);
	    Bubbles[4] = Bubbles[2];
	    Bubbles[5] = Bubbles[1];
	    imgBall = Bubbles[0];
	    sx = 2;
	    sy = rnd.nextInt(3) + 2;
	    
	   
		MovePeople();
	}
	
	
	public void MovePeople() {
		loop ++;
		if(loop % 3 == 0) {
			imgNum ++;
			if(imgNum > 5) imgNum = 0;
			imgBall = Bubbles[imgNum];
			
					}
		x += sx;
		y += sy;
       if(x >= height + rad)
		x = -rad;
				
		if(y <= rad || y >= 3200 ) {
        	sy = -sy;
        	y += sy;
        }
	
	
	}
	


}
package com.adafruit.bluefruit.le.connect.app;

import java.util.*;
import com.adafruit.bluefruit.le.connect.R;
import android.content.*;
import android.graphics.*;

public class Item {
	public int x, y, rad4;			// ��ǥ, ������
	public  Bitmap imgBall4;			// ��Ʈ�� �̹���	
	public  boolean dead = false; 	// �Ͷ߸� ����
	
	
	private int _rad4;
	private int sx, sy;				// �̵� ���� �� �ӵ�
	private int width, height;		// View�� ũ��
	private Bitmap Bubbles[] = new Bitmap[6];
	private int imgNum = 0;
	private int loop = 0;
				// ���� ī����
	
	public Item(Context context, int _x, int _y, int _width, int _height) {
	    width = _width;
	    height = _height;
	    x = _x;
	    y = _y;
	
        imgBall4 = BitmapFactory.decodeResource(MyGameView.mContext.getResources(), R.drawable.tube2);
        
	    Random rnd = new Random();
	    _rad4 = rnd.nextInt(11) + 50;
	    rad4 = _rad4;
	    
	    for(int i = 0; i <= 3; i++)
	    	Bubbles[i] = Bitmap.createScaledBitmap(imgBall4,  _rad4 * 2 + i * 2, 
	    			_rad4 * 2 + i * 2, false);
	    Bubbles[4] = Bubbles[2];
	    Bubbles[5] = Bubbles[1];
	    imgBall4 = Bubbles[0];
	    sx = 2;
	    sy = rnd.nextInt(3) + 2;
	    
	   
		MoveItem();
	}
	
	
	public void MoveItem() {
		loop ++;
		if(loop % 3 == 0) {
			imgNum ++;
			if(imgNum > 5) imgNum = 0;
			imgBall4 = Bubbles[imgNum];
			
					}
		x += sx;
		y += sy;
       if(x >= height + rad4)
		x = -rad4;
				
		if(y <= rad4 || y >= 3200 ) {
        	sy = -sy;
        	y += sy;
        }
	
	
	}
	


}
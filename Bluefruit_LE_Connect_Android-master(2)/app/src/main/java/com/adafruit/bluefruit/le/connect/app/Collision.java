package com.adafruit.bluefruit.le.connect.app;
import java.util.Random;

//----------------------------
//  �浹 ���� - ������ ����
//----------------------------
public class Collision {
	private Random rnd = new Random();

	//----------------------------
	//  �浹 ����
	//----------------------------
	public void CheckCollision() {
		Check_1();					// �Ʊ� �̻��ϰ� ������ �浹
		Check_2();					// �� �̻��ϰ� �Ʊ����� �浹
		Check_3();					// ������ �Ʊ����� �浹
		Check_4();					// �Ʊ��� ���ʽ����� �浹
		Check_7();
		Check_8();
		if (MyGameView.isBoss) {	// Boss Stage
			Check_5();				// Boss �̻��ϰ� �Ʊ����� �浹
			Check_6();				// �Ʊ� �̻��ϰ� �������� �浹
		}
	}
	
	//----------------------------
	//  �Ʊ� �̻��ϰ� �� �浹
	//----------------------------
	private void Check_1() {
		int x, y, x1, y1, w, h; 
		int r = rnd.nextInt(100) - 93;			// 0~6 - ���ʽ� ���� Ȯ��

		NEXT:
		for (int p = MyGameView.mGun.size() - 1; p >= 0; p--) { 
			x = MyGameView.mGun.get(p).x;		//	�̻��� ��ǥ	
			y = MyGameView.mGun.get(p).y;
			
			for (int i = 0; i < 6; i++) {		// ��� ������ ���� ����		 
				for (int j = 0; j < 8; j++) {			 
					if (MyGameView.mEnemy[i][j].isDead) continue;	// ����� ����

					x1 = MyGameView.mEnemy[i][j].x;			// ������ ��ǥ
					y1 = MyGameView.mEnemy[i][j].y;
					w = MyGameView.mEnemy[i][j].w;				// �浹�� ������ ����
					h = MyGameView.mEnemy[i][j].h;
					
					if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	// �浹 ���� 
						continue;

					if (MyGameView.isPower)							// ��ȭ �̻�����
						MyGameView.mEnemy[i][j].shield -= 4;		// ��ȣ�� 4�� ����
					else
						MyGameView.mEnemy[i][j].shield--;			// �Ϲ� �̻���
						
					if (MyGameView.mEnemy[i][j].shield > 0) {		// ��ȣ���� ������
						// ���� ������ �̻��� ��ġ��
						MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL)); 
						//MyGameView.score += (6 - i) * 100;			// ������ ����
					} else {				 
						MyGameView.mEnemy[i][j].isDead = true;	 	// ���� ���
						MyGameView.mMap.enemyCnt--;					// ���� ���� ��
						// ū ������ ������ �߽ɺο� 
						MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG)); 
						//MyGameView.score += (6 - i) * 200;			// ����

						if (r > 0)									// ���ʽ��� �ֳ�?
							MyGameView.mBonus.add(new Bonus(x1, y1, r));	// ���ʽ�
					}
					MyGameView.mGun.remove(p);						// �Ʊ� �̻��� ����
					continue NEXT;
				} // for j
			} // for i
		} // for p
	}

	//----------------------------
	//  ���� �̻��ϰ� �Ʊ��� �浹
	//----------------------------
	private void Check_2() {
		if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
		
		int x, y, x1, y1, x2, y2, w, h;
		x = MyGameView.mShip.x;		// �Ʊ��� ��ǥ
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		// ��� �� �̻��Ͽ� ���� ����
		for (int i = MyGameView.mMissile.size() - 1; i >= 0; i--) {
			
			x1 = MyGameView.mMissile.get(i).x;					// �̻��� ��ǥ
			y1 = MyGameView.mMissile.get(i).y;
			
			
			if (Math.abs(x1 - x) > w || Math.abs(y1 - y) > h )	
				continue; 
			MyGameView.mMissile.remove(i);	
			
			//MyGameView.mShip.shield--;							// �Ʊ� ��ȣ�� ����
			//MyGameView.score += (6 - i) *  200;
			
			//MyGameView.people -= 1;
			
		} // for
	
	}
	//----------------------------
	//  �Ʊ���� ������� �浹
	//----------------------------
	
	private void Check_3() {
if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
		
		int x, y, x1, y1, x2, y2, w, h;
		x = MyGameView.mShip.x;		// �Ʊ��� ��ǥ
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		// ��� �� �̻��Ͽ� ���� ����
		for (int i = MyGameView.mPeople.size() - 1; i >= 0; i--) {
			
			x1 = MyGameView.mPeople.get(i).x;
			y1 = MyGameView.mPeople.get(i).y;
			
			if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	
				continue; 
			MyGameView.mPeople.remove(i);	
			
			//MyGameView.mShip.shield--;							// �Ʊ� ��ȣ�� ����
			MyGameView.score += i *  200;
			MyGameView.sdPool.play(MyGameView.sdExp4, 1, 1, 9, 0, 1);
			MyGameView.people += 1;
			
		} // for
	
	}

	
	//----------------------------
	//  �Ʊ���� ���ʽ���  �浹
	//----------------------------
	private void Check_4() {
		int x, y, x1, y1, w, h, bonus = 0;
		
		x = MyGameView.mShip.x;						// �Ʊ��� ��ǥ
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		for (int i = MyGameView.mBonus.size() - 1; i >= 0; i--) {
			x1 = MyGameView.mBonus.get(i).x;	// ���ʽ� ��ǥ
			y1 = MyGameView.mBonus.get(i).y;
			if (Math.abs(x - x1) > w * 2 || Math.abs(y - y1) > h * 2)	// �浹 ���� 
				continue;

			bonus = MyGameView.mBonus.get(i).kind;		// ���ʽ� ����
			MyGameView.mBonus.remove(i);				// ���ʽ� ����

			switch (bonus) {
			case 1: 
				MyGameView.isDouble = true;		// Double Fire ���
				break;
			case 2: 	
				MyGameView.isPower = true;		// ��ȭ �̻���
				break;
			case 3: 	
				if (MyGameView.gunDelay > 6)
					MyGameView.gunDelay -= 2;	// �̻���  �߻� �ӵ�
				break;
			case 4:
				MyGameView.mShip.shield = 6;	// ��ȣ�� ����
				break;
			case 5:
				MyGameView.mShip.undeadCnt = 100;	// ���� ���� ����		 
				MyGameView.mShip.undead = true;				 
				break;
			case 6:
				if (MyGameView.shipCnt < 4)		// ���ּ� 1�� �÷���
					MyGameView.shipCnt++;
			}
		} // for
	}

	//----------------------------
	//  Boss �̻��ϰ� �Ʊ�����  �浹
	//----------------------------
	private void Check_5() {
		if (MyGameView.mShip.undead) return;
		
		int x, y, x1, y1;
		int w, h;

		x = MyGameView.mShip.x;
		y = MyGameView.mShip.y;
		w = MyGameView.mShip.w;
		h = MyGameView.mShip.h;
		
		for (int i = MyGameView.mBsMissile.size() - 1; i >= 0; i--) {	
			x1 = MyGameView.mBsMissile.get(i).x;					// �̻��� ��ǥ
			y1 = MyGameView.mBsMissile.get(i).y;

			if (Math.abs(x1 - x) <= w && Math.abs(y1 - y) <= h)	{ 
				MyGameView.mBsMissile.remove(i);				// �� �̻��� ����
				MyGameView.mShip.isDead = true;					// �Ʊ��⸦ ����
				MyGameView.mExp.add(new Explosion(x, y, Explosion.MYSHIP));	
				MyGameView.shipCnt--;						
			}	
		}
	}
	
	//----------------------------
	//  �Ʊ� �̻��ϰ� Boss��  �浹
	//----------------------------
	private void Check_6() {
		int x1, x2, x3, y1, w, h;			// Boss�� Center, Left, Right, ��
		int x, y, damage = 1;				// �̻��� ��ǥ, power
		if (MyGameView.isPower) damage = 4;
		
		// Boss 3�κ� ��ǥ�� ��
		w = MyGameView.mBoss.w / 2;
		h = MyGameView.mBoss.h;
		x1 = MyGameView.mBoss.x;
		x2 = x1 - w;
		x3 = x1 + w;
		y1 = MyGameView.mBoss.y;

		for (int i = MyGameView.mGun.size() - 1; i >= 0; i--) { 
			x = MyGameView.mGun.get(i).x;		//	�̻��� ��ǥ	
			y = MyGameView.mGun.get(i).y;
		
			// Boss Center
			if (Math.abs(x - x1) < w && Math.abs(y - y1) < h ) {
				MyGameView.mBoss.shield[EnemyBoss.CENTER] -= damage;
				MyGameView.mGun.remove(i);

				// ���� (-)�� �ɶ����� ó�� - Explosion���� CENTER�� ������
				if (MyGameView.mBoss.shield[EnemyBoss.CENTER] >= 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));
					//MyGameView.score += 50;
					continue;
				}
				ClearAllEnemies();
				return;
			} // if
			
			// ������ ����
			if (Math.abs(x - x2) < w && Math.abs(y - y1) < h && 
					MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
				MyGameView.mBoss.shield[1] -= damage;
				MyGameView.mGun.remove(i);

				if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));	
					//MyGameView.score += 50;
					continue;
				}	
				
				// Boss ���� �ı�
				MyGameView.mExp.add(new Explosion(x2, y1, Explosion.BIG));
				//MyGameView.score += 1000;		
				MyGameView.mGun.remove(i);
					
				if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0)
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.RIGHT];
				else	
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.CENTER];
				continue;
			} // if
			
			// ������ ������
			if (Math.abs(x - x3) < w && Math.abs(y - y1) < h && 
					MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
				MyGameView.mBoss.shield[EnemyBoss.RIGHT] -= damage;
				MyGameView.mGun.remove(i);

				if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
					MyGameView.mExp.add(new Explosion(x, y, Explosion.SMALL));	
					//MyGameView.score += 50;
					continue;
				}	
				
				// Boss ������ �ı�
				MyGameView.mExp.add(new Explosion(x3, y1, Explosion.BIG));	
				//MyGameView.score += 1000;		
				MyGameView.mGun.remove(i);
					
				if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0)
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.LEFT];
				else	
					MyGameView.mBoss.imgBoss = MyGameView.mBoss.imgSpt[EnemyBoss.CENTER];
			} // if
		} // for
	}	
	
	
	//----------------------------
	//  ��� �� �ı�
	//----------------------------
	private void ClearAllEnemies() {
		int x1, x2, x3, y1, w;			// Boss�� Center, Left, Right, ��

		w = MyGameView.mBoss.w / 2;
		x1 = MyGameView.mBoss.x;
		x2 = x1 - w;
		x3 = x1 + w;
		y1 = MyGameView.mBoss.y;
		
		// Boss �ı�
		MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BOSS));
		//MyGameView.score += 5000;
		
		// ���� �ı�
		if (MyGameView.mBoss.shield[EnemyBoss.LEFT] > 0) {
			MyGameView.mBoss.shield[EnemyBoss.LEFT] = 0;
			MyGameView.mExp.add(new Explosion(x2, y1, Explosion.BOSS));
		}
		
		// ������ �ı�
		if (MyGameView.mBoss.shield[EnemyBoss.RIGHT] > 0) {
			MyGameView.mBoss.shield[EnemyBoss.RIGHT] = 0;
			MyGameView.mExp.add(new Explosion(x3, y1, Explosion.BOSS));
		}

		// Boss Missile ��� ����
		for (BossMissile tmp : MyGameView.mBsMissile) {
			MyGameView.mExp.add(new Explosion(tmp.x, tmp.y, Explosion.BIG));
		}
		
		MyGameView.mBsMissile.clear();
		
		// ȭ�鿡 ���� �� ��� ����
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (MyGameView.mEnemy[i][j].shield > 0) {
					x1 = MyGameView.mEnemy[i][j].x;
					y1 = MyGameView.mEnemy[i][j].y;
					MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG));
					MyGameView.mEnemy[i][j].shield = 0;
					MyGameView.mMap.enemyCnt--;					// ���� ���� ��
				}
			}
		} // for
		
		//Stage Clear�� Explosion���� ó����
	}
	
	private void Check_7() {
		if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
				
				int x, y, x1, y1, x2, y2, w, h;
				x = MyGameView.mShip.x;		// �Ʊ��� ��ǥ
				y = MyGameView.mShip.y;
				w = MyGameView.mShip.w;
				h = MyGameView.mShip.h;
				
				// ��� �� �̻��Ͽ� ���� ����
				for (int i = MyGameView.mChild.size() - 1; i >= 0; i--) {
					
					x1 = MyGameView.mChild.get(i).x;
					y1 = MyGameView.mChild.get(i).y;
					
					if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	
						continue; 
					MyGameView.mChild.remove(i);	
					
					//MyGameView.mShip.shield--;							// �Ʊ� ��ȣ�� ����
					MyGameView.score += i *  150;
					MyGameView.sdPool.play(MyGameView.sdExp4, 1, 1, 9, 0, 1);
					MyGameView.people += 1;
					
				} // for
			
			}

	private void Check_8() {
		if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
				
				int x, y, x1, y1, x2, y2, w, h;
				x = MyGameView.mShip.x;		// �Ʊ��� ��ǥ
				y = MyGameView.mShip.y;
				w = MyGameView.mShip.w;
				h = MyGameView.mShip.h;
				
				// ��� �� �̻��Ͽ� ���� ����
				for (int i = MyGameView.mWreck.size() - 1; i >= 0; i--) {
					
					x1 = MyGameView.mWreck.get(i).x;
					y1 = MyGameView.mWreck.get(i).y;
					
					if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	
						continue; 
					if (MyGameView.mShip.undead) {
						MyGameView.mExp.add(new Explosion(x1, y1, Explosion.BIG));
					} else {
						MyGameView.mShip.isDead = true;
						MyGameView.mExp.add(new Explosion(x, y, Explosion.MYSHIP));
						MyGameView.shipCnt --;
					}
						
					MyGameView.sdPool.play(MyGameView.sdExp0, 1, 1, 9, 0, 1);
					MyGameView.score -= i *  150;
					
					return;
					//MyGameView.mShip.shield--;							// �Ʊ� ��ȣ�� ����
					
					
					
				} // for
			
			}
			
	
	private void Check_9() {
		if (MyGameView.mShip.undead || MyGameView.mShip.isDead) return;
				
				int x, y, x1, y1, x2, y2, w, h;
				x = MyGameView.mShip.x;		// �Ʊ��� ��ǥ
				y = MyGameView.mShip.y;
				w = MyGameView.mShip.w;
				h = MyGameView.mShip.h;
				
				// ��� �� �̻��Ͽ� ���� ����
				for (int i = MyGameView.mItem.size() - 1; i >= 0; i--) {
					
					x1 = MyGameView.mItem.get(i).x;
					y1 = MyGameView.mItem.get(i).y;
					
					if (Math.abs(x - x1) > w || Math.abs(y - y1) > h)	
						continue; 
					MyGameView.mItem.remove(i);	
					
					//MyGameView.mShip.shield--;							// �Ʊ� ��ȣ�� ����
					MyGameView.score += (6 - i) *  150;
					MyGameView.sdPool.play(MyGameView.sdExp4, 1, 1, 9, 0, 1);
					MyGameView.people += 1;
					
				} // for
			
			}
}
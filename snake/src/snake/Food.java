package snake;

import java.util.Random;


public class Food {
	Random r = new Random();
		
	
	private  int foodx;
	private  int foody;
	
	public void getRandomed() {
		//随机生成食物，确保在障碍物打开的情况下，食物不会出现在在障碍物中
		int a = r.nextInt(34)*25 + 25;
		int b = r.nextInt(24)*25 + 75;
		for(int i=10;i<21;i++) {
		if((a==10*25||a==24*25)&&b==i*25) {
			getRandomed();
		}
		else
		{
			this.foodx = a;
			this.foody = b;
		}
		}
		
		for(int k = 5;k<8;k++) {
			if((a==5*25||a==30*25)&&b==k*25) {
				getRandomed();
			}
			else
			{
				this.foodx = a;
				this.foody = b;
			}
		}
		
		for(int j = 18;j<21;j++) {
			if((a==5*25||a==30*25)&&b==j*25) {
				getRandomed();
			}
			else
			{
				this.foodx = a;
				this.foody = b;
			}
			
			
		}
		
	}
	public Food(int foodx,int foody) {
		this.foodx = foodx;
		this.foody = foody;
	}
	
	
	public void foodMoved() {
		int a = r.nextInt(34)*25 + 25;
		int b = r.nextInt(24)*25 + 75;
		this.foodx = a;
		this.foody = b;
	}

	public  int getfoodx() {
		return foodx;
	}
	public  int getfoody() {
		return foody;
	}

}

package rosalila.studio.slotmachine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Box extends Image {
	Animation animation;
	int current_row;
	String current_effect;
	int  original_x;
	int original_y;
	float original_width;
	float original_height;
	public Box(int x, int y, int current_value)
	{
		super(new AnimationDrawable(4,8,128,128, current_value,"data/frutas.png",0.1f));
		
		original_x = x;
		original_y = y;
		original_width = this.getWidth();
		original_height = this.getHeight();
		
		current_row = 0;

        setPosition(x, y);
        
        current_effect = "bigger size";
	}

	@Override
	public void act(float delta)
	{
		((AnimationDrawable) this.getDrawable()).act(delta);
		super.act(delta);
		if(current_effect=="bigger size")
		{
			setScale((float)(getScaleX()+0.01));
			setPosition(original_x-(this.getWidth()*getScaleX()-this.getWidth())/2, original_y-(this.getHeight()*getScaleY()-this.getHeight())/2);
			if((float)(getScaleX()+0.1)>1.5)
			{
				current_effect = "smaller size";
			}
		}else if(current_effect=="smaller size")
		{
			setScale((float)(getScaleX()-0.01));
			setPosition(original_x-(this.getWidth()*getScaleX()-this.getWidth())/2, original_y-(this.getHeight()*getScaleY()-this.getHeight())/2);
			if((float)(getScaleX())<1.0)
			{
				setScale((float)1.0);
				setPosition(original_x-(this.getWidth()*getScaleX()-this.getWidth())/2, original_y-(this.getHeight()*getScaleY()-this.getHeight())/2);
				current_effect = "bigger size";
			}
		}else
		{
			setScale((float)1.0);
			setPosition(original_x-(this.getWidth()*getScaleX()-this.getWidth())/2, original_y-(this.getHeight()*getScaleY()-this.getHeight())/2);
		}
	}
	
	void beginRoll()
	{
		current_row = 0;
		((AnimationDrawable) getDrawable()).animateRow(current_row,true);
	}
	
	void endRoll()
	{
		current_row = (int)((Math.random()*1000)%7+1);
		((AnimationDrawable) getDrawable()).animateRow(current_row,false);
		current_effect = "";
	}
	
	double getPrize(int combo)
	{
		if(current_row==1)
		{
			if(combo==6)
				return 1000;
			if(combo==5)
				return 700;
			if(combo==4)
				return 380;
			if(combo==3)
				return 200;
			if(combo==2)
				return 100;
		}
		
		if(current_row==2)
		{
			if(combo==6)
				return 800;
			if(combo==5)
				return 500;
			if(combo==4)
				return 180;
			if(combo==3)
				return 80;
			if(combo==2)
				return 20;
		}
		
		if(current_row==3)
		{
			if(combo==6)
				return 650;
			if(combo==5)
				return 450;
			if(combo==4)
				return 150;
			if(combo==3)
				return 60;
			if(combo==2)
				return 15;
		}
		
		if(current_row==4)
		{
			if(combo==6)
				return 450;
			if(combo==5)
				return 250;
			if(combo==4)
				return 90;
			if(combo==3)
				return 40;
			if(combo==2)
				return 10;
		}
		
		if(current_row==5)
		{
			if(combo==6)
				return 250;
			if(combo==5)
				return 50;
			if(combo==4)
				return 25;
			if(combo==3)
				return 5;
		}
		
		if(current_row==6)
		{
			if(combo==6)
				return 50;
			if(combo==5)
				return 25;
			if(combo==4)
				return 5;
			if(combo==3)
				return 3;
		}
		
		if(current_row==7)
		{
			if(combo==6)
				return 25;
			if(combo==5)
				return 5;
			if(combo==4)
				return 3;
		}
		return 0;
	}
}
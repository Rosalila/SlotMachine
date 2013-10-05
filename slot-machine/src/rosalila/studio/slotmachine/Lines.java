package rosalila.studio.slotmachine;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Lines extends Image {
	int lines;
	int max_lines;
	Label label_total_bet;
	SlotLogic slot_logic;
	ArrayList<Image>lines_ui;
	public Lines(int x, int y, int max_lines_param, Label label_total_bet_param,ArrayList<Image>lines_ui_param)
	{
		super(new AnimationDrawable(1,4,128,64,0,"data/lines.png",0.1f));
		
		this.max_lines = max_lines_param;
		this.label_total_bet=label_total_bet_param;
		this.lines_ui=lines_ui_param;

        setPosition(x, y);
        addCaptureListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
        	{
        		lines++;
        		if(lines>max_lines)
        			lines=1;
        		animateLines(lines);
        		label_total_bet.setText(slot_logic.getTotalBetString());
        		return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button)
            {
            	
            }
        });
        lines = 1;
        animateLines(lines);
	}

	@Override
	public void act(float delta)
	{
		((AnimationDrawable) this.getDrawable()).act(delta);
		super.act(delta);
	}
	
	void animateLines(int lines)
	{
		((AnimationDrawable) getDrawable()).animateRow(lines-1,true);
		if(lines==1)
		{
			lines_ui.get(0).setVisible(false);
			lines_ui.get(1).setVisible(true);
			lines_ui.get(2).setVisible(false);
		}
		if(lines==2)
		{
			lines_ui.get(0).setVisible(true);
			lines_ui.get(1).setVisible(false);
			lines_ui.get(2).setVisible(true);
		}
		if(lines==3)
		{
			lines_ui.get(0).setVisible(true);
			lines_ui.get(1).setVisible(true);
			lines_ui.get(2).setVisible(true);
		}
	}
	
	int getValue()
	{
		return lines;
	}
	
	void hideLinesUI()
	{
		for(int i=0;i<lines_ui.size();i++)
		{
			lines_ui.get(i).setVisible(false);
		}
	}
}
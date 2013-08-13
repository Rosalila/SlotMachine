package rosalila.studio.slotmachine;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Handle extends Image {
	String state;
	public Handle(int x, int y, final SlotLogic slot_logic)
	{
		super(new AnimationDrawable(4,4,128,128,0,"data/handle.png",0.1f));

        setPosition(x, y);
        addCaptureListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		((AnimationDrawable) getDrawable()).animateRow(1,false);
        		slot_logic.beginRoll();
        		return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	((AnimationDrawable) getDrawable()).animateRow(2,false);
            	slot_logic.endRoll();
            }
        });
        state = "animating";
        ((AnimationDrawable) getDrawable()).animateRow(0,true);
	}

	@Override
	public void act(float delta)
	{
		((AnimationDrawable) this.getDrawable()).act(delta);
		super.act(delta);
	}
}
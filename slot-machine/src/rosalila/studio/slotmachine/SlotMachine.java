package rosalila.studio.slotmachine;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SlotMachine implements ApplicationListener{
	
	private Stage stage;
	
	SlotLogic slot_logic;
	
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	private final AndroidFunctionsInterface androidFunctions;
	
	public SlotMachine(AndroidFunctionsInterface desktopFunctions)
	{
		this.androidFunctions = desktopFunctions;
	}
        
	@Override
	public void create() {	        
		//Stage init
		//androidFunctions.SwarmInitiate();
		
//		int i_temp=0;
//		while(androidFunctions.getScore()==-1)
//		{
////			try {
////				Thread.sleep(1);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			i_temp++;
////			if(i_temp<10000)
////				break;
//		}
		
		androidFunctions.ShowLeaderboardSwarm();
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//font
		font = new BitmapFont(Gdx.files.internal("data/default.fnt"),false);
		font.setColor(0.0f,0.0f,0.0f,1.0f);
		Skin uiSkin;
		uiSkin = new Skin();
		uiSkin.add("default", new BitmapFont());
		//Label style
		LabelStyle label_syle = new LabelStyle();
		label_syle.font = font;
		label_syle.fontColor = Color.BLACK;
		uiSkin.add("default", label_syle);
		
		Texture texture_background = new Texture("data/background.png");
		Background background = new Background(texture_background);
		
		Texture texture_win = new Texture("data/win.png");
		Image win = new Image(texture_win);
		win.setPosition(0, 400);
		win.setVisible(false);
		
		ArrayList< ArrayList<Box> > boxes = new ArrayList< ArrayList<Box> >();
		for(int i=0;i<6;i++)
		{
			ArrayList<Box> boxes_columns = new ArrayList<Box>();
			for(int j=0;j<3;j++)
			{
				Box box = new Box(256+i*128,384-j*128,(int)((Math.random()*1000)%7+1));
				boxes_columns.add(box);
			}
			boxes.add(boxes_columns);
		}
		
		Label label_credits = new Label("Credits: "+androidFunctions.getScore(),uiSkin);
		label_credits.setPosition(25,160);
		Label label_total_bet = new Label("Total bet: 1",uiSkin);
		label_total_bet.setPosition(25,260);
		Label label_last_prize = new Label("Last prize: 0",uiSkin);
		label_last_prize.setPosition(25,360);
		
		Lines lines = new Lines(1050,500,3,label_total_bet);
		
		ArrayList<Double>bets=new ArrayList<Double>();
		bets.add(1.0);
		bets.add(5.0);
		bets.add(10.0);
		bets.add(20.0);
		Bet bet = new Bet(1050,350,bets,label_total_bet);
		
		BackgroundButton bg_btn1 = new BackgroundButton(380, 600, 1, stage);
		BackgroundButton bg_btn2 = new BackgroundButton(480, 600, 2, stage);
		BackgroundButton bg_btn3 = new BackgroundButton(580, 600, 3, stage);
		BackgroundButton bg_btn4 = new BackgroundButton(680, 600, 4, stage);
		BackgroundButton bg_btn5 = new BackgroundButton(780, 600, 5, stage);
		BackgroundButton bg_btn6 = new BackgroundButton(880, 600, 6, stage);
		BackgroundButton bg_btn7 = new BackgroundButton(980, 600, 7, stage);
		BackgroundButton bg_btn8 = new BackgroundButton(1080, 600, 8, stage);
		
		ArrayList<Powerup> powerups=new ArrayList<Powerup>();
		Powerup pb_btn1 = new Powerup(380, 50, 1,uiSkin,label_credits);
		Powerup pb_btn2 = new Powerup(480, 50, 2,uiSkin,label_credits);
		Powerup pb_btn3 = new Powerup(580, 50, 3,uiSkin,label_credits);
		Powerup pb_btn4 = new Powerup(680, 50, 4,uiSkin,label_credits);
		Powerup pb_btn5 = new Powerup(780, 50, 5,uiSkin,label_credits);
		Powerup pb_btn6 = new Powerup(880, 50, 6,uiSkin,label_credits);
		Powerup pb_btn7 = new Powerup(980, 50, 7,uiSkin,label_credits);
		powerups.add(pb_btn1);
		powerups.add(pb_btn2);
		powerups.add(pb_btn3);
		powerups.add(pb_btn4);
		powerups.add(pb_btn5);
		powerups.add(pb_btn6);
		powerups.add(pb_btn7);
		
		slot_logic = new SlotLogic(boxes, androidFunctions.getScore(), win,lines,bet,label_credits,label_last_prize,powerups,androidFunctions);
		lines.slot_logic=slot_logic;
		bet.slot_logic=slot_logic;
		pb_btn1.slot_logic=slot_logic;
		pb_btn2.slot_logic=slot_logic;
		pb_btn3.slot_logic=slot_logic;
		pb_btn4.slot_logic=slot_logic;
		pb_btn5.slot_logic=slot_logic;
		pb_btn6.slot_logic=slot_logic;
		pb_btn7.slot_logic=slot_logic;
		Handle handle = new Handle(1050,200,slot_logic);
		slot_logic.handle = handle;
		
		//Add objects to the stage
		stage.addActor(background);
		for(int i=0;i<boxes.size();i++)
		{
			for(int j=0;j<boxes.get(i).size();j++)
				stage.addActor(boxes.get(i).get(j));
		}
		stage.addActor(handle);
		stage.addActor(lines);
		stage.addActor(bet);
		stage.addActor(win);
		stage.addActor(label_credits);
		stage.addActor(label_total_bet);
		stage.addActor(label_last_prize);
		stage.addActor(bg_btn1);
		stage.addActor(bg_btn2);
		stage.addActor(bg_btn3);
		stage.addActor(bg_btn4);
		stage.addActor(bg_btn5);
		stage.addActor(bg_btn6);
		stage.addActor(bg_btn7);
		stage.addActor(bg_btn8);
		
		stage.addActor(pb_btn1);
		stage.addActor(pb_btn2);
		stage.addActor(pb_btn3);
		stage.addActor(pb_btn4);
		stage.addActor(pb_btn5);
		stage.addActor(pb_btn6);
		stage.addActor(pb_btn7);
		//stage.addActor(button);
		//stage.addActor(introText);
	}
	
	@Override
	public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		//slot_logic.credits=androidFunctions.getScore();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(1280, 736, true);	
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

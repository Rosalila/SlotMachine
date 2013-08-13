package rosalila.studio.slotmachine;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class SlotLogic {
	ArrayList< ArrayList<Box> > boxes;
	ArrayList<Powerup> powerups;
	Handle handle;
	Lines lines;
	Bet bet;
	float credits;
	float credits_saved;
	Image win;
	
	Label label_credits;
	Label label_total_bet;
	double last_prize;
	
	private final AndroidFunctionsInterface androidFunctions;
	
	public SlotLogic(ArrayList< ArrayList<Box> > boxes, float credits, Image win, Lines lines, Bet bet, Label label_credits, Label label_total_bet,ArrayList<Powerup> powerups,AndroidFunctionsInterface androidFunctions_param)
	{
		this.boxes = boxes;
		this.credits = credits;
		this.credits_saved = credits;
		this.win = win;
		this.lines=lines;
		this.bet=bet;
		this.label_credits=label_credits;
		this.label_total_bet=label_total_bet;
		this.powerups=powerups;
		this.androidFunctions=androidFunctions_param;
		last_prize=0;
	}
	
	void beginRoll()
	{
		for(int i=0;i<powerups.size();i++)
		{
			powerups.get(i).value--;
			if(powerups.get(i).value<0)
				powerups.get(i).value=0;
			powerups.get(i).act(0);
		}
		credits-=getTotalBet();
		for(int i=0;i<boxes.size();i++)
		{
			for(int j=0;j<boxes.get(i).size();j++)
				boxes.get(i).get(j).beginRoll();
		}
		win.setVisible(false);
		label_credits.setText(getCreditsString());
	}
	
	void endRoll()
	{
		for(int i=0;i<boxes.size();i++)
		{
			for(int j=0;j<boxes.get(i).size();j++)
				boxes.get(i).get(j).endRoll();
		}
		
		double prize=0;
		if(lines.getValue()==1)
		{
			prize+=winCombo6(1);
			prize+=winCombo5(1);
			prize+=winCombo4(1);
			prize+=winCombo3(1);
			prize+=winCombo2(1);
		}
		
		if(lines.getValue()==2)
		{
			prize+=winCombo6(0);
			prize+=winCombo5(0);
			prize+=winCombo4(0);
			prize+=winCombo3(0);
			prize+=winCombo2(0);
			
			prize+=winCombo6(2);
			prize+=winCombo5(2);
			prize+=winCombo4(2);
			prize+=winCombo3(2);
			prize+=winCombo2(2);
		}
		
		if(lines.getValue()==3)
		{
			prize+=winCombo6(0);
			prize+=winCombo5(0);
			prize+=winCombo4(0);
			prize+=winCombo3(0);
			prize+=winCombo2(0);
			
			prize+=winCombo6(1);
			prize+=winCombo5(1);
			prize+=winCombo4(1);
			prize+=winCombo3(1);
			prize+=winCombo2(1);
			
			prize+=winCombo6(2);
			prize+=winCombo5(2);
			prize+=winCombo4(2);
			prize+=winCombo3(2);
			prize+=winCombo2(2);
		}

		if(prize!=0)
		{
			win.setVisible(true);
			credits+=prize;
		}
		
		last_prize=prize;
		label_credits.setText(getCreditsString());
		androidFunctions.SubmitScore(credits);
	}
	
	double winCombo6(int row)
	{
		double prize = 0;
		for(int col=0;col<1;col++)
		{
			if(boxes.get(col).get(row).current_row == boxes.get(col+1).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+2).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+3).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+4).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+5).get(row).current_row
					
					&& boxes.get(col).get(row).current_effect == ""
					&& boxes.get(col+1).get(row).current_effect == ""
					&& boxes.get(col+2).get(row).current_effect == ""
					&& boxes.get(col+3).get(row).current_effect == ""
					&& boxes.get(col+4).get(row).current_effect == ""
					&& boxes.get(col+5).get(row).current_effect == "")
			{
				double new_prize = boxes.get(col).get(row).getPrize(6);
				if(new_prize!=0)
				{
					prize+=new_prize;
					boxes.get(col).get(row).current_effect = "bigger size";
					boxes.get(col+1).get(row).current_effect = "bigger size";
					boxes.get(col+2).get(row).current_effect = "bigger size";
					boxes.get(col+3).get(row).current_effect = "bigger size";
					boxes.get(col+4).get(row).current_effect = "bigger size";
					boxes.get(col+5).get(row).current_effect = "bigger size";
				}
			}
		}
		return prize;
	}
	
	double winCombo5(int row)
	{
		double prize = 0;
		for(int col=0;col<2;col++)
		{
			if(boxes.get(col).get(row).current_row == boxes.get(col+1).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+2).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+3).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+4).get(row).current_row
					
					&& boxes.get(col).get(row).current_effect == ""
					&& boxes.get(col+1).get(row).current_effect == ""
					&& boxes.get(col+2).get(row).current_effect == ""
					&& boxes.get(col+3).get(row).current_effect == ""
					&& boxes.get(col+4).get(row).current_effect == "")
			{
				double new_prize = boxes.get(col).get(row).getPrize(5);
				if(new_prize!=0)
				{
					prize+=new_prize;
					boxes.get(col).get(row).current_effect = "bigger size";
					boxes.get(col+1).get(row).current_effect = "bigger size";
					boxes.get(col+2).get(row).current_effect = "bigger size";
					boxes.get(col+3).get(row).current_effect = "bigger size";
					boxes.get(col+4).get(row).current_effect = "bigger size";
				}
			}
		}
		return prize;
	}

	double winCombo4(int row)
	{
		double prize = 0;
		for(int col=0;col<3;col++)
		{
			if(boxes.get(col).get(row).current_row == boxes.get(col+1).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+2).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+3).get(row).current_row
					
					&& boxes.get(col).get(row).current_effect == ""
					&& boxes.get(col+1).get(row).current_effect == ""
					&& boxes.get(col+2).get(row).current_effect == ""
					&& boxes.get(col+3).get(row).current_effect == "")
			{
				double new_prize = boxes.get(col).get(row).getPrize(4);
				if(new_prize!=0)
				{
					prize+=new_prize;
					boxes.get(col).get(row).current_effect = "bigger size";
					boxes.get(col+1).get(row).current_effect = "bigger size";
					boxes.get(col+2).get(row).current_effect = "bigger size";
					boxes.get(col+3).get(row).current_effect = "bigger size";
				}
			}
		}
		return prize;
	}
	
	double winCombo3(int row)
	{
		double prize = 0;
		for(int col=0;col<4;col++)
		{
			if(boxes.get(col).get(row).current_row == boxes.get(col+1).get(row).current_row
					&& boxes.get(col).get(row).current_row == boxes.get(col+2).get(row).current_row
					
					&& boxes.get(col).get(row).current_effect == ""
					&& boxes.get(col+1).get(row).current_effect == ""
					&& boxes.get(col+2).get(row).current_effect == "")
			{
				double new_prize = boxes.get(col).get(row).getPrize(3);
				if(new_prize!=0)
				{
					prize+=new_prize;
					boxes.get(col).get(row).current_effect = "bigger size";
					boxes.get(col+1).get(row).current_effect = "bigger size";
					boxes.get(col+2).get(row).current_effect = "bigger size";
				}
			}
		}
		return prize;
	}
	
	double winCombo2(int row)
	{
		double prize = 0;
		for(int col=0;col<5;col++)
		{
			if(boxes.get(col).get(row).current_row == boxes.get(col+1).get(row).current_row
					
					&& boxes.get(col).get(row).current_effect == ""
					&& boxes.get(col+1).get(row).current_effect == "")
			{
				double new_prize = boxes.get(col).get(row).getPrize(2);
				if(new_prize!=0)
				{
					prize+=new_prize;
					boxes.get(col).get(row).current_effect = "bigger size";
					boxes.get(col+1).get(row).current_effect = "bigger size";
				}
			}
		}
		return prize;
	}
	
	String getCreditsString()
	{
		return "Credits: "+credits;
	}
	
	String getTotalBetString()
	{
		return "Total bet: "+getTotalBet();
	}
	
	String getLastPrizeString()
	{
		return "Last prize: "+last_prize;
	}
	
	double getTotalBet()
	{
		return lines.getValue()*bet.getValue();
	}
}

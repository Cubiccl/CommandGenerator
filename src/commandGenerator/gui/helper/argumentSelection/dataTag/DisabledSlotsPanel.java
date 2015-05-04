package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Dimension;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.CPanel;

@SuppressWarnings("serial")
public class DisabledSlotsPanel extends CPanel
{

	private CLabel hand, feet, legs, chest, head, label;
	private CCheckBox boxRemHand, boxRemHead, boxRemChest, boxRemLegs, boxRemFeet;
	private CCheckBox boxReplHand, boxReplHead, boxReplChest, boxReplLegs, boxReplFeet;
	private CCheckBox boxPlaceHand, boxPlaceHead, boxPlaceChest, boxPlaceLegs, boxPlaceFeet;

	public DisabledSlotsPanel()
	{
		super("GUI:disabledslot.title");

		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(label);
		addLine(hand, boxPlaceHand, boxReplHand, boxRemHand);
		addLine(head, boxPlaceHead, boxReplHead, boxRemHead);
		addLine(chest, boxPlaceChest, boxReplChest, boxRemChest);
		addLine(legs, boxPlaceLegs, boxReplLegs, boxRemLegs);
		addLine(feet, boxPlaceFeet, boxReplFeet, boxRemFeet);
	}

	@Override
	protected void createComponents()
	{
		label = new CLabel("GUI:disabledslot.label", true);
		label.setPreferredSize(new Dimension(600, 60));
		label.setMinimumSize(new Dimension(600, 60));

		hand = new CLabel("GUI:slot.hand");
		feet = new CLabel("GUI:slot.feet");
		legs = new CLabel("GUI:slot.legs");
		chest = new CLabel("GUI:slot.chest");
		head = new CLabel("GUI:slot.head");

		boxPlaceHand = new CCheckBox("GUI:slot.place");
		boxPlaceHead = new CCheckBox("GUI:slot.place");
		boxPlaceChest = new CCheckBox("GUI:slot.place");
		boxPlaceLegs = new CCheckBox("GUI:slot.place");
		boxPlaceFeet = new CCheckBox("GUI:slot.place");

		boxRemHand = new CCheckBox("GUI:slot.remove");
		boxRemHead = new CCheckBox("GUI:slot.remove");
		boxRemChest = new CCheckBox("GUI:slot.remove");
		boxRemLegs = new CCheckBox("GUI:slot.remove");
		boxRemFeet = new CCheckBox("GUI:slot.remove");

		boxReplHand = new CCheckBox("GUI:slot.replace");
		boxReplHead = new CCheckBox("GUI:slot.replace");
		boxReplChest = new CCheckBox("GUI:slot.replace");
		boxReplLegs = new CCheckBox("GUI:slot.replace");
		boxReplFeet = new CCheckBox("GUI:slot.replace");
	}

	@Override
	protected void createListeners()
	{}

	public int getDisabledSlots()
	{
		int flag = 0;

		if (boxRemHand.isSelected()) flag += 1;
		if (boxRemFeet.isSelected()) flag += 2;
		if (boxRemLegs.isSelected()) flag += 4;
		if (boxRemChest.isSelected()) flag += 8;
		if (boxRemHead.isSelected()) flag += 16;

		if (boxReplHand.isSelected()) flag += 256;
		if (boxReplFeet.isSelected()) flag += 512;
		if (boxReplLegs.isSelected()) flag += 1024;
		if (boxReplChest.isSelected()) flag += 2048;
		if (boxReplHead.isSelected()) flag += 4096;

		if (boxPlaceHand.isSelected()) flag += 65536;
		if (boxPlaceFeet.isSelected()) flag += 131072;
		if (boxPlaceLegs.isSelected()) flag += 262144;
		if (boxPlaceChest.isSelected()) flag += 524288;
		if (boxPlaceHead.isSelected()) flag += 1048576;

		if (flag == 1 + 2 + 4 + 8 + 16 + 256 + 65536) return 1;
		else if (boxRemHand.isSelected()) flag -= 1;

		return flag;
	}

	public void setupFrom(int value)
	{
		if (value == 1 + 2 + 4 + 8 + 16 + 256 + 65536)
		{
			boxPlaceHand.setSelected(true);
			boxRemHand.setSelected(true);
			boxReplHand.setSelected(true);
			boxRemHead.setSelected(true);
			boxRemChest.setSelected(true);
			boxRemLegs.setSelected(true);
			boxRemFeet.setSelected(true);
			return;
		}
		
		if (value - 1048576 >= 0)
		{
			value -= 1048576;
			boxPlaceHead.setSelected(true);
		}
		
		if (value - 524288 >= 0)
		{
			value -= 524288;
			boxPlaceChest.setSelected(true);
		}
		
		if (value - 262144 >= 0)
		{
			value -= 262144;
			boxPlaceLegs.setSelected(true);
		}
		
		if (value - 131072 >= 0)
		{
			value -= 131072;
			boxPlaceFeet.setSelected(true);
		}
		
		if (value - 65536 >= 0)
		{
			value -= 65536;
			boxPlaceHand.setSelected(true);
		}
		
		if (value - 4096 >= 0)
		{
			value -= 4096;
			boxReplHead.setSelected(true);
		}
		
		if (value - 2048 >= 0)
		{
			value -= 2048;
			boxReplChest.setSelected(true);
		}
		
		if (value - 1024 >= 0)
		{
			value -= 1024;
			boxReplLegs.setSelected(true);
		}
		
		if (value - 512 >= 0)
		{
			value -= 512;
			boxReplFeet.setSelected(true);
		}
		
		if (value - 256 >= 0)
		{
			value -= 256;
			boxReplHand.setSelected(true);
		}
		
		if (value - 16 >= 0)
		{
			value -= 16;
			boxRemHead.setSelected(true);
		}
		
		if (value - 8 >= 0)
		{
			value -= 8;
			boxRemChest.setSelected(true);
		}
		
		if (value - 4 >= 0)
		{
			value -= 4;
			boxRemLegs.setSelected(true);
		}
		
		if (value - 2 >= 0)
		{
			value -= 2;
			boxRemFeet.setSelected(true);
		}
	}

}

package commandGenerator.gui.helper.argumentSelection.dataTag;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.HelperPanel;

@SuppressWarnings("serial")
public class DisabledSlotsPanel extends HelperPanel
{

	private CCheckBox hand, feet, legs, chest, head;
	private CLabel label;

	public DisabledSlotsPanel()
	{
		super("GUI:disabledslot.title");
	}

	@Override
	protected void addComponents()
	{
		add(label);
		add(hand);
		add(head);
		add(chest);
		add(legs);
		add(feet);
	}

	@Override
	protected void createComponents()
	{
		label = new CLabel("GUI:disabledslot.label");

		hand = new CCheckBox("GUI:slot.hand");
		feet = new CCheckBox("GUI:slot.feet");
		legs = new CCheckBox("GUI:slot.legs");
		chest = new CCheckBox("GUI:slot.chest");
		head = new CCheckBox("GUI:slot.head");
	}

	@Override
	protected void createListeners()
	{}

	public int getDisabledSlots()
	{
		int flag = 0;
		if (!hand.isSelected()) flag += 1;
		if (!feet.isSelected()) flag += 2;
		if (!legs.isSelected()) flag += 4;
		if (!chest.isSelected()) flag += 8;
		if (!head.isSelected()) flag += 16;
		return flag;
	}

	public void setupFrom(int value)
	{
		boolean[] checked = { false, false, false, false, false };
		if (value >= 16)
		{
			value -= 16;
			checked[4] = true;
		}
		if (value >= 8)
		{
			value -= 8;
			checked[3] = true;
		}
		if (value >= 4)
		{
			value -= 4;
			checked[2] = true;
		}
		if (value >= 2)
		{
			value -= 2;
			checked[1] = true;
		}
		if (value >= 1)
		{
			value -= 1;
			checked[0] = true;
		}
		hand.setSelected(!checked[0]);
		feet.setSelected(!checked[1]);
		legs.setSelected(!checked[2]);
		chest.setSelected(!checked[3]);
		head.setSelected(!checked[4]);
	}

}

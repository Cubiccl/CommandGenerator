package commandGenerator.gui.helper.argumentSelection.dataTag;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.CPanel;

@SuppressWarnings("serial")
public class FlagSelectionPanel extends CPanel
{

	private CCheckBox enchant, attribute, unbreak, destroy, place, other;
	private CLabel label;

	public FlagSelectionPanel()
	{
		super("GUI:flag.title");
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(label);
		add(enchant);
		add(unbreak);
		add(destroy);
		add(attribute);
		add(place);
		add(other);
	}

	@Override
	protected void createComponents()
	{
		label = new CLabel("GUI:flag.select");

		enchant = new CCheckBox("GUI:flag.enchant");
		attribute = new CCheckBox("GUI:flag.attribute");
		unbreak = new CCheckBox("GUI:flag.unbreak");
		destroy = new CCheckBox("GUI:flag.destroy");
		place = new CCheckBox("GUI:flag.place");
		other = new CCheckBox("GUI:flag.other");
	}

	@Override
	protected void createListeners()
	{}

	public int getHideFlags()
	{
		int flag = 0;
		if (enchant.isSelected()) flag += 1;
		if (attribute.isSelected()) flag += 2;
		if (unbreak.isSelected()) flag += 4;
		if (destroy.isSelected()) flag += 8;
		if (place.isSelected()) flag += 16;
		if (other.isSelected()) flag += 32;
		return flag;
	}

	public void setup(int value)
	{
		boolean[] checked = { false, false, false, false, false, false };
		if (value >= 32)
		{
			checked[5] = true;
			value -= 32;
		}
		if (value >= 16)
		{
			checked[4] = true;
			value -= 16;
		}
		if (value >= 8)
		{
			checked[3] = true;
			value -= 8;
		}
		if (value >= 4)
		{
			checked[2] = true;
			value -= 4;
		}
		if (value >= 2)
		{
			checked[1] = true;
			value -= 2;
		}
		if (value >= 1)
		{
			checked[0] = true;
			value -= 1;
		}

		enchant.setSelected(checked[0]);
		attribute.setSelected(checked[1]);
		unbreak.setSelected(checked[2]);
		destroy.setSelected(checked[3]);
		place.setSelected(checked[4]);
		other.setSelected(checked[5]);

	}

}

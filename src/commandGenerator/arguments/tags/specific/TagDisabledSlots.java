package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagInt;
import commandGenerator.gui.helper.argumentSelection.dataTag.DisabledSlotsPanel;

public class TagDisabledSlots extends TagInt
{

	public TagDisabledSlots()
	{
		super("entity.DisabledSlots", "ArmorStand");
		setValue(31);
	}

	@Override
	public void askValue()
	{

		panel = new DisabledSlotsPanel();
		((DisabledSlotsPanel) panel).setupFrom(getValue());
		if (showPanel()) return;
		value = ((DisabledSlotsPanel) panel).getDisabledSlots();

	}

}

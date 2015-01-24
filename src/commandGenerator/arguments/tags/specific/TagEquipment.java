package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.EquipmentSelectionPanel;

public class TagEquipment extends TagList
{

	public TagEquipment()
	{
		super("entity.Equipment", "LIST=mob", "ArmorStand");
	}

	@Override
	public void askValue()
	{
		panel = new EquipmentSelectionPanel("TAGS:" + getId());
		((EquipmentSelectionPanel) panel).setup(getValue());
		showPanel();
		setValue(((EquipmentSelectionPanel) panel).getSelectedEquipment());
	}

}

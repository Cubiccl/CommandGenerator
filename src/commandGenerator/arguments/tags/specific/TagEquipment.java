package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.EquipmentSelectionPanel;

public class TagEquipment extends TagList
{
	private String[] slots;

	public TagEquipment(String id, String... slots)
	{
		super(id, "LIST=mob:ArmorStand");
		this.slots = slots;
	}

	@Override
	public void askValue()
	{
		this.panel = new EquipmentSelectionPanel("TAGS:" + this.getId(), slots);
		((EquipmentSelectionPanel) this.panel).setup(this.getValue());
		if (this.showPanel()) return;
		this.setValue(((EquipmentSelectionPanel) panel).getSelectedEquipment());
	}

}

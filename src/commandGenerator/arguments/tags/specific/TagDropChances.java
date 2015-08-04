package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.DropChancesSelectionPanel;

public class TagDropChances extends TagList
{
	private String[] slots;

	public TagDropChances(String id, String... slots)
	{
		super(id, "LIST=mob");
		this.slots = slots;
	}

	@Override
	public void askValue()
	{
		this.panel = new DropChancesSelectionPanel("TAGS:" + this.getId(), this.slots);
		((DropChancesSelectionPanel) this.panel).setup(this.getValue());
		if (this.showPanel()) return;
		this.setValue(((DropChancesSelectionPanel) this.panel).getDropChances());
	}

}

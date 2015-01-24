package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.TradesSelectionPanel;

public class TagOffers extends TagCompound
{

	public TagOffers()
	{
		super("entity.Offers", "Villager");
	}

	@Override
	public void askValue()
	{
		panel = new TradesSelectionPanel("TAGS:" + getId());
		showPanel();
		setValue(((TradesSelectionPanel) panel).getSelectedTrades());
	}

}

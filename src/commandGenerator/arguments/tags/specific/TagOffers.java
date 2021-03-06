package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;

public class TagOffers extends TagCompound
{

	public TagOffers()
	{
		super("entity.Offers", "Villager");
		addTag(new TagList("Recipes") {
			@Override
			public void askValue()
			{}
		});
	}

	@Override
	public void askValue()
	{
		panel = new ListSelectionPanel("TAGS:Offers", ObjectBase.TAG_TRADE);
		((ListSelectionPanel) panel).setList(((TagList) get(0)).getValue());

		if (showPanel()) return;

		clear();
		TagList value = new TagList("Recipes") {
			@Override
			public void askValue()
			{}
		};
		value.setValue(((ListSelectionPanel) panel).getList());
		addTag(value);
	}

}

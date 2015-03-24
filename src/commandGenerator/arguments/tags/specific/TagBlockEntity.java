package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;

public class TagBlockEntity extends TagCompound
{

	private Item item;

	public TagBlockEntity(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		panel = new NBTTagPanel("TAG:" + getId(), item, DataTags.blocks);
		((NBTTagPanel) panel).updateCombobox(item);
		((NBTTagPanel) panel).setupFrom(this.getValue());
		if (showPanel()) return;
		setValue(((NBTTagPanel) panel).getTagList());
	}

	public void setItem(Item item)
	{
		this.item = item;
	}

}

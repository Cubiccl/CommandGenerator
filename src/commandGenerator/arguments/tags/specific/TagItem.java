package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.main.CGConstants;

public class TagItem extends TagCompound
{

	private boolean slot;
	private String[] ids;

	public TagItem(String id, String[] usableItems, boolean slot, String... applicable)
	{
		super(id, applicable);
		this.slot = slot;
		ids = usableItems;
	}

	@Override
	public void askValue()
	{

		clear();
		ObjectBase[] items = new ObjectBase[ids.length];
		for (int i = 0; i < ids.length; i++)
			items[i] = ObjectBase.getObjectFromId(ids[i]);
		panel = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:item", items, true, slot);
		showPanel();

		if (this.slot)
		{
			int slot = ((ItemSelectionPanel) panel).getSlot();
			if (slot == -1) return;
			addTag(new TagInt().setValue(slot));
		}

		addTag(new TagString("id").setValue(((ItemSelectionPanel) panel).generateItem().getId()));
		addTag(new TagInt("Damage").setValue(((ItemSelectionPanel) panel).getDamage()));
		addTag(new TagInt("Count").setValue(((ItemSelectionPanel) panel).getCount()));
		if (!((ItemSelectionPanel) panel).getItemTag().commandStructure().substring(((ItemSelectionPanel) panel).getItemTag().getId().length() + 1).equals("{}")) addTag(((ItemSelectionPanel) panel)
				.getItemTag());

	}

	public Tag setOptions(String... items)
	{
		ids = items;
		return this;
	}

}

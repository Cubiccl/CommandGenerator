package commandGenerator.arguments.tags.specific;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;

public class TagRiding extends TagCompound
{

	public TagRiding()
	{
		super("entity.Riding", "LIST=allEntities");
	}

	@Override
	public void askValue()
	{
		panel = new NBTTagPanel("TAGS:" + getId(), Entity.entity, DataTags.entities);
		panel.setSize(800, 400);
		showPanel();
		setValue(((NBTTagPanel) panel).getTagList());
	}

}

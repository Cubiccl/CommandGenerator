package commandGenerator.arguments.tags.specific;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.main.CGConstants;

public class TagRiding extends TagCompound
{

	public TagRiding()
	{
		super("entity.Riding", "LIST=allEntities");
		setValue(new ArrayList<Tag>());
	}

	@Override
	public void askValue()
	{
		panel = new NBTTagPanel("TAGS:" + getId(), Entity.entity, DataTags.entities);
		panel.setSize(800, 400);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CGConstants.PANELID_NBT, getValue());
		((NBTTagPanel) panel).setupFrom(data);

		if (showPanel()) return;

		setValue(((NBTTagPanel) panel).getTagList());
	}

}

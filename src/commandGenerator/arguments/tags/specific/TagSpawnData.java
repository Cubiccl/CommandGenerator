package commandGenerator.arguments.tags.specific;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.main.Constants;

public class TagSpawnData extends TagCompound
{

	public TagSpawnData()
	{
		super("block.SpawnData", "mob_spawner");
	}

	@Override
	public void askValue()
	{
		panel = new JPanel();
		EntitySelectionPanel panelE = new EntitySelectionPanel(Constants.PANELID_ENTITY, "GUI:entity.title", Entity.getListNoPlayer());

		panel.add(panelE);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(Constants.PANELID_NBT, getValue());
		data.put(Constants.PANELID_ENTITY, DataTags.getObjectFromTags(getValue()));
		if (data.get(Constants.PANELID_ENTITY) instanceof Item) data.put(Constants.PANELID_ENTITY, Registerer.getObjectFromId("ArmorStand"));
		((EntitySelectionPanel) panelE).setupFrom(data);

		if (showPanel()) return;

		List<Tag> tags = panelE.getTagList();
		for (int i = 0; i < tags.size(); i++)
			if (tags.get(i).getId().equals("id")) tags.remove(i);
		tags.add(new TagString("id").setValue(panelE.getEntity().getId()));

		setValue(tags);
	}
}

package commandGenerator.arguments.tags.specific;

import java.util.List;

import javax.swing.JPanel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;

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
		EntitySelectionPanel panelE = new EntitySelectionPanel("GUI:entity.title", Entity.getListNoPlayer());

		panel.add(panelE);

		ObjectBase entity = DataTags.getObjectFromTags(getValue());
		if (entity instanceof Item) entity = Registry.getObjectFromId("ArmorStand");
		panelE.setupFrom((Entity) entity);
		panelE.setDataTags(this.getValue());

		if (showPanel()) return;

		List<Tag> tags = panelE.getTagList();
		for (int i = 0; i < tags.size(); i++)
			if (tags.get(i).getId().equals("id")) tags.remove(i);
		tags.add(new TagString("id").setValue(panelE.getEntity().getId()));

		setValue(tags);
	}
}

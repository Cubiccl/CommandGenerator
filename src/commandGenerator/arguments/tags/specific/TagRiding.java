package commandGenerator.arguments.tags.specific;

import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

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
		panel = new JPanel(new GridBagLayout());
		EntitySelectionPanel panelE = new EntitySelectionPanel(CGConstants.PANELID_ENTITY, "GUI:entity.title", Entity.getListNoPlayer());
		JLabel label = new JLabel(Lang.get("GUI:entity.riding"));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(label, gbc);
		gbc.gridy++;
		panel.add(panelE, gbc);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(CGConstants.PANELID_NBT, getValue());
		data.put(CGConstants.PANELID_ENTITY, DataTags.getObjectFromTags(getValue()));
		if (data.get(CGConstants.PANELID_ENTITY) instanceof Item) data.put(CGConstants.PANELID_ENTITY, Registerer.getObjectFromId("ArmorStand"));
		((EntitySelectionPanel) panelE).setupFrom(data);

		if (showPanel()) return;

		setValue(((EntitySelectionPanel) panelE).getTagList());
	}

}

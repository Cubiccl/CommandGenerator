package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SpawnSelectionPanel extends HelperPanel
{

	private HelpButton buttonHelp;
	private CEntry entryWeight;
	private EntitySelectionPanel panelEntity;

	public SpawnSelectionPanel()
	{
		super(Constants.PANELID_NONE, "GUI:spawn.entity");
	}

	@Override
	protected void addComponents()
	{
		addLine(entryWeight, buttonHelp);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new HelpButton(Lang.get("HELP:weight"), Lang.get("TAGS:Weight"));
		
		entryWeight = new CEntry(Constants.DATAID_NONE, "GUI:spawn.weight", "1");
		entryWeight.setTextField("1");

		panelEntity = new EntitySelectionPanel(Constants.PANELID_ENTITY, "GUI:entity.select", Entity.getListNoPlayer());
	}

	@Override
	protected void createListeners()
	{}

	public TagCompound getTag()
	{

		String weight = entryWeight.getText();
		try
		{
			int test = Integer.parseInt(weight);
			if (test < 1)
			{
				JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, Lang.get("WARNING:min").replaceAll("<min>", "1"), Lang.get("WARNING:title"), JOptionPane.WARNING_MESSAGE);
			return null;
		}

		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		tag.addTag(new TagString("Type").setValue(panelEntity.getEntity().getId()));
		tag.addTag(new TagInt("Weight").setValue(Integer.parseInt(weight)));
		tag.addTag(panelEntity.getEntityTag());
		return tag;
	}

	public void setup(TagCompound nbt)
	{
		Entity sel = (Entity) Registerer.getObjectFromId("ArmorStand");
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Type"))
			{
				panelEntity.setSelected((Entity) Registerer.getObjectFromId(((TagString) tag).getValue()));
				sel = (Entity) Registerer.getObjectFromId(((TagString) tag).getValue());
			}
			if (tag.getId().equals("Weight")) entryWeight.setTextField(Integer.toString(((TagInt) tag).getValue()));
			if (tag.getId().equals("Properties"))
			{
				System.out.println(sel.getName());
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(Constants.PANELID_ENTITY, sel);
				data.put(Constants.PANELID_NBT, ((TagCompound) tag).getValue());
				panelEntity.setupFrom(data);
			}
		}
	}
}

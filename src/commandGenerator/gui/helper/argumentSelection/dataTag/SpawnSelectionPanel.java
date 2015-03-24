package commandGenerator.gui.helper.argumentSelection.dataTag;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SpawnSelectionPanel extends HelperPanel
{

	private HelpButton buttonHelp;
	private CEntry entryWeight;
	private EntitySelectionPanel panelEntity;

	public SpawnSelectionPanel()
	{
		super("GUI:spawn.entity");
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

		entryWeight = new CEntry("GUI:spawn.weight", "1");
		entryWeight.setTextField("1");

		panelEntity = new EntitySelectionPanel("GUI:entity.select", Entity.getListNoPlayer());
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
			@Override
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
		Entity sel = (Entity) Registry.getObjectFromId("ArmorStand");
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Type"))
			{
				panelEntity.setSelected((Entity) Registry.getObjectFromId(((TagString) tag).getValue()));
				sel = (Entity) Registry.getObjectFromId(((TagString) tag).getValue());
			}
			if (tag.getId().equals("Weight")) entryWeight.setTextField(Integer.toString(((TagInt) tag).getValue()));
			if (tag.getId().equals("Properties"))
			{
				panelEntity.setupFrom(sel);
				panelEntity.setDataTags(((TagCompound) tag).getValue());
			}
		}
	}
}

package commandGenerator.gui.helper.argumentSelection.dataTag;

import javax.swing.JOptionPane;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagInt;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.panel.CPanel;

@SuppressWarnings("serial")
public class SpawnSelectionPanel extends CPanel
{

	private HelpButton buttonHelp;
	private CEntry entryWeight;
	private EntitySelectionPanel panelEntity;

	public SpawnSelectionPanel()
	{
		super("GUI:spawn.entity");
		
		this.initGui();
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
		buttonHelp = new HelpButton();
		buttonHelp.setData(Generator.translate("HELP:weight"), Generator.translate("TAGS:Weight"));

		entryWeight = new CEntry("GUI:spawn.weight", "1");
		entryWeight.setTextField("1");

		panelEntity = new EntitySelectionPanel("GUI:entity.select", Generator.registry.getListNoPlayer());
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
				JOptionPane.showMessageDialog(null, Generator.translate("WARNING:min").replaceAll("<min>", "1"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
				return null;
			}
		} catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, Generator.translate("WARNING:min").replaceAll("<min>", "1"), Generator.translate("WARNING:title"), JOptionPane.WARNING_MESSAGE);
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
		Entity sel = (Entity) Generator.registry.getObjectFromId("ArmorStand");
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("Type"))
			{
				panelEntity.setSelected((Entity) Generator.registry.getObjectFromId(((TagString) tag).getValue()));
				sel = (Entity) Generator.registry.getObjectFromId(((TagString) tag).getValue());
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

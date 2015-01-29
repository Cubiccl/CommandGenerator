package commandGenerator.gui.options;

import javax.swing.JLabel;

import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.dataTag.NBTTagPanel;
import commandGenerator.gui.helper.components.CComboBox;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EntityDataOptionsPanel extends OptionsPanel implements IBox
{

	private String command;
	private CLabel labelExplain;
	private JLabel label;
	private CComboBox comboboxEntity;
	private EntitySelectionPanel panelEntity;
	private NBTTagPanel panelEntitydata;

	public EntityDataOptionsPanel(String command)
	{
		super();
		this.command = command;

		updateCombobox();
	}

	@Override
	public String generateCommand()
	{
		Target entity = panelEntity.generateEntity();
		TagCompound tag = panelEntitydata.getNbtTags("tag");
		String commandG = command + " ";

		if (entity == null || tag == null) return null;

		return commandG + entity.commandStructure() + " " + tag.commandStructure().substring(tag.getId().length() + 1);
	}

	@Override
	public void updateCombobox()
	{
		panelEntitydata.updateCombobox((Entity) comboboxEntity.getValue());
	}

	@Override
	protected void createComponents()
	{
		label = new JLabel(((Entity) Registerer.getObjectFromId("Item")).getTexture());
		labelExplain = new CLabel("GUI:entity.explain", true);

		comboboxEntity = new CComboBox(CGConstants.DATAID_ENTITY, "GUI:entity.select", Registerer.getObjectList(CGConstants.OBJECT_ENTITY), this);

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelEntitydata = new NBTTagPanel("GUI:entity.tags", Entity.player, DataTags.entities);
	}

	@Override
	protected void addComponents()
	{
		add(labelExplain);
		add(comboboxEntity);
		add(label);
		add(panelEntity);
		add(panelEntitydata);
	}

	@Override
	protected void createListeners()
	{}

}

package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EntityDataOptionsPanel extends OptionsPanel
{

	private String command;
	private CLabel labelExplain;
	private TargetSelectionPanel panelSelector;
	private EntitySelectionPanel panelEntity;

	public EntityDataOptionsPanel(String command)
	{
		//super(command);
	}

	@Override
	protected void addComponents()
	{
		add(panelSelector);
		add(labelExplain);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		labelExplain = new CLabel("GUI:entity.explain", true);

		panelSelector = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_ENTITY, "GUI:entity.title", Registry.getObjectList(CGConstants.OBJECT_ENTITY));
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Target entity = panelSelector.generateEntity();
		TagCompound tag = panelEntity.getEntityTag();
		String commandG = command + " ";

		if (entity == null || tag == null) return null;

		return commandG + entity.commandStructure() + " " + tag.commandStructure().substring(tag.getId().length() + 1);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		this.command = (String) details[0];
	}

}

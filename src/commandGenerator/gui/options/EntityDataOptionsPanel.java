package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class EntityDataOptionsPanel extends OptionsPanel
{

	private String command;
	private CLabel labelExplain;
	private TargetSelectionPanel panelSelector;
	private EntitySelectionPanel panelEntity;

	public EntityDataOptionsPanel(String command)
	{
		super(command);
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

		panelSelector = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);
		panelEntity = new EntitySelectionPanel(Constants.PANELID_ENTITY, "GUI:entity.title", Registerer.getObjectList(Constants.OBJECT_ENTITY));
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

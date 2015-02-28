package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Entity;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SummonOptionsPanel extends OptionsPanel
{

	private CoordSelectionPanel panelCoord;
	private EntitySelectionPanel panelEntity;

	public SummonOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelCoord);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:summon.coords", true, false);
		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_ENTITY, "GUI:entity.title", Entity.getListNoPlayer());
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{
		Coordinates coord = panelCoord.generateCoord();
		TagCompound tag = panelEntity.getEntityTag();
		Entity entity = (Entity) panelEntity.getEntity();

		if (coord == null) return null;
		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) return "summon " + entity.getId() + " " + coord.commandStructure() + " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);
		return "summon " + entity.getId() + " " + coord.commandStructure();
	}

}

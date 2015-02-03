package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SpawnpointOptionsPanel extends OptionsPanel
{

	private CoordSelectionPanel panelCoords;
	private EntitySelectionPanel panelEntity;

	public SpawnpointOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelEntity);
		add(panelCoords);
	}

	@Override
	protected void createComponents()
	{
		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_ALL);
		panelCoords = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:spawnpoint.coords", false, false);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateCommand()
	{

		Target player = panelEntity.generateEntity();
		Coordinates coords = panelCoords.generateCoord();

		if (coords == null || player == null) return null;

		return "spawnpoint " + player.commandStructure() + " " + coords.commandStructure();
	}

}

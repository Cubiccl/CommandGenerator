package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SpawnpointOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelEntity;
	private CoordSelectionPanel panelCoords;

	public SpawnpointOptionsPanel()
	{
		super();

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_ALL);
		panelCoords = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:spawnpoint.coords", false, false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelEntity, gbc);
		gbc.gridy++;
		add(panelCoords, gbc);
	}

	@Override
	public String generateCommand()
	{

		Target player = panelEntity.generateEntity();
		Coordinates coords = panelCoords.generateCoord();

		if (coords == null || player == null) return null;

		return "spawnpoint " + player.commandStructure() + " " + coords.commandStructure();
	}

}

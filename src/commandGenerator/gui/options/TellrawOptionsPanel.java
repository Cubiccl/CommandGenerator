package commandGenerator.gui.options;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.json.JsonMessageSelectionPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TellrawOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelPlayer;
	private JsonMessageSelectionPanel panelJson;

	public TellrawOptionsPanel()
	{
		super();

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelJson = new JsonMessageSelectionPanel("GUI:json.text");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPlayer, gbc);
		gbc.gridy++;
		add(panelJson, gbc);
	}

	@Override
	public String generateCommand()
	{

		EntitySelector player = panelPlayer.generateEntity();

		if (player == null) return null;
		if (panelJson.generateMessage() == null) return null;

		return "tellraw " + player.commandStructure() + " " + panelJson.generateMessage().commandStructure();
	}

}

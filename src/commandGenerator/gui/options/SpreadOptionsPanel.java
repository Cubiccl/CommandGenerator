package commandGenerator.gui.options;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.commandSpecific.SpreadPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SpreadOptionsPanel extends OptionsPanel
{

	private SpreadPanel panelSpreadOptions;
	private EntitySelectionPanel panelPlayer;

	public SpreadOptionsPanel()
	{
		super();

		panelSpreadOptions = new SpreadPanel("GENERAL:options");
		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelSpreadOptions, gbc);
		gbc.gridy++;
		add(panelPlayer, gbc);
	}

	@Override
	public String generateCommand()
	{
		EntitySelector player = panelPlayer.generateEntity();
		String[] options = panelSpreadOptions.generateOptions();

		if (player == null || options == null) return null;

		String command = "spreadplayers";

		for (int i = 0; i < options.length; i++)
		{
			command += " " + options[i];
		}

		command += " " + player.commandStructure();

		return command;
	}

}

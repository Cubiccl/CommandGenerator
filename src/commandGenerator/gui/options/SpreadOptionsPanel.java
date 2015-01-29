package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.commandSpecific.SpreadPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
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
		add(panelSpreadOptions);
		gbc.gridy++;
		add(panelPlayer);
	}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();
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

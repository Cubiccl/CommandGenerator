package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.commandSpecific.SpreadPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class SpreadOptionsPanel extends OptionsPanel
{

	private TargetSelectionPanel panelPlayer;
	private SpreadPanel panelSpreadOptions;

	public SpreadOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelSpreadOptions);
		add(panelPlayer);
	}

	@Override
	protected void createComponents()
	{
		panelSpreadOptions = new SpreadPanel("GENERAL:options");
		panelPlayer = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
	}

	@Override
	protected void createListeners()
	{}

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

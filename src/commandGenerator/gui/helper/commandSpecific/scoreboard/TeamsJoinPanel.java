package commandGenerator.gui.helper.commandSpecific.scoreboard;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsJoinPanel extends ScoreboardPanel
{

	private CLabel labelTeam;
	private EntitySelectionPanel panelPlayer;
	private JTextField textfieldTeam;

	public TeamsJoinPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelTeam, textfieldTeam);
		add(panelPlayer);
	}

	@Override
	protected void createComponents()
	{
		labelTeam = new CLabel("GUI:scoreboard.team");

		textfieldTeam = new JTextField(15);

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateText()
	{
		Target player = panelPlayer.generateEntity();

		if (textfieldTeam.getText().equals("") || textfieldTeam.getText().contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}
		if (player == null) return null;

		return textfieldTeam.getText() + " " + player.commandStructure();
	}

}

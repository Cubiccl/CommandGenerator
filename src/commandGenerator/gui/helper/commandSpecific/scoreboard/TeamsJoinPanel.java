package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;

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
	private JTextField textfieldTeam;
	private EntitySelectionPanel panelPlayer;

	public TeamsJoinPanel()
	{
		super();
		setPreferredSize(new Dimension(500, 300));

		labelTeam = new CLabel("GUI:scoreboard.team");

		textfieldTeam = new JTextField(15);

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelTeam);
		gbc.gridx++;
		add(textfieldTeam);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelPlayer);
		gbc.gridwidth = 1;
	}

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

package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.ObjectivesAddPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.ObjectivesRemovePanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.ObjectivesSetdisplayPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersAddPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersEnablePanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersOperationPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersRemovePanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersResetPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersSetPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.PlayersTestPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.ScoreboardPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsAddPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsEmptyPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsJoinPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsLeavePanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsOptionPanel;
import commandGenerator.gui.helper.commandSpecific.scoreboard.TeamsRemovePanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ScoreboardOptionsPanel extends OptionsPanel
{

	private static final String[][] scoreboardModes = { { "objectives", "add", "remove", "setdisplay" },
			{ "players", "set", "add", "remove", "reset", "enable", "test", "operation" }, { "teams", "add", "remove", "empty", "join", "leave", "option" } };

	private JButton buttonHelp;
	private LangComboBox comboboxMode1, comboboxMode2;
	private ScoreboardPanel panelScore;

	public ScoreboardOptionsPanel()
	{
		super();

		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DisplayHelper.showHelp(
						Lang.get("HELP:scoreboard." + scoreboardModes[comboboxMode1.getSelectedIndex()][0] + "_" + comboboxMode2.getSelectedIndex()),
						(String) comboboxMode1.getSelectedItem() + " " + (String) comboboxMode2.getSelectedItem());
			}
		});

		comboboxMode1 = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:scoreboard.mode", 3);
		comboboxMode1.setPreferredSize(new Dimension(200, 20));
		comboboxMode1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (comboboxMode1.getSelectedIndex() == 0) comboboxMode2.setText("RESOURCES:scoreboard.mode.objectives", 3);
				else if (comboboxMode1.getSelectedIndex() == 1) comboboxMode2.setText("RESOURCES:scoreboard.mode.players", 7);
				else if (comboboxMode1.getSelectedIndex() == 2) comboboxMode2.setText("RESOURCES:scoreboard.mode.teams", 6);

				setupPanel();
			}
		});
		comboboxMode2 = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:scoreboard.mode.objectives", 3);
		comboboxMode2.setPreferredSize(new Dimension(200, 20));
		comboboxMode2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setupPanel();
			}
		});

		panelScore = new ObjectivesAddPanel();

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode1, gbc);
		gbc.gridx++;
		add(comboboxMode2, gbc);
		gbc.gridx++;
		add(buttonHelp, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		add(panelScore, gbc);
		gbc.gridwidth = 1;
	}

	@Override
	public String generateCommand()
	{
		if (panelScore.generateText() == null) return null;
		return "scoreboard " + scoreboardModes[comboboxMode1.getSelectedIndex()][0] + " "
				+ scoreboardModes[comboboxMode1.getSelectedIndex()][comboboxMode2.getSelectedIndex() + 1] + " " + panelScore.generateText();
	}

	private void setupPanel()
	{

		panelScore.setVisible(false);
		remove(panelScore);

		if (comboboxMode1.getSelectedIndex() == 0 && comboboxMode2.getSelectedIndex() == 0) panelScore = new ObjectivesAddPanel();
		else if (comboboxMode1.getSelectedIndex() == 0 && comboboxMode2.getSelectedIndex() == 1) panelScore = new ObjectivesRemovePanel();
		else if (comboboxMode1.getSelectedIndex() == 0 && comboboxMode2.getSelectedIndex() == 2) panelScore = new ObjectivesSetdisplayPanel();

		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 0) panelScore = new PlayersSetPanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 1) panelScore = new PlayersAddPanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 2) panelScore = new PlayersRemovePanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 3) panelScore = new PlayersResetPanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 4) panelScore = new PlayersEnablePanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 5) panelScore = new PlayersTestPanel();
		else if (comboboxMode1.getSelectedIndex() == 1 && comboboxMode2.getSelectedIndex() == 6) panelScore = new PlayersOperationPanel();

		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 0) panelScore = new TeamsAddPanel();
		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 1) panelScore = new TeamsRemovePanel();
		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 2) panelScore = new TeamsEmptyPanel();
		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 3) panelScore = new TeamsJoinPanel();
		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 4) panelScore = new TeamsLeavePanel();
		else if (comboboxMode1.getSelectedIndex() == 2 && comboboxMode2.getSelectedIndex() == 5) panelScore = new TeamsOptionPanel();

		panelScore.setVisible(true);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		add(panelScore, gbc);
		gbc.gridwidth = 1;
	}

}

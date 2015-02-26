package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class TeamsOptionPanel extends ScoreboardPanel
{
	public static final String[] scoreboardTeamsOptionList = { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" },
			visibilityList = { "never", "hideForOtherTeams", "hideForOwnTeam", "always" };

	private LangComboBox comboboxMode, comboboxValue;
	private CEntry entryTeam;
	private CLabel labelMode, labelValue;

	public TeamsOptionPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryTeam);
		addLine(labelMode, comboboxMode);
		addLine(labelValue, comboboxValue);
	}

	@Override
	protected void createComponents()
	{
		labelMode = new CLabel("GUI:scoreboard.teams.option.option");
		labelValue = new CLabel("GUI:scoreboard.teams.option.value");

		entryTeam = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.team", "");

		comboboxMode = new LangComboBox(CGConstants.DATAID_CHECK, "RESOURCES:scoreboard.teams.option", 5);
		comboboxValue = new LangComboBox(CGConstants.DATAID_VALUE, "RESOURCES:color", 17);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (comboboxMode.getSelectedIndex() == 0) comboboxValue.setText("RESOURCES:color", 17);
				if (comboboxMode.getSelectedIndex() == 1 || comboboxMode.getSelectedIndex() == 2) comboboxValue.setModel(new JComboBox<String>(new String[] {
						Lang.get("GENERAL:true"), Lang.get("GENERAL:false") }).getModel());
				if (comboboxMode.getSelectedIndex() >= 3) comboboxValue.setText("RESOURCES:scoreboard.teams.visibility", 4);
			}
		});
	}

	@Override
	public String generateText()
	{

		if (entryTeam.getText().equals("") || entryTeam.getText().contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		String value = "";

		if (scoreboardTeamsOptionList[comboboxMode.getSelectedIndex()].equals("color")) value = Resources.colors[comboboxValue.getSelectedIndex()];
		else if (scoreboardTeamsOptionList[comboboxMode.getSelectedIndex()].equals("friendlyfire")) value = Boolean
				.toString(comboboxValue.getSelectedIndex() == 0);
		else value = visibilityList[comboboxValue.getSelectedIndex()];

		return entryTeam.getText() + " " + scoreboardTeamsOptionList[comboboxMode.getSelectedIndex()] + " " + value;
	}

}

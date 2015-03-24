package commandGenerator.gui.helper.argumentSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class TeamsOptionPanel extends HelperPanel
{
	public static final String[] scoreboardTeamsOptionList = { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" },
			visibilityList = { "never", "hideForOtherTeams", "hideForOwnTeam", "always" };

	private LangComboBox comboboxMode, comboboxValue;
	private CEntry entryTeam;
	private CLabel labelMode, labelValue;

	public TeamsOptionPanel()
	{
		super("GENERAL:options");
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

		entryTeam = new CEntry("GUI:scoreboard.team", "");

		comboboxMode = new LangComboBox("RESOURCES:scoreboard.teams.option", 5);
		comboboxValue = new LangComboBox("RESOURCES:color", 17);
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

	public void setupFrom(String mode, String value)
	{
		if (mode.equals("color"))
		{
			this.comboboxMode.setSelectedIndex(0);
			for (int i = 0; i < Resources.colors.length; i++)
				if (value.equals(Resources.colors[i])) this.comboboxValue.setSelectedIndex(i);
		} else if (mode.equals("friendlyfire") || mode.equals("seeFriendlyInvisibles"))
		{
			this.comboboxMode.setSelectedIndex(1);
			if (value.equals("false")) this.comboboxValue.setSelectedIndex(1);
		} else {
			this.comboboxMode.setSelectedIndex(2);
			for (int i = 0; i < visibilityList.length; i++) if (value.equals(visibilityList[i])) this.comboboxValue.setSelectedIndex(i);
		}
	}

}

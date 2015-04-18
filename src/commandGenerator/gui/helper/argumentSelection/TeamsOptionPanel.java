package commandGenerator.gui.helper.argumentSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TeamsOptionPanel extends HelperPanel
{
	public static final String[] scoreboardTeamsOptionList = { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" },
			visibilityList = { "never", "hideForOtherTeams", "hideForOwnTeam", "always" }, colors = { "black", "dark_blue", "dark_green", "dark_aqua",
					"dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white", "reset" };;

	private ChoiceComboBox comboboxMode, comboboxValue;
	private CEntry entryTeam;
	private CLabel labelMode, labelValue;

	public TeamsOptionPanel()
	{
		super("GENERAL:options");
		
		this.initGui();
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

		comboboxMode = new ChoiceComboBox("scoreboard.teams.option", scoreboardTeamsOptionList, false);
		comboboxValue = new ChoiceComboBox("color", colors, false);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String mode = comboboxMode.getSelectedValue();
				if (mode.equals("color")) comboboxValue.setData("color", colors);
				else if (mode.equals("friendlyfire") || mode.equals("seeFriendlyInvisibles")) comboboxValue.setData("value", new String[] { "true", "false" });
				else comboboxValue.setData("scoreboard.visibility", visibilityList);
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

		return entryTeam.getText() + " " + this.comboboxMode.getSelectedValue() + " " + this.comboboxValue.getSelectedValue();
	}

	public void setupFrom(String mode, String value)
	{
		this.comboboxMode.setSelected(mode);
		this.comboboxValue.setSelected(value);
	}

}

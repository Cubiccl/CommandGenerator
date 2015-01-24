package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersEnablePanel extends ScoreboardPanel
{

	private CEntry entryObjective;
	private EntitySelectionPanel panelEntity;

	public PlayersEnablePanel()
	{
		super();
		setPreferredSize(new Dimension(500, 300));

		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryObjective, gbc);
		gbc.gridy++;
		add(panelEntity, gbc);
	}

	@Override
	public String generateText()
	{

		EntitySelector player = panelEntity.generateEntity();
		String objective = entryObjective.getText();

		if (objective.equals("") || objective.contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		if (player == null) return null;

		return player.commandStructure() + " " + objective;

	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryObjective.updateLang();
		panelEntity.updateLang();
	}

}

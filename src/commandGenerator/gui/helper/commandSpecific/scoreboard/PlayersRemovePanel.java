package commandGenerator.gui.helper.commandSpecific.scoreboard;

import java.awt.Dimension;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersRemovePanel extends ScoreboardPanel
{

	private CEntry entryObjective, entryScore;
	private EntitySelectionPanel panelEntity;

	public PlayersRemovePanel()
	{
		super();
		setPreferredSize(new Dimension(500, 300));

		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");
		entryScore = new CEntry(CGConstants.DATAID_VALUE, "GUI:scoreboard.score");

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryObjective);
		gbc.gridy++;
		add(entryScore);
		gbc.gridy++;
		add(panelEntity);
	}

	@Override
	public String generateText()
	{

		Target player = panelEntity.generateEntity();
		String objective = entryObjective.getText(), score = entryScore.getText();

		if (objective.equals("") || objective.contains(" "))
		{
			DisplayHelper.warningName();
			return null;
		}

		try
		{
			int test = Integer.parseInt(score);
			if (test < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		if (player == null) return null;

		return player.commandStructure() + " " + objective + " " + score;

	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryObjective.updateLang();
		entryScore.updateLang();
		panelEntity.updateLang();
	}

}

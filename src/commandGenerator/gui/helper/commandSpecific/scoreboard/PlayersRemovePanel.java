package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersRemovePanel extends ScoreboardPanel
{

	private CEntry entryObjective, entryScore;
	private TargetSelectionPanel panelEntity;

	public PlayersRemovePanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(entryObjective);
		add(entryScore);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");
		entryScore = new CEntry(CGConstants.DATAID_VALUE, "GUI:scoreboard.score");

		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{}

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

}

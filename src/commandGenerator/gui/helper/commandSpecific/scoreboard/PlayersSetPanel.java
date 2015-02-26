package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class PlayersSetPanel extends ScoreboardPanel
{

	private CEntry entryObjective, entryScore;
	private TargetSelectionPanel panelEntity;

	public PlayersSetPanel()
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
		entryObjective = new CEntry(Constants.DATAID_NAME, "GUI:scoreboard.objective", "");
		entryScore = new CEntry(Constants.DATAID_VALUE, "GUI:scoreboard.score", "1");

		panelEntity = new TargetSelectionPanel(Constants.PANELID_TARGET, "GENERAL:target.entity", Constants.ENTITIES_ALL);
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
			@SuppressWarnings("unused")
			int test = Integer.parseInt(score);
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		if (player == null) return null;

		return player.commandStructure() + " " + objective + " " + score;

	}

}

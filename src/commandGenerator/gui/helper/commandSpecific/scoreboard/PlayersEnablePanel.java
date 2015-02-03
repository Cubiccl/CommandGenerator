package commandGenerator.gui.helper.commandSpecific.scoreboard;

import commandGenerator.arguments.objects.Target;
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
	}

	@Override
	protected void addComponents()
	{
		add(entryObjective);
		add(panelEntity);
	}

	@Override
	protected void createComponents()
	{
		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective");

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{}

	@Override
	public String generateText()
	{

		Target player = panelEntity.generateEntity();
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

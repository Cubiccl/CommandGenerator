package commandGenerator.gui.helper.argumentSelection.json;

import commandGenerator.arguments.objects.EntitySelector;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ScoreDisplayPanel extends HelperPanel
{

	private CEntry entryObjective;
	private EntitySelectionPanel panelEntity;

	public ScoreDisplayPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 500, 300);

		entryObjective = new CEntry(CGConstants.DATAID_NONE, "GUI:scoreboard.objective");

		panelEntity = new EntitySelectionPanel(CGConstants.DATAID_NONE, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryObjective, gbc);
		gbc.gridy++;
		add(panelEntity, gbc);
	}

	public TagCompound generateScore()
	{

		String objective = entryObjective.getText();
		EntitySelector entity = panelEntity.generateEntity();

		if (objective == null || objective.equals(""))
		{
			DisplayHelper.showWarning("objective");
			return null;
		}
		if (entity == null) return null;

		TagCompound tag = new TagCompound("score") {
			public void askValue()
			{}
		};
		tag.addTag(new TagString("name").setValue(entity.display()));
		tag.addTag(new TagString("objective").setValue(objective));

		return tag;
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryObjective.updateLang();
		panelEntity.updateLang();
	}

}

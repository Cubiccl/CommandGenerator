package commandGenerator.gui.helper.argumentSelection.json;

import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.Tag;
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

		panelEntity = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.ENTITIES_ALL);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryObjective);
		gbc.gridy++;
		add(panelEntity);
	}

	public TagCompound generateScore()
	{

		String objective = entryObjective.getText();
		Target entity = panelEntity.generateEntity();

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
		tag.addTag(new TagString("name").setValue(entity.commandStructure()));
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

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("objective")) entryObjective.setTextField(((TagString) tag).getValue());
			if (tag.getId().equals("name"))
			{
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(CGConstants.PANELID_TARGET, Target.generateFrom(((TagString) tag).getValue()));
				panelEntity.setupFrom(data);
			}
		}
	}
}

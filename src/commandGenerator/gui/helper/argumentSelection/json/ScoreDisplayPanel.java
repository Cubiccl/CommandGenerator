package commandGenerator.gui.helper.argumentSelection.json;

import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ScoreDisplayPanel extends CPanel
{

	private CEntry entryObjective;
	private TargetSelectionPanel panelEntity;

	public ScoreDisplayPanel(String title)
	{
		super(title);
		
		this.initGui();
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
		entryObjective = new CEntry("GUI:scoreboard.objective", "");

		panelEntity = new TargetSelectionPanel("GENERAL:target.entity", CGConstants.ENTITIES_ALL);
	}

	@Override
	protected void createListeners()
	{}

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
			@Override
			public void askValue()
			{}
		};
		tag.addTag(new TagString("name").setValue(entity.commandStructure()));
		tag.addTag(new TagString("objective").setValue(objective));

		return tag;
	}

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("objective")) entryObjective.setTextField(((TagString) tag).getValue());
			if (tag.getId().equals("name")) panelEntity.setupFrom(ObjectCreator.generateTarget(((TagString) tag).getValue()));
		}
	}
}

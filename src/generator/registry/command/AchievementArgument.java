package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.panel.object.PanelAchievement;
import generator.main.GenerationException;
import generator.main.Text;
import generator.main.Utils;
import generator.registry.Achievement;

import java.awt.Component;

/** An Achievement. */
public class AchievementArgument extends Argument
{
	/** The panel to select the Achievement. */
	private PanelAchievement panel;

	/** Creates a new Achievement Argument. */
	public AchievementArgument()
	{
		super(true, 1);
	}

	@Override
	public void createGui()
	{
		this.panel = new PanelAchievement();
	}

	@Override
	protected String generateValue() throws GenerationException
	{
		return this.panel.generateAchievement().getId();
	}

	@Override
	public Component getComponent()
	{
		return this.panel;
	}

	@Override
	public void updateLang()
	{
		if (this.panel != null) this.panel.updateLang();
	}

	@Override
	protected void verifyValue(String value) throws GenerationException
	{
		for (Achievement achievement : CommandGenerator.getRegistry().getAchievements())
		{
			if (achievement.getId().equals(value)) return;
		}
		throw new GenerationException(new Text("GUI", "error.id", false).addReplacement("<value>", value).addReplacement("<object>", Utils.getObjectTypeName(Utils.ACHIEVEMENT)));
	}
}

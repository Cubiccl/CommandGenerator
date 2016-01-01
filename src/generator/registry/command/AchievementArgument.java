package generator.registry.command;

import generator.gui.panel.object.PanelAchievement;
import generator.main.GenerationException;

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
	public String generate() throws GenerationException
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

}

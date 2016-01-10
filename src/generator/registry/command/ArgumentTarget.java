package generator.registry.command;

import generator.gui.panel.object.PanelTarget;
import generator.main.GenerationException;
import generator.main.Text;

import java.awt.Component;

/** Entities to target. */
public class ArgumentTarget extends Argument
{
	/** The target types :
	 * <ul>
	 * <li>ALL = 0;</li>
	 * <li>PLAYERS = 1;</li>
	 * <li>ENTITIES = 2;</li>
	 * </ul> */
	public static final int ALL = 0, PLAYERS = 1, ENTITIES = 2;

	/** The GUI */
	private PanelTarget panel;
	/** Type of the target
	 * 
	 * @see ArgumentTarget#ALL */
	private int targetType;
	/** The ID of the text for the title of the GUI */
	private Text title;

	/** Creates a new ArgumentTarget.
	 * 
	 * @param isCompulsory - True if it is compulsory.
	 * @param title - The ID of the text for the title of the GUI.
	 * @param targetType - The type of the target.
	 * @see ArgumentTarget#ALL */
	public ArgumentTarget(boolean isCompulsory, Text title, int targetType)
	{
		super(isCompulsory, 1);
		this.title = title;
		this.targetType = targetType;
	}

	@Override
	public void createGui()
	{
		this.panel = new PanelTarget(this.title, this.targetType);
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.panel.generateTarget().toCommand();
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

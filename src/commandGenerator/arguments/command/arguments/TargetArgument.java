package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;

public class TargetArgument extends Argument
{
	/** What kind of Target can be selected. */
	private final int mode;
	/** This Argument's panel. */
	private TargetSelectionPanel panel;

	public TargetArgument(String id, boolean isCompulsery, int mode)
	{
		super(id, isCompulsery);
		this.mode = mode;
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateEntity().commandStructure();
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		String title = "GUI:" + this.getId();
		if (this.getId().startsWith("target")) title = "GENERAL:" + this.getId();
		this.panel = new TargetSelectionPanel(title, this.mode);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return true;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setupFrom(ObjectCreator.generateTarget(data.get(0)));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof TargetArgument)) return;
		this.panel.setupFrom(((TargetArgument) toMatch).panel.generateEntity());
	}

}

package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;

public class TargetArgument extends Argument
{
	private int mode;
	private TargetSelectionPanel panel;

	public TargetArgument(String id, boolean isCompulsery, int mode)
	{
		super(id, Argument.TARGET, isCompulsery, 1);
		this.mode = mode;
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateEntity().commandStructure();
	}

	@Override
	public void initGui()
	{
		String title = "GUI:" + this.getId();
		if (this.getId().startsWith("target")) title = "GENERAL:" + this.getId();
		this.panel = new TargetSelectionPanel(this.getId(), title, this.mode);
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

}

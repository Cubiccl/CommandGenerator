package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.argumentSelection.ParticleSelectionPanel;

public class ParticleArgument extends Argument
{

	private ParticleSelectionPanel panel;

	public ParticleArgument()
	{
		super("particle", Argument.PARTICLE, true, 1);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new ParticleSelectionPanel();
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateParticleId();
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

}

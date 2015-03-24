package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.ParticleSelectionPanel;
import commandGenerator.main.CGConstants;

public class ParticleArgument extends Argument
{

	private ParticleSelectionPanel panel;

	public ParticleArgument()
	{
		super("particle", Argument.PARTICLE, true, 1);
	}

	@Override
	public String generateCommand()
	{
		return this.panel.generateParticleId();
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
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		return Registry.exists(data.get(0), CGConstants.OBJECT_PARTICLE);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setSelected((Particle) Registry.getObjectFromId(data.get(0)));
	}

}

package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Particle;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.ParticleSelectionPanel;

public class ParticleArgument extends Argument
{

	private ParticleSelectionPanel panel;

	public ParticleArgument()
	{
		super("particle");
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
		return Registry.exists(data.get(0), ObjectBase.PARTICLE);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setSelected((Particle) Registry.getObjectFromId(data.get(0)));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof ParticleArgument)) return;
		this.panel.setSelected(((ParticleArgument) toMatch).panel.generateParticle());
	}

}

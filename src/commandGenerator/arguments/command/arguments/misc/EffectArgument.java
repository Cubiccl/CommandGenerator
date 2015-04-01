package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;

public class EffectArgument extends Argument
{
	private EffectSelectionPanel panel;

	public EffectArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.setMaximumLength(4);
	}

	@Override
	public String generateCommand()
	{
		Effect effect = this.panel.generateEffect();
		if (effect == null) return null;
		else return effect.commandStructure();
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EffectSelectionPanel("GENERAL:effect");
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		boolean ok = true;
		if (data.size() > 0) ok = Registry.exists(data.get(0), ObjectBase.EFFECT);
		if (data.size() > 1)
		{
			try
			{
				Integer.parseInt(data.get(1));
			} catch (Exception e)
			{
				ok = false;
			}
		}
		if (data.size() > 2)
		{
			try
			{
				Integer.parseInt(data.get(2));
			} catch (Exception e)
			{
				ok = false;
			}
		}
		if (data.size() > 3)
		{
			try
			{
				Boolean.parseBoolean(data.get(3));
			} catch (Exception e)
			{
				ok = false;
			}
		}
		return ok;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		String id = data.get(0);
		int duration = 0, level = 0;
		boolean particles = false;
		if (data.size() > 1) duration = Integer.parseInt(data.get(1));
		if (data.size() > 2) level = Integer.parseInt(data.get(2));
		if (data.size() > 3) particles = Boolean.parseBoolean(data.get(3));
		this.panel.setupFrom(new Effect((EffectType) Registry.getObjectFromId(id), level, duration, !particles));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof EffectArgument)) return;
		this.panel.setupFrom(((EffectArgument) toMatch).panel.generateEffect());
	}

}

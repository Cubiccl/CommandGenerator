package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Effect;
import commandGenerator.arguments.objects.EffectType;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.EffectSelectionPanel;
import commandGenerator.main.CGConstants;

public class EffectArgument extends Argument
{
	private EffectSelectionPanel panel;

	public EffectArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.EFFECT, isCompulsery, 1);
		this.setMaximumLength(4);
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EffectSelectionPanel(this.getId(), "GENERAL:effect");
	}

	@Override
	public String generateCommand()
	{
		Effect effect = this.panel.generateEffect();
		if (effect == null) return null;
		else return effect.commandStructure();
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
		if (data.size() > 0) ok = Registry.exists(data.get(0), CGConstants.OBJECT_EFFECT);
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

}

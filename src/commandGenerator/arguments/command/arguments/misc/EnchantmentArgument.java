package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;

public class EnchantmentArgument extends Argument
{

	private EnchantSelectionPanel panel;

	public EnchantmentArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.NORMAL, isCompulsery, 2);
	}

	@Override
	public String generateCommand()
	{
		Enchantment enchant = this.panel.generateEnchantment();
		if (enchant == null) return null;
		return enchant.getEnchantType().getId() + " " + enchant.getLevel();
	}

	@Override
	public Component generateComponent()
	{
		return this.panel;
	}

	@Override
	public void initGui()
	{
		this.panel = new EnchantSelectionPanel("GENERAL:enchant", true);
	}

	@Override
	public boolean isUsed()
	{
		return true;
	}

	@Override
	public boolean matches(List<String> data)
	{
		try
		{
			Integer.parseInt(data.get(1));
		} catch (Exception e)
		{
			return false;
		}
		return Generator.registry.exists(data.get(0), ObjectBase.ENCHANTMENT);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setEnchantment((EnchantType) Generator.registry.getObjectFromId(data.get(0)));
		this.panel.setLevel(Integer.parseInt(data.get(1)));
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof EnchantmentArgument)) return;
		this.panel.setupFrom(((EnchantmentArgument) toMatch).panel.generateEnchantment());
	}

	@Override
	public void reset()
	{
		this.panel.reset();
	}

}

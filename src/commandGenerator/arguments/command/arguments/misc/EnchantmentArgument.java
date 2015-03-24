package commandGenerator.arguments.command.arguments.misc;

import java.awt.Component;
import java.util.List;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.EnchantType;
import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.main.CGConstants;

public class EnchantmentArgument extends Argument
{

	private EnchantSelectionPanel panel;

	public EnchantmentArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.ENCHANTMENT, isCompulsery, 2);
	}

	@Override
	public String generateCommand()
	{
		Enchantment enchant = this.panel.generateEnchantment();
		if (enchant == null) return null;
		return enchant.getType().getId() + " " + enchant.getLevel();
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
		return Registry.exists(data.get(0), CGConstants.OBJECT_ENCHANT);
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.panel.setEnchantment((EnchantType) Registry.getObjectFromId(data.get(0)));
		this.panel.setLevel(Integer.parseInt(data.get(1)));
	}

}

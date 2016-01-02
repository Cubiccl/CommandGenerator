package generator.registry.command;

import generator.gui.panel.object.PanelEnchantment;
import generator.main.GenerationException;

import java.awt.Component;

/** An Enchantment. */
public class EnchantmentArgument extends Argument
{
	/** The Enchantment panel. */
	private PanelEnchantment panel;

	/** Creates a new Enchantment Argument. */
	public EnchantmentArgument()
	{
		super(true, 2);
	}

	@Override
	public void createGui()
	{
		this.panel = new PanelEnchantment(true);
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.panel.generateEnchantment().toCommand();
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

package commandGenerator.gui.options;

import commandGenerator.arguments.objects.Enchantment;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EnchantSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class EnchantOptionsPanel extends OptionsPanel
{

	private EntitySelectionPanel panelPlayer;
	private EnchantSelectionPanel panelEnchant;

	public EnchantOptionsPanel()
	{
		super();
	}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();
		Enchantment enchant = panelEnchant.generateEnchantment();

		if (player == null || enchant == null) return null;

		return "enchant " + player.commandStructure() + " " + enchant.getType().getId() + " " + Integer.toString(enchant.getLevel());
	}

	@Override
	protected void createComponents()
	{
		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelEnchant = new EnchantSelectionPanel(CGConstants.PANELID_ENCHANT, "GENERAL:enchant", true);
	}

	@Override
	protected void addComponents()
	{
		add(panelPlayer);
		add(panelEnchant);
	}

	@Override
	protected void createListeners()
	{}

}

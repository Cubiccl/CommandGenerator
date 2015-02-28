package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ClearOptionsPanel extends OptionsPanel
{

	private CCheckBox checkboxInventory, checkboxMaxCount;
	private ItemSelectionPanel panelItem;
	private TargetSelectionPanel panelPlayer;

	public ClearOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		add(panelPlayer);
		add(panelItem);
		add(checkboxInventory);
		add(checkboxMaxCount);
	}

	@Override
	protected void createComponents()
	{
		checkboxInventory = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:clear.inventory");
		checkboxMaxCount = new CCheckBox(CGConstants.DATAID_CLEAR_ITEM, "GUI:clear.items");

		panelPlayer = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:clear.item", Registry.getList(CGConstants.LIST_ITEMS), true, false);
	}

	@Override
	protected void createListeners()
	{
		checkboxInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelItem.setEnabled(!checkboxInventory.isSelected());
				panelItem.setEnabledContent(!checkboxInventory.isSelected());
			}
		});
	}

	@Override
	public String generateCommand()
	{
		Target player = panelPlayer.generateEntity();

		if (player == null) return null;

		String command = "clear " + player.commandStructure();

		if (!checkboxInventory.isSelected())
		{
			Item item = panelItem.generateItem();
			int damage = panelItem.getDamage();

			if (item == null || damage < 0) return null;

			command += " " + item.getCommandId() + " " + Integer.toString(damage);
			if (!checkboxMaxCount.isSelected())
			{
				command += " " + Integer.toString(panelItem.getCount());
				if (!panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1).equals("{}")) command += " "
						+ panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1);
			}

		}

		return command;
	}

}

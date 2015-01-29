package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ClearOptionsPanel extends OptionsPanel
{

	private CCheckBox checkboxInventory, checkboxMaxCount;
	private EntitySelectionPanel panelPlayer;
	private ItemSelectionPanel panelItem;

	public ClearOptionsPanel()
	{
		super();
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

			command += " " + item.getId() + " " + Integer.toString(damage);
			if (!checkboxMaxCount.isSelected())
			{
				command += " " + Integer.toString(panelItem.getCount());
				if (!panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1).equals("{}")) command += " "
						+ panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1);
			}

		}

		return command;
	}

	@Override
	protected void createComponents()
	{
		checkboxInventory = new CCheckBox(CGConstants.DATAID_CHECK, "GUI:clear.inventory");

		panelPlayer = new EntitySelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.player", CGConstants.ENTITIES_PLAYERS);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:clear.item", Registerer.getList(CGConstants.LIST_ITEMS), true, false);
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
		checkboxMaxCount = new CCheckBox(CGConstants.DATAID_CLEAR_ITEM, "GUI:clear.items");
	}

}

package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.commandSpecific.ReplaceitemEntityPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ReplaceitemOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "block", "entity" };

	private LangComboBox comboboxMode;
	private CoordSelectionPanel panelBlock;
	private ReplaceitemEntityPanel panelEntity;
	private ItemSelectionPanel panelItem;

	public ReplaceitemOptionsPanel()
	{
		super();
	}

	@Override
	public String generateCommand()
	{

		if (comboboxMode.getSelectedIndex() == 0 && panelBlock.generateCoord() == null) return null;
		if (comboboxMode.getSelectedIndex() == 1 && panelEntity.generateCommand() == null) return null;
		if (panelItem.generateItem() == null) return null;

		String command = "replaceitem " + modes[comboboxMode.getSelectedIndex()] + " ";

		if (comboboxMode.getSelectedIndex() == 0) command += panelBlock.generateCoord().commandStructure() + " slot.container." + panelItem.getSlot();
		if (comboboxMode.getSelectedIndex() == 1) command += panelEntity.generateCommand();

		command += " " + panelItem.generateItem().getId() + " " + Integer.toString(panelItem.getCount()) + " " + Integer.toString(panelItem.getDamage());
		if (!panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1).equals("{}")) command += " "
				+ panelItem.getItemTag().commandStructure().substring(panelItem.getItemTag().getId().length() + 1);

		return command;
	}

	@Override
	protected void createComponents()
	{
		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:replaceitem.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));

		panelBlock = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:block.coords", true, false);
		panelEntity = new ReplaceitemEntityPanel();
		panelEntity.setVisible(false);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:replaceitem.item", Registerer.getList(CGConstants.LIST_ITEMS), true, true);
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMode);
		add(panelBlock);
		add(panelEntity);
		add(panelItem);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelBlock.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelEntity.setVisible(comboboxMode.getSelectedIndex() == 1);
			}
		});
	}

}

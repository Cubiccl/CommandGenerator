package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Registerer;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.commandSpecific.ReplaceitemEntityPanel;
import commandGenerator.gui.helper.components.LangComboBox;
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
		super(800);

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:replaceitem.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelBlock.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelEntity.setVisible(comboboxMode.getSelectedIndex() == 1);
			}
		});

		panelBlock = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:block.coords", true, false);
		panelEntity = new ReplaceitemEntityPanel();
		panelEntity.setVisible(false);
		panelItem = new ItemSelectionPanel(CGConstants.PANELID_ITEM, "GUI:replaceitem.item", Registerer.getList(CGConstants.LIST_ITEMS), true, true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridy++;
		add(panelBlock, gbc);
		add(panelEntity, gbc);
		gbc.gridy++;
		add(panelItem, gbc);
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

}

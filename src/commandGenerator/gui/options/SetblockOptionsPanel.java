package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SetblockOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "keep", "destroy", "replace" };

	private JButton buttonHelp;
	private LangComboBox comboboxMode;
	private CoordSelectionPanel panelCoord;
	private BlockSelectionPanel panelBlock;

	public SetblockOptionsPanel()
	{
		super();

		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				DisplayHelper.showHelp(Lang.get("HELP:placeblock.mode_" + comboboxMode.getSelectedIndex()),
						Lang.get("RESOURCES:placeblock.mode_" + comboboxMode.getSelectedIndex()));
			}
		});

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:placeblock.mode", 3);
		comboboxMode.setPreferredSize(new Dimension(200, 20));

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:setblock.coords", true, false);
		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:setblock.block", Registerer.getList(CGConstants.LIST_BLOCKS), true);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridx++;
		add(buttonHelp, gbc);
		gbc.gridx++;
		add(panelCoord, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 3;
		add(panelBlock, gbc);
	}

	@Override
	public String generateCommand()
	{
		Coordinates coords = panelCoord.generateCoord();
		Item block = panelBlock.generateBlock();
		int damage = panelBlock.getDamage();
		TagCompound tag = panelBlock.getBlockTag();

		if (coords == null || block == null) return null;

		String command = "setblock " + coords.commandStructure() + " " + block.getId() + " " + Integer.toString(damage) + " "
				+ modes[comboboxMode.getSelectedIndex()];

		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) command += " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);

		return command;
	}

}

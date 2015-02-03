package commandGenerator.gui.helper.commandSpecific.fill;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Item;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.gui.helper.argumentSelection.BlockSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class FillNormalPanel extends HelperPanel
{

	private static final String[] modes = { "replace", "destroy", "keep", "hollow", "outline" };
	protected JButton buttonHelp;
	protected LangComboBox comboboxMode;
	protected BlockSelectionPanel panelBlock;
	protected CoordSelectionPanel panelCoord1, panelCoord2;

	public FillNormalPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options");
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxMode, buttonHelp);
		add(panelCoord1);
		add(panelCoord2);
		add(panelBlock);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new JButton("?");

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:fill.mode", 5);
		comboboxMode.setPreferredSize(new Dimension(200, 20));

		panelCoord1 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_START, "GUI:fill.start", true, false);
		panelCoord2 = new CoordSelectionPanel(CGConstants.PANELID_COORDS_END, "GUI:fill.end", true, false);
		panelBlock = new BlockSelectionPanel(CGConstants.PANELID_BLOCK, "GUI:fill.block", Registerer.getList(CGConstants.LIST_BLOCKS), true);
	}

	@Override
	protected void createListeners()
	{
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(Lang.get("HELP:fill_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
			}
		});
	}

	public String generateCommand()
	{
		Coordinates coord1 = panelCoord1.generateCoord();
		Coordinates coord2 = panelCoord2.generateCoord();
		Item item = panelBlock.generateBlock();
		int damage = panelBlock.getDamage();
		TagCompound tag = panelBlock.getBlockTag();

		if (coord1 == null || coord2 == null || item == null) return null;

		String command = "fill " + coord1.commandStructure() + " " + coord2.commandStructure() + " " + item.getId() + " " + Integer.toString(damage) + " "
				+ modes[comboboxMode.getSelectedIndex()];

		if (!tag.commandStructure().substring(tag.getId().length() + 1).equals("{}")) command += " "
				+ tag.commandStructure().substring(tag.getId().length() + 1);

		return command;
	}

}

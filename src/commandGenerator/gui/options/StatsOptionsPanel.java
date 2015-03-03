package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.Target;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class StatsOptionsPanel extends OptionsPanel
{
	private static final String[] mode = { "block", "entity" }, mode2 = { "clear", "set" }, type = { "AffectedBlocks", "AffectedEntities", "AffectedItems",
			"QueryResult", "SuccessCount" };

	private CEntry entryObjective;
	private LangComboBox comboboxMode, comboboxMode2, comboboxType;
	private CoordSelectionPanel panelCoord;
	private TargetSelectionPanel panelEntity, panelSelector;
	private HelpButton buttonHelpMode, buttonHelpType;

	@Override
	public String generateCommand()
	{
		String command = "stats " + mode[comboboxMode.getSelectedIndex()] + " ";

		if (comboboxMode.getSelectedIndex() == 0)
		{
			Coordinates coord = panelCoord.generateCoord();
			if (coord == null) return null;
			command += coord.commandStructure();
		} else
		{
			Target target = panelEntity.generateEntity();
			if (target == null) return null;
			command += target.commandStructure();
		}

		command += " " + mode2[comboboxMode2.getSelectedIndex()] + " " + type[comboboxType.getSelectedIndex()];

		if (comboboxMode2.getSelectedIndex() == 1)
		{
			Target sel = panelSelector.generateEntity();
			if (sel == null) return null;
			String obj = entryObjective.getText();
			if (obj == null || obj.equals("") || obj.contains(" "))
			{
				DisplayHelper.warningName();
				return null;
			}
			command += " " + sel.commandStructure() + " " + obj;
		}

		return command;
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMode);
		addLine(panelCoord, panelEntity);
		addLine(comboboxMode2, buttonHelpMode);
		addLine(comboboxType, buttonHelpType);
		add(panelSelector);
		add(entryObjective);
	}

	@Override
	protected void createComponents()
	{
		entryObjective = new CEntry(CGConstants.DATAID_NAME, "GUI:scoreboard.objective", "");
		entryObjective.setVisible(false);

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:replaceitem.mode", 2);
		comboboxMode2 = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:stats.mode", 2);
		comboboxType = new LangComboBox(CGConstants.DATAID_NAME2, "RESOURCES:stats.type", 5);

		panelCoord = new CoordSelectionPanel(CGConstants.PANELID_COORDS, "GUI:block.coords", true, false);

		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET2, "GENERAL:target.stats", CGConstants.ENTITIES_ALL);
		panelEntity.setVisible(false);
		panelSelector = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GENERAL:target.entity", CGConstants.DETAILS_ALL);
		panelSelector.setVisible(false);

		buttonHelpMode = new HelpButton(Lang.get("HELP:stats.mode_0"), (String) comboboxMode2.getSelectedItem());
		buttonHelpType = new HelpButton(Lang.get("HELP:stats.type_0"), (String) comboboxType.getSelectedItem());
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				panelCoord.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelEntity.setVisible(comboboxMode.getSelectedIndex() == 1);
			}
		});
		comboboxMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				panelSelector.setVisible(comboboxMode2.getSelectedIndex() == 1);
				entryObjective.setVisible(comboboxMode2.getSelectedIndex() == 1);
				buttonHelpMode.setData(Lang.get("HELP:stats.mode_" + comboboxMode2.getSelectedIndex()), (String) comboboxMode2.getSelectedItem());
			}
		});
		comboboxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				buttonHelpType.setData(Lang.get("HELP:stats.type_" + comboboxType.getSelectedIndex()), (String) comboboxType.getSelectedItem());
			}
		});

	}
}

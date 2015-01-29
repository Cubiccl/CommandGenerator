package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

import commandGenerator.gui.helper.commandSpecific.worldborder.AddBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.CenterBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.DamageBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.WarningBorderPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class WorldborderOptionsPanel extends OptionsPanel
{

	private JButton buttonHelp;
	private LangComboBox comboboxMode;
	private AddBorderPanel panelAdd, panelSet;
	private CenterBorderPanel panelCenter;
	private DamageBorderPanel panelDamage;
	private WarningBorderPanel panelWarning;
	private static final String[] modes = { "add", "center", "damage", "set", "warning" };

	public WorldborderOptionsPanel()
	{
		super();

		buttonHelp = new JButton("?");
		buttonHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DisplayHelper.showHelp(Lang.get("HELP:worldborder_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
			}
		});

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:worldborder.mode", 5);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelAdd.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelCenter.setVisible(comboboxMode.getSelectedIndex() == 1);
				panelDamage.setVisible(comboboxMode.getSelectedIndex() == 2);
				panelSet.setVisible(comboboxMode.getSelectedIndex() == 3);
				panelWarning.setVisible(comboboxMode.getSelectedIndex() == 4);
			}
		});

		panelAdd = new AddBorderPanel(true);
		panelCenter = new CenterBorderPanel();
		panelCenter.setVisible(false);
		panelDamage = new DamageBorderPanel();
		panelDamage.setVisible(false);
		panelSet = new AddBorderPanel(false);
		panelSet.setVisible(false);
		panelWarning = new WarningBorderPanel();
		panelWarning.setVisible(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridx++;
		add(buttonHelp, gbc);

		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(panelAdd);
		add(panelCenter);
		add(panelDamage);
		add(panelSet);
		add(panelWarning);
	}

	@Override
	public String generateCommand()
	{
		String command = "worldborder " + modes[comboboxMode.getSelectedIndex()] + " ";
		String text;

		if (comboboxMode.getSelectedIndex() == 0) text = panelAdd.generateText();
		else if (comboboxMode.getSelectedIndex() == 1) text = panelCenter.generateText();
		else if (comboboxMode.getSelectedIndex() == 2) text = panelDamage.generateText();
		else if (comboboxMode.getSelectedIndex() == 3) text = panelSet.generateText();
		else text = panelWarning.generateText();

		if (text == null) return null;

		return command + text;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		comboboxMode.setSelectedIndex((int) data.get(CGConstants.DATAID_MODE));
		switch ((int) data.get(CGConstants.DATAID_MODE))
		{
			case 0:
				panelAdd.setupFrom(data);
				break;
			case 1:
				panelCenter.setupFrom(data);
				break;
			case 2:
				panelDamage.setupFrom(data);
				break;
			case 3:
				panelSet.setupFrom(data);
				break;
			case 4:
				panelWarning.setupFrom(data);
				break;

			default:
				break;
		}
	}

}

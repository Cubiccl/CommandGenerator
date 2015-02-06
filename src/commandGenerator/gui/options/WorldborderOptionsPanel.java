package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.helper.commandSpecific.worldborder.AddBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.CenterBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.DamageBorderPanel;
import commandGenerator.gui.helper.commandSpecific.worldborder.WarningBorderPanel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.IBox;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class WorldborderOptionsPanel extends OptionsPanel implements IBox
{

	private static final String[] modes = { "add", "center", "damage", "set", "warning" };
	private HelpButton buttonHelp;
	private LangComboBox comboboxMode;
	private AddBorderPanel panelAdd, panelSet;
	private CenterBorderPanel panelCenter;
	private DamageBorderPanel panelDamage;
	private WarningBorderPanel panelWarning;

	public WorldborderOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(comboboxMode, buttonHelp);
		add(panelAdd);
		add(panelCenter);
		add(panelDamage);
		add(panelSet);
		add(panelWarning);
	}

	@Override
	protected void createComponents()
	{
		buttonHelp = new HelpButton(Lang.get("HELP:worldborder_0"), Lang.get("RESOURCES:worldborder.mode_0"));

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:worldborder.mode", 5);
		comboboxMode.addListener(this);

		panelAdd = new AddBorderPanel(true);
		panelCenter = new CenterBorderPanel();
		panelCenter.setVisible(false);
		panelDamage = new DamageBorderPanel();
		panelDamage.setVisible(false);
		panelSet = new AddBorderPanel(false);
		panelSet.setVisible(false);
		panelWarning = new WarningBorderPanel();
		panelWarning.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
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

	@Override
	public void updateCombobox()
	{
		buttonHelp.setData(Lang.get("HELP:worldborder_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
	}

}

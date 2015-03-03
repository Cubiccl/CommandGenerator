package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TimeOptionsPanel extends OptionsPanel
{

	private static final String[] querys = { "daytime", "gametime" };

	private LangComboBox comboboxMode, comboboxValue;
	private CEntry entryTime;
	private CLabel labelMode;
	private HelpButton buttonHelp, buttonHelpQuery;

	public TimeOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelMode, comboboxMode);
		addLine(comboboxValue, buttonHelp, buttonHelpQuery);
		add(entryTime);
	}

	@Override
	protected void createComponents()
	{
		labelMode = new CLabel("GUI:time.mode");

		entryTime = new CEntry(CGConstants.DATAID_VALUE, "GUI:time.time", "0");
		entryTime.setVisible(false);

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:time.mode", 3);
		comboboxValue = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:time.value", 3);

		buttonHelp = new HelpButton(Lang.get("HELP:time_0"), (String) comboboxMode.getSelectedItem());
		buttonHelpQuery = new HelpButton(Lang.get("HELP:time.query_0"), (String) comboboxValue.getSelectedItem());
		buttonHelpQuery.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				comboboxValue.setVisible(comboboxMode.getSelectedIndex() != 1);
				entryTime.setVisible((comboboxMode.getSelectedIndex() == 0 && comboboxValue.getSelectedIndex() == 2) || comboboxMode.getSelectedIndex() == 1);
				buttonHelp.setData(Lang.get("HELP:time_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
				buttonHelp.setVisible(comboboxMode.getSelectedIndex() == 0);
				buttonHelpQuery.setData(Lang.get("HELP:time.query_" + comboboxValue.getSelectedIndex()), (String) comboboxValue.getSelectedItem());
				buttonHelpQuery.setVisible(comboboxMode.getSelectedIndex() == 2);

				if (comboboxMode.getSelectedIndex() == 0) comboboxValue.setText("RESOURCES:time.value", 3);
				if (comboboxMode.getSelectedIndex() == 2) comboboxValue.setText("RESOURCES:time.query", 2);
			}
		});
		comboboxValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				entryTime.setVisible((comboboxMode.getSelectedIndex() == 0 && comboboxValue.getSelectedIndex() == 2) || comboboxMode.getSelectedIndex() == 1);
				buttonHelp.setData(Lang.get("HELP:time.query_" + comboboxValue.getSelectedIndex()), (String) comboboxValue.getSelectedItem());
				buttonHelpQuery.setData(Lang.get("HELP:time.query_" + comboboxValue.getSelectedIndex()), (String) comboboxValue.getSelectedItem());
			}
		});
	}

	@Override
	public String generateCommand()
	{
		String time = entryTime.getText();

		try
		{
			int flag = Integer.parseInt(time);
			if (flag < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		switch (comboboxMode.getSelectedIndex())
		{
			case 0:
				switch (comboboxValue.getSelectedIndex())
				{
					case 0:
						return "time set day";
					case 1:
						return "time set night";
					case 2:
						return "time set " + entryTime.getText();

				}
			case 1:
				return "time add " + entryTime.getText();
			case 2:
				return "time query " + querys[comboboxValue.getSelectedIndex()];
		}

		return "error xD";
	}

}

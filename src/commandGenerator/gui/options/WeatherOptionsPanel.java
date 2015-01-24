package commandGenerator.gui.options;

import java.awt.Dimension;
import java.util.Map;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class WeatherOptionsPanel extends OptionsPanel
{

	private CLabel labelWeather;
	private CEntry entryDuration;
	private LangComboBox comboboxWeather;
	private static final String[] modes = { "clear", "rain", "thunder" };

	public WeatherOptionsPanel()
	{
		super();

		labelWeather = new CLabel("GUI:weather.select");

		entryDuration = new CEntry(CGConstants.DATAID_NONE, "GUI:weather.duration");

		comboboxWeather = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:weather.type", 3);
		comboboxWeather.setPreferredSize(new Dimension(200, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelWeather, gbc);
		gbc.gridx++;
		add(comboboxWeather, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(entryDuration, gbc);
		gbc.gridwidth = 1;
	}

	@Override
	public String generateCommand()
	{

		String duration = entryDuration.getText();

		try
		{
			int flag = Integer.parseInt(duration);
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

		return "weather " + modes[comboboxWeather.getSelectedIndex()] + " " + duration;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		super.setupFrom(data);
		if (data.get(CGConstants.DATAID_VALUE) != null) entryDuration.setTextField((String) data.get(CGConstants.DATAID_VALUE));
	}

}

package commandGenerator.gui.options;

import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class WeatherOptionsPanel extends OptionsPanel
{

	private static final String[] modes = { "clear", "rain", "thunder" };
	private LangComboBox comboboxWeather;
	private CEntry entryDuration;
	private CLabel labelWeather;

	public WeatherOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelWeather, comboboxWeather);
		add(entryDuration);
	}

	@Override
	protected void createComponents()
	{
		labelWeather = new CLabel("GUI:weather.select");

		entryDuration = new CEntry(CGConstants.DATAID_NONE, "GUI:weather.duration", "0");

		comboboxWeather = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:weather.type", 3);
	}

	@Override
	protected void createListeners()
	{}

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

package commandGenerator.gui.helper.commandSpecific.worldborder;

import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class CenterBorderPanel extends HelperPanel
{

	private CEntry entryX, entryZ;

	public CenterBorderPanel()
	{
		super(Constants.PANELID_OPTIONS, "GENERAL:options");
	}

	@Override
	protected void addComponents()
	{
		add(entryX);
		add(entryZ);
	}

	@Override
	protected void createComponents()
	{
		entryX = new CEntry(Constants.DATAID_NONE, "GUI:coord.x", "0");
		entryZ = new CEntry(Constants.DATAID_NONE, "GUI:coord.z", "0");
	}

	@Override
	protected void createListeners()
	{}

	public String generateText()
	{
		String x = entryX.getText();
		String z = entryZ.getText();

		try
		{
			@SuppressWarnings("unused")
			int test = Integer.parseInt(x);
			test = Integer.parseInt(z);
		} catch (Exception ex)
		{
			DisplayHelper.warningInteger();
			return null;
		}

		return x + " " + z;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		String[] options = (String[]) data.get(getPanelId());
		entryX.setTextField(options[0]);
		entryZ.setTextField(options[1]);
	}

}

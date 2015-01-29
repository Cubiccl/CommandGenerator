package commandGenerator.gui.helper.commandSpecific.worldborder;

import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class CenterBorderPanel extends HelperPanel
{

	private CEntry entryX, entryZ;

	public CenterBorderPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options");
	}

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

	@Override
	protected void createComponents()
	{
		entryX = new CEntry(CGConstants.DATAID_NONE, "GUI:coord.x");
		entryZ = new CEntry(CGConstants.DATAID_NONE, "GUI:coord.z");
	}

	@Override
	protected void addComponents()
	{
		add(entryX);
		add(entryZ);
	}

	@Override
	protected void createListeners()
	{}

}

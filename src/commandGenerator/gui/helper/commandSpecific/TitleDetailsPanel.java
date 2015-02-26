package commandGenerator.gui.helper.commandSpecific;

import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.Constants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TitleDetailsPanel extends HelperPanel
{

	private CEntry entryFadeIn, entryStay, entryFadeOut;

	public TitleDetailsPanel(String title)
	{
		super(Constants.PANELID_OPTIONS, title);
	}

	@Override
	protected void addComponents()
	{
		add(entryFadeIn);
		add(entryStay);
		add(entryFadeOut);
	}

	@Override
	protected void createComponents()
	{
		entryFadeIn = new CEntry(Constants.DATAID_NONE, "GUI:title.fade_in", "1");
		entryStay = new CEntry(Constants.DATAID_NONE, "GUI:title.stay", "1");
		entryFadeOut = new CEntry(Constants.DATAID_NONE, "GUI:title.fade_out", "1");
	}

	@Override
	protected void createListeners()
	{}

	public String[] generateOptions()
	{
		DisplayHelper.log("Generating title details");
		String fadeIn = entryFadeIn.getText(), stay = entryStay.getText(), fadeOut = entryFadeOut.getText();

		try
		{
			int testIn = Integer.parseInt(fadeIn);
			int testStay = Integer.parseInt(stay);
			int testOut = Integer.parseInt(fadeOut);
			if (testIn < 0 || testStay < 0 || testOut < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		return new String[] { fadeIn, stay, fadeOut };
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (data.get(Constants.DATAID_VALUE) == null)
		{
			reset();
			return;
		}
		int[] opt = (int[]) data.get(Constants.DATAID_VALUE);
		entryFadeIn.setTextField(Integer.toString(opt[0]));
		entryStay.setTextField(Integer.toString(opt[1]));
		entryFadeOut.setTextField(Integer.toString(opt[2]));
	}

}

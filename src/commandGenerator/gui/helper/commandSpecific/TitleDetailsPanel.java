package commandGenerator.gui.helper.commandSpecific;

import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class TitleDetailsPanel extends HelperPanel
{

	private CEntry entryFadeIn, entryStay, entryFadeOut;

	public TitleDetailsPanel(String title)
	{
		super(CGConstants.PANELID_OPTIONS, title, 400, 200);

		entryFadeIn = new CEntry(CGConstants.DATAID_NONE, "GUI:title.fade_in");
		entryStay = new CEntry(CGConstants.DATAID_NONE, "GUI:title.stay");
		entryFadeOut = new CEntry(CGConstants.DATAID_NONE, "GUI:title.fade_out");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entryFadeIn, gbc);
		gbc.gridy++;
		add(entryStay, gbc);
		gbc.gridy++;
		add(entryFadeOut, gbc);
	}

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
		if (data.get(CGConstants.DATAID_VALUE) == null)
		{
			reset();
			return;
		}
		int[] opt = (int[]) data.get(CGConstants.DATAID_VALUE);
		entryFadeIn.setTextField(Integer.toString(opt[0]));
		entryStay.setTextField(Integer.toString(opt[1]));
		entryFadeOut.setTextField(Integer.toString(opt[2]));
	}

}

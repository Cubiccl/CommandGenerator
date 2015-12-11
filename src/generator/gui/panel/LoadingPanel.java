package generator.gui.panel;

import generator.gui.CLabel;
import generator.main.Constants;

import java.awt.Font;


@SuppressWarnings("serial")
public class LoadingPanel extends CPanelVertical
{
	private CLabel labelDetails, labelLoading;
	
	public LoadingPanel()
	{
		super();
		this.labelLoading = new CLabel("GUI:loading.loading");
		this.labelLoading.setFont(Constants.font.deriveFont(Font.BOLD, 30));
		this.labelDetails = new CLabel("GUI:loading.lang");

		this.add(this.labelLoading);
		this.add(this.labelDetails);
	}

	@Override
	public void updateLang()
	{
		this.labelLoading.updateLang();
		this.labelDetails.updateLang();
	}

}

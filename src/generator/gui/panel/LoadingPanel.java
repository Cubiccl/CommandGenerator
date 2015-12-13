package generator.gui.panel;

import generator.gui.CLabel;
import generator.main.Utils;

import java.awt.Font;

@SuppressWarnings("serial")
public class LoadingPanel extends CPanelVertical
{
	private CLabel labelDetails, labelLoading;

	public LoadingPanel()
	{
		super();
		this.labelLoading = new CLabel("GUI:loading.loading");
		this.labelLoading.setFont(Utils.font.deriveFont(Font.BOLD, 30));
		this.labelDetails = new CLabel("GUI:loading.objects");

		this.add(this.labelLoading);
		this.add(this.labelDetails);
	}

	public void setDetail(String textID)
	{
		this.labelDetails.setText(textID);
	}

	@Override
	public void updateLang()
	{
		this.labelLoading.updateLang();
		this.labelDetails.updateLang();
	}

}

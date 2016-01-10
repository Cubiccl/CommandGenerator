package generator.gui.panel;

import generator.gui.CLabel;
import generator.main.Text;
import generator.main.Utils;

import java.awt.Font;

@SuppressWarnings("serial")
public class LoadingPanel extends CPanelVertical
{
	private CLabel labelDetails, labelLoading;

	public LoadingPanel()
	{
		super();
		this.labelLoading = new CLabel(new Text("GUI", "loading.loading"));
		this.labelLoading.setFont(Utils.font.deriveFont(Font.BOLD, 30));
		this.labelDetails = new CLabel(new Text("GUI", "loading.objects"));

		this.add(this.labelLoading);
		this.add(this.labelDetails);
	}

	public void setDetail(Text text)
	{
		this.labelDetails.setText(text);
	}

	@Override
	public void updateLang()
	{
		this.labelLoading.updateLang();
		this.labelDetails.updateLang();
	}

}

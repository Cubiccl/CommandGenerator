package commandGenerator.gui.helper.components;

import javax.swing.JCheckBox;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CCheckBox extends JCheckBox implements CComponent
{

	private String title;

	public CCheckBox(String title)
	{
		super(Lang.get(title));
		this.title = title;
	}

	@Override
	public void reset()
	{
		this.setSelected(false);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	public void setTitle(String title)
	{
		this.title = title;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		this.setText(Lang.get(this.title));
	}

}

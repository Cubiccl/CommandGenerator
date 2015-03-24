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
		setSelected(false);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	public void setTitle(String title)
	{
		this.title = title;
		updateLang();
	}

	@Override
	public void updateLang()
	{
		setText(Lang.get(title));
	}

}

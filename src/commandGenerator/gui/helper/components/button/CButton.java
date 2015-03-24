package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CButton extends JButton implements CComponent
{

	private boolean lang;
	private String title;

	public CButton(String title)
	{
		this(title, true);
	}

	public CButton(String title, boolean lang)
	{
		super(title);
		this.title = title;
		this.lang = lang;
		setPreferredSize(new Dimension(200, 20));
		setMinimumSize(new Dimension(140, 20));
		updateLang();
	}

	@Override
	public void reset()
	{}

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
		if (lang) setText(Lang.get(title));
		else setText(title);
	}

}

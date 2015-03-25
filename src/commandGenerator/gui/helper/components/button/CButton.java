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
		this.setPreferredSize(new Dimension(200, 20));
		this.setMinimumSize(new Dimension(140, 20));
		this.updateLang();
	}

	@Override
	public void reset()
	{}

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
		if (this.lang) this.setText(Lang.get(this.title));
		else this.setText(this.title);
	}

}

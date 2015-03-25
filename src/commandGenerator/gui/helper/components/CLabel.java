package commandGenerator.gui.helper.components;

import javax.swing.JLabel;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CLabel extends JLabel implements CComponent
{

	private boolean html;
	private String title;

	public CLabel(String title)
	{
		this(title, true);
	}

	public CLabel(String title, boolean html)
	{
		super(title);
		this.title = title;
		this.html = html;

		if (this.html) this.setText("<html>" + Lang.get(this.title).replaceAll("\n", "<br />") + "</html>");
		else this.setText(Lang.get(this.title));
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	public void setTitle(String id)
	{
		this.title = id;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		if (this.html) this.setText("<html>" + Lang.get(this.title).replaceAll("\n", "<br />") + "</html>");
		else this.setText(Lang.get(this.title));
	}

}

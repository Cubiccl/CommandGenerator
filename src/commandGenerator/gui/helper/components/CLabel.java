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
		super(title);
		this.title = title;
		setText(Lang.get(title));
		this.html = false;
	}

	public CLabel(String id, boolean html)
	{
		this(id);
		this.html = html;
		setText("<html>" + Lang.get(id).replaceAll("\n", "<br />") + "</html>");
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	public void setTitle(String id)
	{
		this.title = id;
		updateLang();
	}

	@Override
	public void updateLang()
	{
		if (html) setText("<html>" + Lang.get(title).replaceAll("\n", "<br />") + "</html>");
		else setText(Lang.get(title));
	}

}

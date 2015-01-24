package commandGenerator.gui.helper.components;

import java.util.Map;

import javax.swing.JLabel;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CLabel extends JLabel implements CComponent
{

	private String id;
	private boolean html;

	public CLabel(String id)
	{
		super(id);
		this.id = id;
		setText(Lang.get(id));
		this.html = false;
	}

	public CLabel(String id, boolean html)
	{
		this(id);
		this.html = html;
		setText("<html>" + Lang.get(id).replaceAll("\n", "<br />") + "</html>");
	}

	public void updateLang()
	{
		if (html) setText("<html>" + Lang.get(id).replaceAll("\n", "<br />") + "</html>");
		else setText(Lang.get(id));
	}

	public void setTitle(String id)
	{
		this.id = id;
		updateLang();
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	public void setupFrom(Map<String, Object> data)
	{}

	@Override
	public void reset()
	{}

}

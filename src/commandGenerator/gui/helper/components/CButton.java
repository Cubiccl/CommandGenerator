package commandGenerator.gui.helper.components;

import java.util.Map;

import javax.swing.JButton;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CButton extends JButton implements CComponent
{

	private String id;
	private boolean lang;

	public CButton(String id)
	{
		this(id, true);
	}

	public CButton(String id, boolean lang)
	{
		super(id);
		this.id = id;
		this.lang = lang;
		updateLang();
	}

	public void setTitle(String id)
	{
		this.id = id;
		updateLang();
	}

	public void updateLang()
	{
		if (lang) setText(Lang.get(id));
		else setText(id);
	}

	public void setupFrom(Map<String, Object> data)
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	@Override
	public void reset()
	{}

}

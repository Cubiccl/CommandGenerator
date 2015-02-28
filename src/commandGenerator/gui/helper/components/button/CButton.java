package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;
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

	public void setTitle(String id)
	{
		this.id = id;
		updateLang();
	}

	public void setupFrom(Map<String, Object> data)
	{}

	public void updateLang()
	{
		if (lang) setText(Lang.get(id));
		else setText(id);
	}

}

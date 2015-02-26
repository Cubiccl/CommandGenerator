package commandGenerator.gui.helper.components;

import java.util.Map;

import javax.swing.JCheckBox;

import commandGenerator.main.Constants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CCheckBox extends JCheckBox implements CComponent
{

	private String id, title;

	public CCheckBox(String id, String title)
	{
		super(Lang.get(title));
		this.id = id;
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

	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(Constants.DATAID_NONE)) setSelected((boolean) data.get(id));
	}

	public void updateLang()
	{
		setText(Lang.get(title));
	}

}

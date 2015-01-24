package commandGenerator.gui.helper.components;

import java.util.Map;

import javax.swing.JComboBox;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class LangComboBox extends JComboBox<String> implements CComponent
{

	private String id, title;
	private int size;

	public LangComboBox(String id, String title, int size)
	{
		super(new String[] { "" });

		this.id = id;
		this.title = title;
		this.size = size;
		String[] names = new String[size];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = Lang.get(title + "_" + i);
		}
		setModel(new JComboBox<String>(names).getModel());
	}

	public void updateLang()
	{
		String[] names = new String[size];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = Lang.get(title + "_" + i);
		}
		setModel(new JComboBox<String>(names).getModel());
	}

	public void setText(String title, int size)
	{
		this.title = title;
		this.size = size;
		updateLang();
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) setSelectedIndex((int) data.get(id));
	}

	@Override
	public void reset()
	{
		setSelectedIndex(0);
	}

}

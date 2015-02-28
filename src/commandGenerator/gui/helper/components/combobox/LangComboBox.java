package commandGenerator.gui.helper.components.combobox;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.icomponent.IBox;
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
		setPreferredSize(new Dimension(200, 20));
		setMinimumSize(new Dimension(200, 20));
	}

	@Override
	public void reset()
	{
		setSelectedIndex(0);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	public void setText(String title, int size)
	{
		this.title = title;
		this.size = size;
		updateLang();
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) setSelectedIndex((int) data.get(id));
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

	public void addListener(final IBox parent)
	{
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				parent.updateCombobox();
			}
		});
	}

}

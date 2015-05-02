package commandGenerator.gui.helper.components.combobox;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class CComboBox<T> extends JComboBox<T>
{

	public CComboBox(T[] values)
	{
		super(values);
	}

}

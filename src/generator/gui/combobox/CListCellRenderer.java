package generator.gui.combobox;

import generator.main.Utils;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Custom renderer for elements of a list/combobox. */
public class CListCellRenderer implements ListCellRenderer<String>
{

	public CListCellRenderer()
	{}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus)
	{
		list.setSelectionBackground(null);
		JLabel label = new JLabel(value);
		label.setOpaque(true);
		label.setBackground(Utils.BACKGROUND_NORMAL);
		label.setFont(list.getFont());
		if (isSelected) label.setBackground(Utils.BACKGROUND_HOVERED);
		return label;
	}

}

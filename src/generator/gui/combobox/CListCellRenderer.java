package generator.gui.combobox;

import generator.main.Utils;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CListCellRenderer implements ListCellRenderer<String>
{

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus)
	{
		JLabel label = new JLabel(value);
		label.setOpaque(true);
		label.setBackground(Utils.BACKGROUND_NORMAL);
		label.setFont(list.getFont());
		if (isSelected) label.setBackground(Utils.BACKGROUND_HOVERED);
		return label;
	}

}

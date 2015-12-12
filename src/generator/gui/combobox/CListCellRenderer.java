package generator.gui.combobox;

import generator.main.Constants;

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
		label.setBackground(Constants.BACKGROUND_NORMAL);
		label.setFont(list.getFont());
		if (isSelected) label.setBackground(Constants.BACKGROUND_HOVERED);
		return label;
	}

}

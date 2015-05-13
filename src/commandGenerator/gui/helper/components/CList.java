package commandGenerator.gui.helper.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import commandGenerator.gui.helper.GuiHandler;

@SuppressWarnings("serial")
public class CList<T> extends JList<T>
{

	public CList(T[] listData)
	{
		super(listData);
		this.setCellRenderer(new ListCellRenderer<T>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus)
			{
				JLabel label = new JLabel(value.toString());
				label.setOpaque(true);
				if (!list.isEnabled()) label.setForeground(GuiHandler.DISABLED_BORDER);
				else
				{
					label.setBackground(Color.WHITE);
					if (isSelected) label.setBackground(GuiHandler.HOVERED);
				}
				return label;
			}
		});
	}

}

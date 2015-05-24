package commandGenerator.gui.helper.components.combobox;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import commandGenerator.gui.helper.components.button.BaseButton;

public class JScrollPopupMenu extends JPopupMenu implements ActionListener
{
	private static final long serialVersionUID = 1;
	private static final int MAX_ELEMENTS = 25;
	private JPanel panelMenus;
	private JScrollPane scroll;
	private JFrame jframe;

	public JScrollPopupMenu(JFrame jframe)
	{
		super();
		this.jframe = jframe;
		this.setLayout(new BorderLayout());
		this.panelMenus = new JPanel(new GridLayout(0, 1));
		init(jframe);
	}

	private void init(JFrame jframe)
	{
		super.removeAll();
		scroll = new JScrollPane();
		scroll.setViewportView(panelMenus);
		scroll.setBorder(null);
		scroll.getVerticalScrollBar().setUnitIncrement(20);
		scroll.setMinimumSize(new Dimension(BaseButton.DEFAULT_WIDTH, BaseButton.DEFAULT_HEIGHT * MAX_ELEMENTS + 5));
		scroll.setMaximumSize(new Dimension(BaseButton.DEFAULT_WIDTH, BaseButton.DEFAULT_HEIGHT * MAX_ELEMENTS + 5));
		super.add(scroll, BorderLayout.CENTER);
	}

	public void show(Component invoker, int x, int y)
	{
		init(jframe);
		jframe.setAlwaysOnTop(true);
		panelMenus.validate();
		int maxsize = scroll.getMaximumSize().height;
		int realsize = panelMenus.getPreferredSize().height;

		int sizescroll = 0;

		if (maxsize < realsize)
		{
			sizescroll = scroll.getVerticalScrollBar().getPreferredSize().width;
		}
		scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width + 20, scroll.getPreferredSize().height));
		this.pack();
		this.setInvoker(invoker);
		if (sizescroll != 0) this.setPopupSize(new Dimension(scroll.getPreferredSize().width, scroll.getMaximumSize().height));
		Point invokerOrigin = invoker.getLocationOnScreen();
		this.setLocation((int) invokerOrigin.getX() + x, (int) invokerOrigin.getY() + y);
		this.setVisible(true);
	}

	public void hidemenu()
	{
		if (this.isVisible())
		{
			this.setVisible(false);
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		this.jframe.setAlwaysOnTop(visible);
	}

	public void add(AbstractButton menuItem)
	{
		if (menuItem == null) return;
		panelMenus.add(menuItem);
		menuItem.removeActionListener(this);
		menuItem.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		this.hidemenu();
	}

	public Component[] getComponents()
	{
		return panelMenus.getComponents();
	}
}
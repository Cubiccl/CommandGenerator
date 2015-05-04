package commandGenerator.gui.helper.components.panel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public abstract class CPanel extends JPanel implements CComponent
{

	private static final int MIN = 20;
	/** Object to place the elements in the panel. */
	private GridBagConstraints gbc;

	/** The language ID of this Panel's title. */
	protected String title;

	/** A basic JPanel.
	 * 
	 * @param id
	 *            - The panel ID. Used to generate layouts from commands.
	 * @param title
	 *            - The panel Title. Used to display its name.
	 * @param width
	 *            - The panel width.
	 * @param height
	 *            - The panel height. */
	public CPanel(String title)
	{
		super(new GridBagLayout());
		this.title = title;
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				Lang.get(title)));
		setPreferredSize(new Dimension(MIN, MIN));

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
	}

	public void initGui()
	{
		createComponents();
		addComponents();
		createListeners();
		setupSize();
	}

	/** Adds a component to the Panel.
	 * 
	 * @param component
	 *            - <i>Component</i> - The component to add. */
	@Override
	public Component add(Component component)
	{
		super.add(component, gbc);

		gbc.gridy++;

		return this;
	}

	/** Adds all components to this Panel. */
	protected abstract void addComponents();

	/** Adds a line of Components to the Panel.
	 * 
	 * @param split
	 *            - <i>boolean</i> - Whether to sperate them or not.
	 * 
	 * @param components
	 *            - <i>Components...</i> - The Components to add. */
	public void addLine(boolean split, Component... components)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		int width = MIN, height = MIN;

		for (Component component : components)
		{
			panel.add(component, gbc2);
			if (split) gbc2.gridx++;
			if (component.getPreferredSize().height > height) height = component.getPreferredSize().height + MIN;
			width += component.getPreferredSize().width;
		}

		panel.setPreferredSize(new Dimension(width, height));
		panel.setMinimumSize(new Dimension(width, height));
		add(panel);
	}

	/** Adds a line of Components to the Panel.
	 * 
	 * @param components
	 *            - <i>Components...</i> - The Components to add. */
	public void addLine(Component... components)
	{
		addLine(true, components);
	}

	/** Adds a stack of Components, they will overlap each other.
	 * 
	 * @param components
	 *            - <i>Components...</i> - The Components to add. */
	public void addStack(Component... components)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		int width = MIN, height = MIN;

		for (Component component : components)
		{
			panel.add(component, gbc2);
			if (component.getPreferredSize().height > height) height = component.getPreferredSize().height + MIN;
			if (component.getPreferredSize().width > width) width = component.getPreferredSize().width + MIN;
		}

		panel.setPreferredSize(new Dimension(width, height));
		panel.setMinimumSize(new Dimension(width, height));
		add(panel);
	}

	/** Creates all of this Panel's components. */
	protected abstract void createComponents();

	/** Creates all of this Panel's components' Listeners. */
	protected abstract void createListeners();

	@Override
	public void reset()
	{
		reset(this);
	}

	private void reset(JPanel panel)
	{
		for (Component component : panel.getComponents())
		{
			if (component instanceof CComponent) ((CComponent) component).reset();
			else if (component instanceof JPanel) reset((JPanel) component);
		}
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		enableContent(this, enable);
	}

	private void enableContent(JPanel component, boolean enable)
	{
		for (Component c : component.getComponents())
		{
			if (c instanceof CComponent) ((CComponent) c).setEnabledContent(enable);
			else
			{
				c.setEnabled(enable);
				if (c instanceof JPanel) enableContent((JPanel) c, enable);
			}
		}
	}

	/** Sets this Panel's size according to its components. */
	protected void setupSize()
	{
		int width = MIN, height = MIN;

		for (Component component : getComponents())
		{
			if (component.isVisible())
			{
				if (component.getPreferredSize().width >= width - MIN) width = component.getPreferredSize().width + MIN;
				if (component.getMinimumSize().width >= width - MIN) width = component.getMinimumSize().width + MIN;
				height += component.getPreferredSize().height;
			}
		}

		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));

	}

	@Override
	public void updateLang()
	{
		updateTitle();
		updateLang(this);
	}

	private void updateLang(JPanel panel)
	{
		for (Component component : panel.getComponents())
		{
			if (component instanceof CComponent) ((CComponent) component).updateLang();
			else if (component instanceof JPanel) updateLang((JPanel) component);
		}
	}

	/** Updates the title when changing language. */
	protected void updateTitle()
	{
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				Lang.get(title)));
	}

}

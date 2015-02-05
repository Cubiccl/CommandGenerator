package commandGenerator.gui.helper.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public abstract class HelperPanel extends JPanel implements CComponent
{

	/** List containing all of this Panel's elements. */
	private List<CComponent> components;
	/** Object to place the elements in the panel. */
	private GridBagConstraints gbc;
	/** The data ID of this Panel. */
	private String id;
	/** The language ID of this Panel's title. */
	protected String title;
	
	private static final int MIN = 20;

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
	public HelperPanel(String id, String title, Object... details)
	{
		super(new GridBagLayout());
		this.id = id;
		this.title = title;
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				Lang.get(title)));
		setPreferredSize(new Dimension(MIN, MIN));
		components = new ArrayList<CComponent>();

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;

		if (details.length > 0) setupDetails(details);
		createComponents();
		addComponents();
		createListeners();
		setupSize();
	}

	/** Adds a component to the Panel.
	 * 
	 * @param component
	 *            - <i>Component</i> - The component to add. */
	public Component add(Component component)
	{
		super.add(component, gbc);

		gbc.gridy++;
		if (component instanceof CComponent) components.add((CComponent) component);

		return this;
	}

	/** Adds all components to this Panel. */
	protected abstract void addComponents();

	/** Adds a line of Components to the Panel.
	 * 
	 * @param components
	 *            - <i>Components...</i> - The Components to add. */
	public void addLine(Component... components)
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		int width = MIN, height = MIN;

		for (Component component : components)
		{
			panel.add(component, gbc2);
			if (component instanceof CComponent) this.components.add((CComponent) component);
			gbc2.gridx++;
			if (component.getPreferredSize().height > height) height = component.getPreferredSize().height + MIN;
			width += component.getPreferredSize().width;
		}

		panel.setPreferredSize(new Dimension(width, height));
		add(panel);
	}

	/** Creates all of this Panel's components. */
	protected abstract void createComponents();

	/** Creates all of this Panel's components' Listeners. */
	protected abstract void createListeners();

	/** Gets this Panel's data ID. */
	public String getPanelId()
	{
		return id;
	}

	public void reset()
	{
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).reset();
		}
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).setEnabledContent(enable);
		}
	}

	/** Setups details needed to create the GUI. */
	protected void setupDetails(Object[] details)
	{
		System.out.println("This needs to be overriden! " + getPanelId());
	}

	public void setupFrom(Map<String, Object> data)
	{
		if (!isEnabled() || !isVisible()) return;
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).setupFrom(data);
		}
	}

	/** Sets this Panel's size according to its components. */
	protected void setupSize()
	{
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;

		for (Component component : getComponents())
		{
			if (component.getPreferredSize().width > width) width = component.getPreferredSize().width + MIN;
			height += component.getPreferredSize().height;
		}

		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));

	}

	public void updateLang()
	{
		updateTitle();
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).updateLang();
		}
	}

	/** Updates the title when changing language. */
	protected void updateTitle()
	{
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
				Lang.get(title)));
	}

}

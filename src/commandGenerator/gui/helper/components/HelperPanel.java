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
	private GridBagConstraints gbc = new GridBagConstraints();
	/** The data ID of this Panel. */
	private String id;
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
	public HelperPanel(String id, String title, Object... details)
	{
		super(new GridBagLayout());
		this.id = id;
		this.title = title;
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
		setPreferredSize(new Dimension(10, 10));
		components = new ArrayList<CComponent>();

		if (details.length > 0) setupDetails(details);
		createComponents();
		createListeners();
		addComponents();

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
		updateSize(component);

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
		gbc2.gridx = -1;
		for (Component component : components)
		{
			gbc2.gridx++;
			panel.add(component, gbc2);
		}

		add(panel);
	}

	/** Creates all of this Panel's components. */
	protected abstract void createComponents();;

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

	public void updateLang()
	{
		updateTitle();
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).updateLang();
		}
	}

	/** Updates the height of this Panel when a component is added.
	 * 
	 * @param component
	 *            - <i>Component</i> - The new component. */
	protected void updateSize(Component component)
	{
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;

		if (component.getPreferredSize().width > width) width = component.getPreferredSize().width + 10;
		height += component.getPreferredSize().height;

		setPreferredSize(new Dimension(width, height));
	}

	/** Updates the title when changing language. */
	protected void updateTitle()
	{
		if (!title.equals("GENERAL:options")) setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
	}

}

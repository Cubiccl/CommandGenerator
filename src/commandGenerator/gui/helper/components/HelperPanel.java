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

	/** The data ID of this Panel. */
	private String id;
	/** The language ID of this Panel's title. */
	protected String title;
	/** Object to place the elements in the panel. */
	private GridBagConstraints gbc = new GridBagConstraints();
	/** List containing all of this Panel's elements. */
	private List<CComponent> components;

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
	public HelperPanel(String id, String title)
	{
		super(new GridBagLayout());
		this.id = id;
		this.title = title;
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
		setPreferredSize(new Dimension(2, 2));
		components = new ArrayList<CComponent>();

		createComponents();
		addComponents();
		createListeners();

	}

	/** Creates all of this Panel's components. */
	protected abstract void createComponents();

	/** Adds all components to this Panel. */
	protected abstract void addComponents();

	/** Creates all of this Panel's components' Listeners. */
	protected abstract void createListeners();

	/** Updates the title when changing language. */
	protected void updateTitle()
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
	}

	/** Gets this Panel's data ID. */
	public String getPanelId()
	{
		return id;
	}

	/** Adds a component to the Panel.
	 * 
	 * @param component
	 *            - <i>Component</i> - The component to add. */
	public Component add(Component component)
	{
		super.add(component);

		gbc.gridy++;
		if (component instanceof CComponent) components.add((CComponent) component);
		updateSize(component);

		return this;
	}

	/** Updates the height of this Panel when a component is added.
	 * 
	 * @param component
	 *            - <i>Component</i> - The new component. */
	protected void updateSize(Component component)
	{
		int width = getPreferredSize().width;
		int height = getPreferredSize().height;

		if (component.getPreferredSize().width > width) width = component.getPreferredSize().width + 2;
		height += component.getPreferredSize().height;

		setPreferredSize(new Dimension(width, height));
	}

	public void updateLang()
	{
		updateTitle();
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).updateLang();
		}
	}

	public void setupFrom(Map<String, Object> data)
	{
		if (!isEnabled() || !isVisible()) return;
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).setupFrom(data);
		}
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

}

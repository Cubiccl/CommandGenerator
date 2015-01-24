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

	private String id;
	protected String title;
	protected boolean gray;
	public GridBagConstraints gbc = new GridBagConstraints();
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
	public HelperPanel(String id, String title, int width, int height)
	{
		super(new GridBagLayout());
		this.id = id;
		this.title = title;
		this.gray = false;
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
		setPreferredSize(new Dimension(width, height));
		components = new ArrayList<CComponent>();

	}

	protected void updateTitle()
	{
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), Lang.get(title)));
	}

	public void setTitle(String title)
	{
		this.title = title;
		updateTitle();
	}

	public String getPanelId()
	{
		return id;
	}

	/** Adds a component to the panel */
	public void add(Component component, GridBagConstraints gbc)
	{
		super.add(component, gbc);
		if (component instanceof CComponent) components.add((CComponent) component);
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

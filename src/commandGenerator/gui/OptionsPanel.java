package commandGenerator.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import commandGenerator.gui.helper.components.CComponent;

@SuppressWarnings("serial")
public abstract class OptionsPanel extends JPanel implements CComponent
{
	private List<CComponent> components;
	public GridBagConstraints gbc = new GridBagConstraints();

	public OptionsPanel()
	{
		this(700);
	}

	public OptionsPanel(int height)
	{

		setPreferredSize(new Dimension(950, height));
		setLayout(new GridBagLayout());
		components = new ArrayList<CComponent>();

	}

	public abstract String generateCommand();

	public void updateLang()
	{
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).updateLang();
		}
	}

	public void setupFrom(Map<String, Object> data)
	{
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).setupFrom(data);
		}
	}

	public void setEnabledContent(boolean enable)
	{
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).setEnabledContent(enable);
		}
	}

	public void reset()
	{
		for (int i = 0; i < components.size(); i++)
		{
			components.get(i).reset();
		}
	}

	/** Adds a component to the panel */
	public void add(Component component, GridBagConstraints gbc)
	{
		super.add(component, gbc);
		if (component instanceof CComponent) components.add((CComponent) component);
	}

}

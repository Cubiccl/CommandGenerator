package generator.gui.panel;

import generator.interfaces.ITranslate;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/** Default Panels for this interface. */
@SuppressWarnings("serial")
public abstract class CPanel extends JPanel implements ITranslate
{
	/** Used to place components. */
	protected GridBagConstraints gbc;

	public CPanel()
	{
		super(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.gridwidth = 1;
		this.gbc.gridheight = 1;
		this.gbc.insets = new Insets(2, 2, 2, 2);
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		for (Component component : this.getComponents())
		{
			component.setEnabled(this.isEnabled());
		}
	}

	@Override
	public void updateLang()
	{
		for (Component component : this.getComponents())
		{
			if (component != null && component instanceof ITranslate) ((ITranslate) component).updateLang();
		}
	}

}

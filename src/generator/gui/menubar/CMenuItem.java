package generator.gui.menubar;

import generator.interfaces.ITranslate;
import generator.main.Text;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CMenuItem extends JMenuItem implements ITranslate
{
	private Text text;

	/** @param text - The text for this Menu.
	 * @param listener - A listener to call when this Menu is clicked. */
	public CMenuItem(Text text, ActionListener listener)
	{
		this.text = text;
		this.addActionListener(listener);
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		this.setText(this.text.getValue());
	}

}

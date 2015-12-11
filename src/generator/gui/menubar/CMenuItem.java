package generator.gui.menubar;

import java.awt.event.ActionListener;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CMenuItem extends JMenuItem implements ITranslate
{
	private String textId;
	
	public CMenuItem(String textId, ActionListener listener)
	{
		this.textId = "GUI:" + textId;
		this.addActionListener(listener);
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		this.setText(CommandGenerator.translate(textId));
	}

}

package generator.gui.menubar;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CMenu extends JMenu implements ITranslate
{
	private String textId;

	public CMenu(String textId)
	{
		this.textId = "GUI:" + textId;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		this.setText(CommandGenerator.translate(textId));
		for (int index = 0; index < this.getItemCount(); index++)
		{
			JMenuItem item = this.getItem(index);
			if (item != null && item instanceof ITranslate) ((ITranslate) item).updateLang();
		}
	}

}

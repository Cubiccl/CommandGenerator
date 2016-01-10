package generator.gui.menubar;

import generator.interfaces.ITranslate;
import generator.main.Text;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CMenu extends JMenu implements ITranslate
{
	private Text text;

	public CMenu(Text text)
	{
		this.text = text;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		this.setText(this.text.getValue());
		for (int index = 0; index < this.getItemCount(); index++)
		{
			JMenuItem item = this.getItem(index);
			if (item != null && item instanceof ITranslate) ((ITranslate) item).updateLang();
		}
	}

}

package generator.gui.menubar;

import generator.CommandGenerator;
import generator.gui.panel.PanelSettings;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.interfaces.ITranslate;
import generator.main.Text;

import java.awt.AWTEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

@SuppressWarnings("serial")
public class CGMenuBar extends JMenuBar implements ITranslate, IClickEvent
{
	private static final int EXIT = 0, SETTINGS = 1;

	public CGMenuBar()
	{
		super();

		CMenu menu = new CMenu(new Text("GUI", "menu.menu"));
		menu.add(new CMenuItem(new Text("GUI", "menu.settings"), new ClickEvent(this, SETTINGS)));
		menu.add(new CMenuItem(new Text("GUI", "menu.exit"), new ClickEvent(this, EXIT)));
		this.add(menu);
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case SETTINGS:
				if (CommandGenerator.isInitialized()) PanelSettings.create();
				break;

			case EXIT:
				CommandGenerator.exit();
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		for (int index = 0; index < this.getMenuCount(); index++)
		{
			JMenu menu = this.getMenu(index);
			if (menu != null && menu instanceof ITranslate) ((ITranslate) menu).updateLang();
		}
	}

}

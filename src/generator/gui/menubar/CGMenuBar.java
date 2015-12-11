package generator.gui.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import generator.CommandGenerator;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.interfaces.ITranslate;
import generator.main.Settings;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class CGMenuBar extends JMenuBar implements ITranslate, IClickEvent
{
	private static final int EXIT = 0;

	public CGMenuBar()
	{
		super();

		CMenu menu = new CMenu("menu.menu");
		menu.add(new CMenuItem("menu.exit", new ClickEvent(this, EXIT)));
		this.add(menu);

		CMenu language = new CMenu("menu.language");
		for (String[] lang : CommandGenerator.getSettings().getAvailableLanguages())
		{
			JMenuItem item = new JMenuItem(lang[1]);
			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					CommandGenerator.getSettings().setSetting(Settings.LANGUAGE, lang[0]);
				}
			});
			language.add(item);
		}
		this.add(language);
	}

	@Override
	public void onClick(int componentID)
	{
		switch (componentID)
		{
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

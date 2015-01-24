package commandGenerator.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.CommandGenerator;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;
import commandGenerator.main.Settings;

@SuppressWarnings("serial")
public class CGMenubar extends JMenuBar
{

	private static JMenu menu, lang;
	private static JMenuItem reset, changelog, link, folder, exit;
	private static JMenuItem english, english_gb;

	/** The program's menubar. */
	public CGMenubar()
	{

		menu = new JMenu(Lang.get("GUI:menu"));
		lang = new JMenu(Lang.get("GUI:menu.lang"));

		reset = new JMenuItem(Lang.get("GUI:menu.reset"));
		changelog = new JMenuItem(Lang.get("GUI:menu.changelog"));
		link = new JMenuItem(Lang.get("GUI:menu.link"));
		folder = new JMenuItem(Lang.get("GUI:menu.folder"));
		exit = new JMenuItem(Lang.get("GUI:menu.exit"));

		english = new JMenuItem("English (US)");
		english_gb = new JMenuItem("English (GB)");

		menu.add(reset);
		menu.add(changelog);
		menu.add(link);
		menu.add(folder);
		menu.add(exit);
		add(menu);
		lang.add(english);
		lang.add(english_gb);
		add(lang);

		reset.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e)
			{
				CommandGenerator.gui.panelGeneral.panelOptions.reset();
			}
		});

		changelog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final JEditorPane pane = new JEditorPane("text/html", Resources.changelog(Resources.versions[0]));
				JScrollPane scrollpane = new JScrollPane(pane);
				scrollpane.getVerticalScrollBar().setUnitIncrement(20);

				final JList<String> list = new JList<String>(Resources.versions);
				list.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0)
					{
						pane.setText(Resources.changelog(list.getSelectedValue()));
					}
				});
				list.setSelectedIndex(Resources.versions.length - 1);
				JScrollPane scrollpanelist = new JScrollPane(list);
				scrollpanelist.getVerticalScrollBar().setUnitIncrement(20);

				JPanel panel = new JPanel(new GridLayout(1, 2));
				panel.setPreferredSize(new Dimension(600, 400));
				panel.add(scrollpanelist);
				panel.add(scrollpane);

				DisplayHelper.showMessage(panel, Lang.get("GUI:menu.changelog"));

			}
		});
		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
				{
					try
					{
						desktop.browse(new URL("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-tools/2142815").toURI());
					} catch (Exception e)
					{
						DisplayHelper.log("Couldn't access the Minecraft Forum thread.");
					}
				}
			}
		});
		folder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
				{
					try
					{
						desktop.open(new File(Resources.folder));
					} catch (Exception e)
					{}
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		english.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				CommandGenerator.opt.setLanguage(Settings.ENGLISH);
			}
		});
		english_gb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				CommandGenerator.opt.setLanguage(Settings.ENGLISH_GB);
			}
		});

	}

	public void updateLang()
	{
		menu.setText(Lang.get("GUI:menu"));
		lang.setText(Lang.get("GUI:menu.lang"));
		reset.setText("GUI:menu.reset");
		changelog.setText(Lang.get("GUI:menu.changelog"));
		link.setText(Lang.get("GUI:menu.link"));
		folder.setText(Lang.get("GUI:menu.folder"));
		exit.setText(Lang.get("GUI:menu.exit"));
	}

}

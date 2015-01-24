package commandGenerator;

import java.awt.Frame;

import commandGenerator.arguments.objects.InitObjects;
import commandGenerator.gui.MainWindow;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.FileHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;
import commandGenerator.main.Settings;

public class CommandGenerator
{

	/** The main window */
	public static MainWindow gui;
	/** The user's settings */
	public static Settings opt;

	public static void main(String[] args)
	{

		// Setting up
		FileHelper.setupFolder();
		Resources.setupVersions();
		opt = new Settings();
		Lang.initLang();
		InitObjects.init();

		// Creating the GUI
		gui = new MainWindow();
		gui.setExtendedState(Frame.MAXIMIZED_BOTH);

		// Finalizing
		FileHelper.setOption("version", Resources.versions[Resources.versions.length - 1]);
		if (CommandGenerator.opt.firstLaunch) DisplayHelper.showChangelog();

	}

}

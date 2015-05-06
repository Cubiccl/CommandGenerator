package commandGenerator;

import java.awt.Frame;

import commandGenerator.arguments.command.Command;
import commandGenerator.arguments.objects.InitObjects;
import commandGenerator.arguments.objects.Registry;
import commandGenerator.gui.MainWindow;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.FileHelper;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;
import commandGenerator.main.Settings;

public class Generator
{

	/** The main window */
	public static MainWindow gui;
	/** The user's settings */
	public static Settings opt;
	/** Language */
	public static Lang lang;
	public static Registry registry;

	/** The main method. Called when the program launches. */
	public static void main(String[] args)
	{

		// Setting up
		FileHelper.setupFolder();
		Resources.setupVersions();
		opt = new Settings();
		lang = new Lang();
		registry = new Registry();
		InitObjects.init();
		Command.initGui();

		// Creating the GUI
		gui = new MainWindow();
		gui.setExtendedState(Frame.MAXIMIZED_BOTH);

		// Finalizing
		FileHelper.setOption("version", Resources.versions[Resources.versions.length - 1]);
		if (Generator.opt.firstLaunch) DisplayHelper.showChangelog();

	}

	public static String translate(String textId)
	{
		return lang.get(textId);
	}

}

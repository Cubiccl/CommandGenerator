package generator;

import generator.gui.MainWindow;
import generator.gui.panel.CPanel;
import generator.gui.panel.LoadingPanel;
import generator.gui.panel.PanelConfirm;
import generator.interfaces.IConfirmState;
import generator.main.FileManager;
import generator.main.Settings;
import generator.main.State;
import generator.main.StateManager;
import generator.main.Translator;
import generator.registry.Registry;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class CommandGenerator
{
	private static CommandGenerator instance;

	public static void addState(String textID, CPanel component)
	{
		instance.stateManager.addState(textID, component);
	}

	public static void addStateWithConfirm(String textID, CPanel component, IConfirmState listener)
	{
		PanelConfirm confirm = new PanelConfirm(component, listener);
		addState(textID, confirm);
	}

	public static void clearActiveState()
	{
		instance.stateManager.clearActiveState();
	}

	/** Closes the Command Generator. */
	public static void exit()
	{
		getWindow().dispose();
	}

	public static State getActiveState()
	{
		return instance.stateManager.getActiveState();
	}

	public static Registry getRegistry()
	{
		return instance.registry;
	}

	public static Settings getSettings()
	{
		return instance.settings;
	}

	public static MainWindow getWindow()
	{
		return instance.window;
	}

	/** Prints a message into the console and the log file.
	 * 
	 * @param exception - The exception to log. */
	public static void log(Exception exception)
	{
		log(exception.getMessage());
		exception.printStackTrace();
	}

	/** Prints a message into the console and the log file.
	 * 
	 * @param exception - The String to log. */
	public static void log(String message)
	{
		System.out.println(message);
		FileManager.log(message);
	}

	public static void main(String[] args)
	{
		instance = new CommandGenerator();
		instance.create();
	}

	/** @param textId - The ID of the text to translate.
	 * @return A translation of the given text ID. */
	public static String translate(String textId)
	{
		if (instance.translator == null) return textId;
		return instance.translator.translate(textId);
	}

	public static void updateLang()
	{
		instance.translator.updateLang();
		instance.registry.updateLang();
		instance.stateManager.updateLang();
		getWindow().updateLang();
	}
	/** Contains all data. */
	private Registry registry;
	private Settings settings;
	/** Manages what state the Generator is at. */
	private StateManager stateManager;

	private Translator translator;

	private MainWindow window;

	private CommandGenerator()
	{}

	private void create()
	{
		this.settings = new Settings(FileManager.setupFolder());
		this.settings.init();
		log("Command generator loading...");
		this.createFrame();
		this.stateManager = new StateManager(getWindow().getTextAreaStates());
		this.translator = new Translator();
		getWindow().updateLang();
		addState("GUI:loading.loading", new LoadingPanel());
		this.registry = new Registry();
	}

	private void createFrame()
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run()
				{
					window = new MainWindow();
					window.setVisible(true);
				}
			});
		} catch (InvocationTargetException e)
		{
			log(e);
		} catch (InterruptedException e)
		{
			log(e);
		}
	}

}

package generator;

import generator.gui.MainWindow;
import generator.gui.panel.CPanel;
import generator.gui.panel.LoadingPanel;
import generator.gui.panel.PanelCommandSetup;
import generator.gui.panel.PanelConfirm;
import generator.interfaces.IConfirmState;
import generator.main.FileManager;
import generator.main.GenerationException;
import generator.main.Settings;
import generator.main.State;
import generator.main.StateManager;
import generator.main.Translator;
import generator.registry.ObjectCreator;
import generator.registry.Registry;
import generator.registry.command.Structure;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class CommandGenerator
{
	/** The instance of the generator. */
	private static CommandGenerator instance;

	/** Adds a new state.
	 * 
	 * @param textID - The ID of the state's name.
	 * @param component - The component to display. */
	public static void addState(String textID, CPanel component)
	{
		instance.stateManager.addState(textID, component);
	}

	/** Adds a new state. Will have OK and Cancel buttons.
	 * 
	 * @param textID - The ID of the state's name.
	 * @param component - The component to display.
	 * @param listener - Called when the user clicks on OK or Cancel. */
	public static void addStateWithConfirm(String textID, CPanel component, IConfirmState listener)
	{
		PanelConfirm confirm = new PanelConfirm(component, listener);
		addState(textID, confirm);
	}

	/** Deletes the current state. */
	public static void clearActiveState()
	{
		instance.stateManager.clearActiveState();
	}

	/** Closes the Command Generator. */
	public static void exit()
	{
		getWindow().dispose();
	}

	/** Generates the command and displays it. */
	public static void generate()
	{
		Structure structure = ((PanelCommandSetup) getActiveState().getComponent()).getSelectedStructure();
		try
		{
			getWindow().setCommand(structure.generate());
		} catch (GenerationException e)
		{
			e.showMessage();
		}
	}

	/** @return The current State. */
	public static State getActiveState()
	{
		return instance.stateManager.getActiveState();
	}

	/** @return The Registry, containing all Objects. */
	public static Registry getRegistry()
	{
		return instance.registry;
	}

	/** @return The Settings. */
	public static Settings getSettings()
	{
		return instance.settings;
	}

	/** @return The Main Window. */
	public static MainWindow getWindow()
	{
		return instance.window;
	}

	/** @return True if it has finished initializing, thus the user can interact. */
	public static boolean isInitialized()
	{
		return instance.initialized;
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

	/** Updates all translations of the Generator. Called when the user changes language. */
	public static void updateLang()
	{
		instance.translator.updateLang();
		instance.registry.updateLang();
		instance.stateManager.updateLang();
		getWindow().updateLang();
	}

	/** True if it has finished initializing, thus the user can interact. */
	private boolean initialized;
	/** Contains all data. */
	private Registry registry;
	private Settings settings;
	/** Manages what state the Generator is at. */
	private StateManager stateManager;
	/** Contains all translations. */
	private Translator translator;

	private MainWindow window;

	/** Constructor. */
	private CommandGenerator()
	{
		this.initialized = false;
	}

	/** Initializes all the program. */
	private void create()
	{
		this.settings = new Settings(FileManager.setupFolder());
		this.settings.init();
		FileManager.clearLog();

		log("Command generator loading...");
		this.createFrame();
		this.stateManager = new StateManager(getWindow().getTextAreaStates());
		this.translator = new Translator();
		getWindow().updateLang();

		LoadingPanel panel = new LoadingPanel();
		addState("GUI:loading.loading", panel);

		this.registry = new Registry();
		ObjectCreator.createObjects();

		panel.setDetail("GUI:loading.objects_end");
		this.registry.complete();

		panel.setDetail("GUI:loading.gui");
		PanelCommandSetup panelCommand = new PanelCommandSetup();

		clearActiveState();
		addState("GUI:object.command", panelCommand);
		this.initialized = true;
	}

	/** Creates the main window. */
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

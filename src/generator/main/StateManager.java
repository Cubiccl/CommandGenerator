package generator.main;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.gui.panel.CPanel;
import generator.interfaces.ITranslate;

import java.util.ArrayList;

/** Manages states. Determines what should be displayed. */
public class StateManager implements ITranslate
{
	/** List of all states. */
	private ArrayList<State> states;
	/** The TextArea displaying the states. */
	private CTextArea textAreaStates;

	/** Constructor.
	 * 
	 * @param textAreaStates - The TextArea to display the States. */
	public StateManager(CTextArea textAreaStates)
	{
		this.states = new ArrayList<State>();
		this.textAreaStates = textAreaStates;
		this.onStateChanged();
	}

	/** Adds a new state.
	 * 
	 * @param text - The state's name.
	 * @param component - The component to display. */
	public void addState(Text text, CPanel component)
	{
		this.states.add(new State(text, component));
		this.onStateChanged();
	}

	/** Deletes the current state. */
	public void clearActiveState()
	{
		this.states.remove(this.states.size() - 1);
		this.onStateChanged();
	}

	/** @return The current State. */
	public State getActiveState()
	{
		if (this.states.size() == 0) return null;
		return this.states.get(this.states.size() - 1);
	}

	/** Called whenever the active State changes. Updates the TextArea. */
	public void onStateChanged()
	{
		String states = "";
		for (int i = 0; i < this.states.size(); i++)
		{
			states += this.states.get(i).getName();
			if (i != this.states.size() - 1) states += " &#10145; ";
		}
		this.textAreaStates.setText(states);
		if (this.getActiveState() != null) CommandGenerator.getWindow().setPanelCurrent(this.getActiveState().getComponent());
	}

	@Override
	public void updateLang()
	{
		this.onStateChanged();
	}

}

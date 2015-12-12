package generator.main;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.gui.panel.CPanel;
import generator.interfaces.ITranslate;

import java.util.ArrayList;

public class StateManager implements ITranslate
{
	private ArrayList<State> states;
	private CTextArea textAreaStates;

	public StateManager(CTextArea textAreaStates)
	{
		this.states = new ArrayList<State>();
		this.textAreaStates = textAreaStates;
		this.onStateChanged();
	}

	public void addState(String textID, CPanel component)
	{
		this.states.add(new State(textID, component));
		this.onStateChanged();
	}

	public void clearActiveState()
	{
		this.states.remove(this.states.size() - 1);
		this.onStateChanged();
	}

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

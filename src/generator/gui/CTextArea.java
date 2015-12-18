package generator.gui;

import generator.main.Utils;

import javax.swing.JEditorPane;
import javax.swing.text.html.HTMLDocument;

/** This represents a non-editable text area, with html format. Basically, it should be used as a several-rows Label. */
@SuppressWarnings("serial")
public class CTextArea extends JEditorPane
{
	public CTextArea()
	{
		this("");
	}

	public CTextArea(String text)
	{
		super();
		this.setContentType("text/html");
		this.setBackground(null);
		this.setEditable(false);
		this.setHighlighter(null);
		String bodyRule = "body { font-family: " + Utils.font.getFamily() + "; " + "font-size: " + Utils.font.getSize() + "pt; }";
		((HTMLDocument) this.getDocument()).getStyleSheet().addRule(bodyRule);
		
		this.setText(text);
	}

}

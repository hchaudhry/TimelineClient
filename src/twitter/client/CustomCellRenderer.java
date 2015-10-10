package twitter.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import twitter4j.Status;

/**
 * Class used to have custom cell style in JList
 * 
 */
public class CustomCellRenderer extends JLabel implements ListCellRenderer {

	private static final Color HIGHLIGHT_COLOR = new Color(192, 192, 192);
	private static final Border LINE_BORDER = BorderFactory.createLineBorder(Color.GRAY, 1);
	private static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(2, 2, 2, 2);
	
	public CustomCellRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		
		Status status = (Status) value;
		setText("<html><body><font color='grey'> <div>"+ status.getUser().getName()
				+" <font color='#BDBDBD'> @"+ status.getUser().getScreenName() +" . "
				+ status.getCreatedAt().toString() 
				+"</font> </div> "+status.getText()+"</font></body></html>");

		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		
		setBorder(cellHasFocus ? LINE_BORDER : EMPTY_BORDER);

		return this;
	}

}

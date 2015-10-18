package twitter.client;

import java.awt.Color;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		
		Date date = null;
		String dateFormated = "";
		try {
			date = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(status.getCreatedAt().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String urlText = status.getUser().getProfileImageURL();
		URL url = null;
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon img = new ImageIcon(url);
		
		dateFormated = new SimpleDateFormat("MMM d").format(date);
		
		setIcon(img);
		setText("<html><body><font color='grey'> <div>"+ status.getUser().getName()
				+" <font color='#BDBDBD'> @"+ status.getUser().getScreenName() +" - "
				+ dateFormated 
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

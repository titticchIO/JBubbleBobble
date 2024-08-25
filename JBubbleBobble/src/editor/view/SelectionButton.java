package editor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import static editor.view.EditorPanel.SCALE;

public class SelectionButton extends JToggleButton {
	private BufferedImage img;
	private String code;

	public SelectionButton(BufferedImage img,String code) {
		this.img = img;
		this.code=code;
		Image scaledImg = img.getScaledInstance(40 * (int) SCALE, 40 * (int) SCALE, Image.SCALE_SMOOTH);
		setIcon(new ImageIcon(scaledImg));
		setPreferredSize(new Dimension(40 * (int) SCALE, 40 * (int) SCALE));
		setBackground(Color.BLACK);
	}


	public BufferedImage getImg() {
		return img;
	}
	public String getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(img);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectionButton other = (SelectionButton) obj;
		return Objects.equals(img, other.img);
	}
}
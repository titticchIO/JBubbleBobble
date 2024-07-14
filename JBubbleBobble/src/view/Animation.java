package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Animation implements Iterable<BufferedImage> {
	private List<BufferedImage> animationsFrames;

	public Animation() {
		animationsFrames = new ArrayList<BufferedImage>();
		animationsFrames.add(ImageLoader.importImg("/sprites/bubblun/image_5.png"));
		animationsFrames.add(ImageLoader.importImg("/sprites/bubblun/image_6.png"));
		animationsFrames.add(ImageLoader.importImg("/sprites/bubblun/image_7.png"));
		animationsFrames.add(ImageLoader.importImg("/sprites/bubblun/image_8.png"));

	}

	@Override
	public Iterator<BufferedImage> iterator() {
		return new Iterator<BufferedImage>() {
			int it = 0;

			@Override
			public BufferedImage next() {
				if (it < animationsFrames.size()-1)
					it++;
				else
					it = 0;
				return animationsFrames.get(it);
			}

			@Override
			public boolean hasNext() {
				return it<animationsFrames.size()-1;
			}
		};
	}

}

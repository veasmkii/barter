package uk.veasmkii.component;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import uk.veasmkii.component.Movement.Direction;

import com.artemis.Component;

public class Sprite extends Component {

	private final SpriteSheet sheet;
	private final Map<Direction, Animation> animation;

	public Sprite( final String ref ) throws SlickException {
		final int rows = 4, columns = 4;
		sheet = loadSheet( ref, rows, columns );
		animation = new HashMap<Direction, Animation>();
		loadAnimation( sheet, rows, columns );
	}

	private void loadAnimation( final SpriteSheet sheet, final int rows,
			final int columns ) {
		final int speed = 200;
		final boolean auto = false;

		//@formatter:off
		animation.put( Direction.NORTH, new Animation( sheet, 0, 3, 3, 3, true, speed, auto ) );
		animation.put( Direction.EAST,  new Animation( sheet, 0, 2, 3 ,2, true, speed, auto ) );
		animation.put( Direction.SOUTH, new Animation( sheet, 0, 0, 3, 0, true, speed, auto ) );
		animation.put( Direction.WEST,  new Animation( sheet, 0, 1, 3, 1, true, speed, auto ) );
		
		// Temp dirs
		animation.put( Direction.SOUTH_WEST, animation.get( Direction.SOUTH ) );
		animation.put( Direction.SOUTH_EAST, animation.get( Direction.SOUTH ) );
		animation.put( Direction.NORTH_EAST, animation.get( Direction.NORTH ) );
		animation.put( Direction.NORTH_WEST, animation.get( Direction.NORTH ) );
		//@formatter:on

	}

	public Animation getAnimation( final Direction direction ) {
		return animation.get( direction );
	}

	private SpriteSheet loadSheet( final String ref, final int rows,
			final int columns ) throws SlickException {
		final Image image = new Image( ref );
		return new SpriteSheet( image, image.getWidth() / columns,
				image.getHeight() / rows );
	}

}

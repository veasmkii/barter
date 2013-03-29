package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Component;

public class Imageable extends Component {
	@Getter @Setter private Image image;

	public Imageable() {}

	public Imageable( final Image image ) {
		setImage( image );
	}

	public Imageable( final String location ) throws SlickException {
		image = new Image( location );
	}

}

package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Position extends Component {

	@Getter @Setter private float x, y;

	public Position() {}

	public Position( final float x, final float y ) {
		setPosition( x, y );
	}

	public void setPosition( final float x, final float y ) {
		this.x = x;
		this.y = y;
	}

	public void addX( final float x ) {
		this.x += x;
	}

	public void addY( final float y ) {
		this.y += y;
	}

	@Override
	public String toString() {
		return String.valueOf( "Position X:" + x + " Y:" + y );
	}
}

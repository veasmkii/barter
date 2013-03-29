package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Velocity extends Component {

	@Getter @Setter private float x = 0, y = 0;

	public Velocity() {}

	public Velocity( final float x, final float y ) {
		setVelocity( x, y );
	}

	public void setVelocity( final float x, final float y ) {
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
		return String.valueOf( "Velocity X:" + x + " Y:" + y );
	}

}

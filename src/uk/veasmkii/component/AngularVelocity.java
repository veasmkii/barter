package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class AngularVelocity extends Component {

	@Getter @Setter private float speed = 0, angle = 0;

	public AngularVelocity() {}

	public AngularVelocity( final float speed, final float angle ) {
		setVelocity( speed, angle );
	}

	public void setVelocity( final float speed, final float angle ) {
		this.speed = speed;
		this.angle = angle;
	}

	public void addAngle( final float angle ) {
		this.angle = ( this.angle + angle ) % 360;
	}

}
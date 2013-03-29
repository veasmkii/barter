package uk.veasmkii.component;

import lombok.Getter;

import com.artemis.Component;

public class Expiry extends Component {

	@Getter private float durationDelta = 0, durationRemainingDeta = 0;

	public Expiry() {}

	public Expiry( final float duration ) {
		setDuration( duration );
	}

	public void setDuration( final float duration ) {
		this.durationDelta = duration;
		this.durationRemainingDeta = this.durationDelta;
	}

	public void reduceDuration( final float delta ) {
		if ( ( this.durationRemainingDeta - delta ) < 0 ) {
			this.durationRemainingDeta = 0;
			return;
		}
		this.durationRemainingDeta -= delta;
	}

	public boolean isExpired() {
		return durationRemainingDeta <= 0;
	}

}

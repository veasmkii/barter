package uk.veasmkii.component;

import lombok.Getter;

import com.artemis.Component;

public class Expiry extends Component {

	@Getter private float duration = 0, durationRemaining = 0;

	public Expiry() {}

	public Expiry( final float duration ) {
		setDuration( duration );
	}

	public void setDuration( final float duration ) {
		this.duration = duration;
		durationRemaining = this.duration;
	}

	public void reduceDuration( final float delta ) {
		if ( ( durationRemaining - delta ) < 0 ) {
			durationRemaining = 0;
			return;
		}
		durationRemaining -= delta;
	}

	public boolean isExpired() {
		return durationRemaining <= 0;
	}

}

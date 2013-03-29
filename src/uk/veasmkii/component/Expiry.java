package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Expiry extends Component {

	@Getter @Setter private float duration = 0;

	public Expiry() {}

	public Expiry( final float duration ) {
		setDuration( duration );
	}

	public void reduceDuration( final float duration ) {
		if ( ( this.duration - duration ) < 0 ) {
			this.duration = 0;
			return;
		}
		this.duration -= duration;
	}

	public boolean isExpired() {
		return duration <= 0;
	}

}

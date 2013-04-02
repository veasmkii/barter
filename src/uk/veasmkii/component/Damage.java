package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Damage extends Component {

	@Getter @Setter private float damage;

	public Damage() {}

	public Damage( final float damage ) {
		setDamage( damage );
	}

}

package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Player extends Component {

	@Getter @Setter private String name;

	public Player() {}

	public Player( final String name ) {
		setName( name );
	}

	@Override
	public String toString() {
		return String.valueOf( name );
	}

}

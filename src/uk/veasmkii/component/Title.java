package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.Color;

import com.artemis.Component;

public class Title extends Component {

	@Getter @Setter private String name = "";
	@Getter @Setter private Color color = Color.white;

	public Title() {}

	public Title( final String name ) {
		setName( name );
	}

	@Override
	public String toString() {
		return String.valueOf( name );
	}

}

package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.geom.Shape;

import com.artemis.Component;

public class Shapeable extends Component {

	@Setter @Getter private Shape shape;

	public Shapeable() {}

	public Shapeable( final Shape shape ) {
		setShape( shape );
	}

}

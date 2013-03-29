package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.geom.Shape;

import com.artemis.Component;

public class Drawable extends Component {

	@Getter @Setter private Shape shape;

}

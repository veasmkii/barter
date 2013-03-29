package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Size extends Component {

	@Getter @Setter private int width, height;

	public Size() {}

	public Size( final int width, final int height ) {
		setSize( width, height );
	}

	public void setSize( final int width, final int height ) {
		this.width = width;
		this.height = height;
	}

	@Override
	public String toString() {
		return String.valueOf( "Size Width:" + width + " Height:" + height );
	}

}

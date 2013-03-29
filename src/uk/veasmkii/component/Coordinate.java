package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Coordinate extends Component {

	@Getter @Setter private int x = -1, y = -1;

	public Coordinate() {}

	public Coordinate( final int x, final int y ) {
		setCoordinates( x, y );
	}

	public void setCoordinates( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}

	public void addX( final float x ) {
		this.x += x;
	}

	public void addY( final float y ) {
		this.y += y;
	}

	public void addXY( final float x, final float y ) {
		addX( x );
		addY( y );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = ( prime * result ) + x;
		result = ( prime * result ) + y;
		return result;
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		final Coordinate other = (Coordinate) obj;
		if ( x != other.x )
			return false;
		if ( y != other.y )
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf( "Coordinate X:" + x + " Y:" + y );
	}
}

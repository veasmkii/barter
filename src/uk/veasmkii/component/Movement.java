package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Movement extends Component {

	@Getter @Setter private Direction direction = Direction.SOUTH;
	@Getter @Setter private int amount = 0;
	@Getter @Setter private Expiry expiry = new Expiry();

	public Movement() {}

	public void addAmount( final int amount ) {
		this.amount += amount;
	}

	@Override
	public String toString() {
		return String.valueOf( direction );
	}

	public static enum Direction {
		NORTH,
		NORTH_EAST,
		EAST,
		SOUTH_EAST,
		SOUTH,
		SOUTH_WEST,
		WEST,
		NORTH_WEST;
	}

}

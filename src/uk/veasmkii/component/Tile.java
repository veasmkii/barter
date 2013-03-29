package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.Color;

import com.artemis.Component;

public class Tile extends Component {

	public static final int TILE_WIDTH = 32, TILE_HEIGHT = 32;

	@Getter @Setter Biome biome = Biome.NULL;

	public static enum Biome {
		NULL( Color.gray ),
		WATER( Color.blue ),
		GRASS( Color.green ),
		MEADOW( Color.green.darker() ),
		DESERT( Color.orange ),
		FOREST( Color.green.darker().darker() );

		@Getter private final Color color;

		Biome( final Color color ) {
			this.color = color;
		}
	}
}

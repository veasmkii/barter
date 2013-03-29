package uk.veasmkii.component;

import lombok.Getter;

import org.newdawn.slick.Color;

import com.artemis.Component;

public class Biome extends Component {

	public static enum Biomes {
		WHITE( Color.white ),
		NULL( Color.gray ),
		BLACK( Color.black ),
		WATER( Color.blue ),
		GRASS( Color.green ),
		MEADOW( Color.green.darker() ),
		DESERT( Color.orange ),
		FOREST( Color.green.darker().darker() );

		@Getter private final Color color;

		Biomes( final Color color ) {
			this.color = color;
		}
	}

}

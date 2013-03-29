package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.particles.ConfigurableEmitter;

import com.artemis.Component;

public class Sparticle extends Component {

	@Getter @Setter ConfigurableEmitter emitter;

	public Sparticle() {}

	public Sparticle( final ConfigurableEmitter emitter ) {
		setEmitter( emitter );
	}

}

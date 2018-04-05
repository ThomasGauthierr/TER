package core.strategy;

import core.ValueTimestamp;

import java.util.List;

public interface IOscillatorStrategy {

	boolean isOscillating(List<ValueTimestamp> l);
	
	

	
}

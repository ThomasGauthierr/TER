package core.strategy;

import java.util.List;

public interface IOscillatorStrategy {

	boolean isOscillating(List<ValueTimeStamp> l);
	
	
	class ValueTimeStamp{
		private int value;
		private long time;
		
		public ValueTimeStamp(int value, long time) {
			this.value = value;
			this.time = time;
		}
		
		public int getValue() {
			return value;
		}
		public long getTime() {
			return time;
		}
		
	}
	
}

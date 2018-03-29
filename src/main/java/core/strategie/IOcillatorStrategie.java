package core.strategie;

import java.util.List;

public interface IOcillatorStrategie {

	public boolean isOcillating(List<ValueTimeStamp> l);
	
	
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

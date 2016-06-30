package cvg;

public class Incoming {

	private String  incomingId;
	private String  sourceId;
        private String targetId;

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }
	public String getIncomingId() {
		return incomingId;
	}
	public void setIncomingId(String incomingId) {
		this.incomingId = incomingId;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	
}

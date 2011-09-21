package vn.com.epi.gate.vnpostagger;

public class AnnotationData {

	private long start;
	private long end;
	private String tag;
	
	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public String getTag() {
		return tag;
	}

	public AnnotationData(long start, long end, String tag) {
		super();
		this.start = start;
		this.end = end;
		this.tag = tag;
	}
	
}

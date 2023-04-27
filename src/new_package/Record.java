package new_package;

public class Record {
	private String line;
	private int lineNumber;
	
	public Record() {
		super();
	}

	public Record(String line, int lineNumber) {
		super();
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}

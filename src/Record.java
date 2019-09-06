
public class Record {
	
	public static final int MAX_FIELD_NUMBER = 8;
	
	static int ID = 0;
	private int recordID;
	private boolean isEmpty;
	int[] fields;
	String typeName;
	int noOfTypeField;
	
	public Record(String name,int number,int[] fields ) {
		ID++;
		this.recordID=ID;
		this.isEmpty = false;
		this.fields=fields;
		this.typeName= name;
		this.noOfTypeField= number;
	}
	
	@Override
	public String toString() {
		String result = "";
		result+= Integer.toString(recordID);
		result+= "//";
		result+= typeName+"//";
		result+= Integer.toString(noOfTypeField);
		result+= "//";
		for(int i =0;i<fields.length;i++) {
			result+= Integer.toString(fields[i]);
			result+= "//";
		}
		return result;
	}
	


}

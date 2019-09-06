
public class Type {

	private String name ;
	private int numberOfRecord;
	private String[] fields;
	
	
	public Type(String name,int numberOfRecord,String[] fields) {
		this.name= name;
		this.numberOfRecord = numberOfRecord;
		this.fields=fields;
	}
	
	public int getNumberOfRecord() {
		return numberOfRecord;
	}

	public void setNumberOfRecord(int numberOfRecord) {
		this.numberOfRecord = numberOfRecord;
	}

	public String getName() {
		return name;
	}

	public String[] getFields() {
		return fields;
	}

	public static Type create(String name,int number,String[] str) {
		Type type = new Type(name,number,str);
		return type;
		
	}
	
	@Override
	public String toString() {
		String[] asd = {
				name,
				Integer.toString(numberOfRecord),
				String.join("£", fields)
				};
		return String.join("#",asd);
	}
	
	
	
}

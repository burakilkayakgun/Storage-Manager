import java.util.ArrayList;

public class Page {

	PageHeader pageHeader;
	ArrayList<Record> record ;
	boolean isEmpty;
	
	public Page(Record r,DiskDirectory diskDir) {
		record = new ArrayList<>();
		this.pageHeader = new PageHeader(diskDir.pageSet.get(r.typeName + ".txt"),0,0);
		isEmpty = false;
		record.add(r);
		this.pageHeader.increaseOfRecord();
	}
	
	public Page(int id,Record r,DiskDirectory diskDir) {
		record = new ArrayList<>();
		this.pageHeader = new PageHeader(id,0,0);
		isEmpty = false;
		record.add(r);
		this.pageHeader.increaseOfRecord();
	}
	
	@Override
	public String toString() {
		String result = pageHeader.toString();
		if(!record.isEmpty()) {
			result+= "#";
			result+= record.toString();
		}
		
		return result;
	}
	
	
}

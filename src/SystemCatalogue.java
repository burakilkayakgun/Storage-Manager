import java.util.ArrayList;

public class SystemCatalogue {

	PageHeader pageHeader;
	ArrayList<String> record ;
	
	public SystemCatalogue() {
		this.pageHeader = new PageHeader(0, 0, 0);
		this.record = new ArrayList<>();
	}
	
	public SystemCatalogue(int ID) {
		this.pageHeader = new PageHeader(ID, 0, 0);
		this.record = new ArrayList<>();
	}
	public String getName() {
		return "SystemCatalogue.txt";
	}

}

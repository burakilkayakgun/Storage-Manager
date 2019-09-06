
public class PageHeader {
	
	private int pageID;
	private int numberOfRecord;
	private int pointerToNextPage;
	
	public PageHeader(int pageID,int numberOfRecord,int pointerToNextPage) {
		this.pageID= pageID;
		this.numberOfRecord = numberOfRecord;
		this.pointerToNextPage=pointerToNextPage;
	}

	public int getNumberOfRecord() {
		return numberOfRecord;
	}

	public void setNumberOfRecord(int numberOfRecord) {
		this.numberOfRecord = numberOfRecord;
	}

	public int getPointerToNextPage() {
		return pointerToNextPage;
	}

	public void setPointerToNextPage(int pointerToNextPage) {
		this.pointerToNextPage = pointerToNextPage;
	}

	public int getPageID() {
		return pageID;
	}
	
	public void increaseOfRecord() {
		this.numberOfRecord++;
	}
	
	public void decreaseOfRecord() {
		this.numberOfRecord--;
	}
	
	@Override
	public String toString() {
		String[] asd = { 
				Integer.toString(pageID),
				Integer.toString(numberOfRecord),
				Integer.toString(pointerToNextPage)
				};
		
		return String.join("#", asd);
	}
	
}

import java.io.*;
import java.util.*;

public class DiskDirectory {
	
	private static final String freeSpace = "freeSpace";
	Map<String, Integer> pageSet ;
	ArrayList<Type> typeList ;
	ArrayList<Page> pageList;
	
	public DiskDirectory(){
		pageSet = new TreeMap<String, Integer>();
		pageSet.put(freeSpace, 1);
		typeList = new ArrayList<Type>();
		pageList= new ArrayList<Page>();
	}
	
	public DiskDirectory(int a) throws IOException {
		pageSet = new TreeMap<String, Integer>();
		pageSet.put(freeSpace, 1);
		addFile(freeSpace, null);
		typeList = new ArrayList<Type>();
		pageList= new ArrayList<Page>();
	}
	
	public int getFreeAdress() throws NullPointerException {
		return this.pageSet.get(freeSpace);
	}
	
	public int getAdress(String str) {
		return this.pageSet.get(str+".txt");
	}
	
	public void addFile(String a,Type b,String[] field,SystemCatalogue system) throws IOException {
		if(!this.pageSet.containsKey(system.getName())){
			this.pageSet.put(system.getName(), 0);
		}
		if(!system.record.contains(a)) {
			addFile(a,b);   // for disk directory
		}
	}
	
	public void addFile(String a,Type b) throws IOException {
		if(!a.equals(freeSpace)) {
			this.pageSet.put(a+".txt", (this.pageSet.get(freeSpace)));
			this.pageSet.put(freeSpace, (getFreeAdress()+1))  ;
		}
		if(b != null) {
			this.typeList.add(b);
		}
	}
	
	public void deleteFile(String a) throws IOException {
		this.pageSet.remove(a+".txt");
		for(int i=0;i<this.typeList.size();i++) {
			if(this.typeList.get(i).getName().equalsIgnoreCase(a)) {
				this.typeList.remove(this.typeList.get(i));
			}
		}
	}
	
	public boolean getPage(String st) {
		for(Page d : this.pageList) {
			if(d.record.get(0).typeName.equals(st)) {
				return true;
			}
		}
		return false;
	}
	
	
	
}

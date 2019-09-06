import java.io.* ;
import java.util.* ;


public class storageManager {
	
	private static final int MAX_RECORD_NUMBER_FOR_PAGE = 32;
	private static final int MAX_RECORD_NUMBER_FOR_SYSTEM_CATALOGUE = 19 ;
	private static final String DATABASE = "database.txt";
	static DiskDirectory diskDirectory;
	static SystemCatalogue sysCat = new SystemCatalogue();	
	
	public static void main(String[] args) throws IOException {
		if(args.length == 1 && args[0].equals("clear")) {
			File file = new File(DATABASE);
			file.delete();
		}
			File inputFile = new File(args[0]);
			File outputFile = new File(args[1]);
			database();
			process(inputFile,outputFile);
			write_db();
	}
	
	private static void write_db() throws IOException {
		BufferedWriter bw_0 = new BufferedWriter(new FileWriter(new File(DATABASE),true));
		String asd = diskDirectory.pageSet.toString();
		bw_0.write(asd);
		bw_0.newLine();
		bw_0.write("#####");
		bw_0.newLine();
		asd = diskDirectory.typeList.toString();
		bw_0.write(asd);
		bw_0.newLine();
		bw_0.write("#####");
		bw_0.newLine();
		asd = diskDirectory.pageList.toString();
		bw_0.write(asd);
		bw_0.newLine();
		bw_0.write("#####");
		bw_0.newLine();
		bw_0.close();
	}

	
	private static void read_db() throws IOException {
		BufferedReader br_0 = new BufferedReader(new FileReader(new File(DATABASE)));
		Scanner sca=new Scanner(new File(DATABASE));
		int lineCounter = 0;
		while(sca.hasNextLine()) {
			sca.nextLine();
			lineCounter++;
		}
		while((lineCounter-6)!=0) {
			br_0.readLine();
			lineCounter--;
		}
		sca.close();
		
		String line = br_0.readLine();
		
	    while(true) {
	       	String[] line1 = line.split(",");
	       	for(int i=0;i<line1.length;i++) {
	       		line1[i]=line1[i].substring(1, line1[i].length());
	       		String[] a = line1[i].split("=");
	       		String key=a[0];
	       		if(a[1].substring(a[1].length()-1).equals("}")) {
	       			a[1]=a[1].substring(0,a[1].length()-1);
	       		}
	       		int value = Integer.parseInt(a[1]);
	       		diskDirectory.pageSet.put(key, value);
	       		
	       	}
	       		
	       	line = br_0.readLine();
	       	if(line.equals("#####")) {
	       			break;
	       	}
	    }
	    line = br_0.readLine();
	    
	    while(true) {
	    	if(line.equals("[]")) {
	    		break;
	    	}
	    	String[] line1 = line.split(",");
	    	if(line1.length==0) {
    			break;
    		}
	    	for(int i=0;i<line1.length;i++) {
	    		
	    		line1[i]=line1[i].substring(1, line1[i].length());
	    		String[] a = line1[i].split("#");
	    		if(a.length==0) {
	    			break;
	    		}
	    		if(!a[a.length-1].isEmpty() && a[a.length-1].substring(a[a.length-1].length()-1).equals("]")) {
	       			a[a.length-1]=a[a.length-1].substring(0,a[a.length-1].length()-1);
	       		}
	    		
	    		String type = a[0];
	    		int nOfField= Integer.parseInt(a[1]);
	    		String[] fiel = a[2].split("£");
	    		
	    		Type type2 = Type.create(type,nOfField,fiel);
				
				diskDirectory.typeList.add(type2);
	    	}
	    	line = br_0.readLine();
	       	if(line.equals("#####")) {
	       			break;
	       	}
	    }
	    
	    line = br_0.readLine();
	    while(true) {
	    	if(line.equals("[]")) {
	    		break;
	    	}
	    	String[] line1 = line.split("]");
	    	if(line1.length==0) {
    			break;
    		}
	    	for(int i=0;i<line1.length;i++) {
	    		line1[i]=line1[i].substring(1, line1[i].length());
	    		String[] a = line1[i].split("#");
	    		if(a.length==0) {
	    			break;
	    		}
	    		if(a.length!=0 && a[0].charAt(0)==' ') {
	    			a[0] = a[0].substring(1);
	    		}
	    		int pagId=Integer.parseInt(a[0]);
	    		int nOfRecord=Integer.parseInt(a[1]);
	    		int pointer = Integer.parseInt(a[2]);
	    		
	    		String[] reco= a[3].split(",");
	    		for(int t=0;t<reco.length;t++) {
	    			reco[t]=reco[t].substring(1,reco[t].length()-2);
	    			String[] recor = reco[t].split("//");
	    			
	    			int recId=Integer.parseInt(recor[0]);
	    			String recName= recor[1];
	    			int nOftypeFiel = Integer.parseInt(recor[2]);
	    			int[] fieldd = new int[recor.length-3];
	    			for(int y=3;y<recor.length;y++) {
	    				fieldd[y-3]=Integer.parseInt(recor[y]);
	    			}
	    			
	    			Record reccor = new Record(recName,fieldd.length,fieldd);
	    			Page page = new Page(pagId,reccor,diskDirectory);
	    			diskDirectory.pageList.add(page);
	    		}
	 
	    	}
	    	line = br_0.readLine();
	       	if(line.equals("#####")) {
	       			break;
	       	}
	    }
	    br_0.close();
	}
	
	private static void database() throws IOException {
		int k = checkDb(new File(DATABASE));
		if(k==1) {  // there exist a database file
			diskDirectory = new DiskDirectory();
			read_db();
		//	System.out.println("exist");
			
		}else {  // there is not exist a database file , it should be created
			diskDirectory = new DiskDirectory(0);
		//	System.out.println("not exist");
		}
	}

	private static int checkDb(File file) {
		if(file.exists()) {
			return 1;
		}else {
			return 0;
		}
	}

	private static void process(File inputFile, File outputFile) throws IOException {
		FileWriter fileWriter = new FileWriter(outputFile);
		Scanner scan = new Scanner(inputFile);
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			if(line.isEmpty()) {
				break;
			}
			Scanner lineScanner = new Scanner(line);
			String operation = lineScanner.next();
			String type = lineScanner.next();
			switch(type) 
			{
			case "type" :
				switch(operation) 
				{
				case "create" :
					String typeName = lineScanner.next();
					int fieldNumber = lineScanner.nextInt();
					if(fieldNumber> 8) {
					//	System.out.println("A record can has 8 fields at most");
						break;
					}
					String[] field = new String[fieldNumber];
					for(int i =0 ; i<fieldNumber;i++) {
						String fieldName = lineScanner.next();
						field[i] = fieldName;
					}
					Type type2 = Type.create(typeName,fieldNumber,field);
					
					diskDirectory.addFile(typeName, type2, field, sysCat);
					if( !sysCat.record.contains(typeName)   &&  sysCat.pageHeader.getNumberOfRecord() == MAX_RECORD_NUMBER_FOR_SYSTEM_CATALOGUE) { 
						int newFreeAdress = diskDirectory.getFreeAdress();
						sysCat.pageHeader.setPointerToNextPage(newFreeAdress);
						sysCat = new SystemCatalogue(newFreeAdress);
						sysCat.pageHeader.increaseOfRecord();
					}else {
						sysCat.pageHeader.increaseOfRecord();
						
					}
					
					if(!sysCat.record.contains(typeName)) {
						sysCat.record.add(typeName);
					}
					break;
				case "delete":
					String deleteName = lineScanner.next();
					diskDirectory.deleteFile(deleteName);
					sysCat.record.remove(deleteName);
					sysCat.pageHeader.decreaseOfRecord();
					
					
					for (Iterator<Page> iterator = diskDirectory.pageList.iterator(); iterator.hasNext(); ) {
						Page p= iterator.next();
						if(!p.record.isEmpty() &&  p.record.get(0).typeName.equals(deleteName)) {
							for(int a=0;a<p.record.size();a++) {
								p.record.remove(a);
								p.pageHeader.decreaseOfRecord();
								
							}
							iterator.remove();
						}
					}
					
					break;
				case "list":
					BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile,true));
					Collections.sort(sysCat.record);
					for(String asd: sysCat.record) {
						bw.write(asd);
						bw.newLine();
					}
					bw.close();
					break;
				}
			break;
			case "record" :
				switch(operation)
				{
				case "create" :
					String typeName = lineScanner.next();
					int nOfRecord=0;
					
					for(int i =0;i<diskDirectory.typeList.size();i++) {
						if(diskDirectory.typeList.get(i).getName().equals(typeName)) {
							nOfRecord = diskDirectory.typeList.get(i).getNumberOfRecord(); 
						}
					}
					int[] re = new int[nOfRecord] ;
					for(int i=0;i<nOfRecord;i++) {
						int k = lineScanner.nextInt();
						re[i]=k;
					}
					
					Record rec = new Record(typeName,nOfRecord,re);
					if(diskDirectory.getPage(typeName)) {
						for(Page p:diskDirectory.pageList) {
							if(p.record.get(0).typeName.equals(typeName)) {
								if(p.record.size() == MAX_RECORD_NUMBER_FOR_PAGE) {
									int newFreeAdress = diskDirectory.getFreeAdress();
									p.pageHeader.setPointerToNextPage(newFreeAdress);
									p = new Page(newFreeAdress,rec,diskDirectory);
									diskDirectory.pageSet.put(" freeSpace", newFreeAdress+1);
									p.pageHeader.increaseOfRecord();
								}else {
									p.record.add(rec);
									p.pageHeader.increaseOfRecord();
								}
							}
						}
					}else {
						Page page = new Page(rec,diskDirectory);
						diskDirectory.pageList.add(page);
					}
					
					break;
				case "delete" :
					String deleteName = lineScanner.next();
					int k = lineScanner.nextInt();
					for(Page p: diskDirectory.pageList) {
						if(p.record.get(0).typeName.equals(deleteName)) {
							for(int a=0;a<p.record.size();a++) {
								if(p.record.get(a).fields[0]==k) {
									p.record.remove(a);
									p.pageHeader.decreaseOfRecord();
								}
							}
						}
					}
					break;
				case "update" :
					String updateName = lineScanner.next();
					int nOfRecord_2=0;
					for(int i =0;i<diskDirectory.typeList.size();i++) {
						if(diskDirectory.typeList.get(i).getName().equals(updateName)) {
							nOfRecord_2 = diskDirectory.typeList.get(i).getNumberOfRecord(); 
						}
					}
					
					int[] new_value = new int[nOfRecord_2] ;
					
					for(int i=0;i<nOfRecord_2;i++) {
						int w = lineScanner.nextInt();
						new_value[i]=w;
					}
					for(Page p : diskDirectory.pageList) {
						if(p.record.get(0).typeName.equals(updateName)) {
							for(int a=0;a<p.record.size();a++) {
								if(p.record.get(a).fields[0]==new_value[0]) {
									System.arraycopy(new_value, 0, p.record.get(a).fields, 0, p.record.get(a).fields.length);
								}
							}
						}
					}
					break;
				case "search" :
					String searchName = lineScanner.next();
					int nOfRecord_3=lineScanner.nextInt();
					BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile,true));
					for(Page p : diskDirectory.pageList) {
						if(!p.record.isEmpty() && p.record.get(0).typeName.equals(searchName)) {
							for(int a=0;a<p.record.size();a++) {
								if(p.record.get(a).fields[0]==nOfRecord_3) {
									for(int q=0;q<p.record.get(a).fields.length;q++) {
										bw.write(p.record.get(a).fields[q]+" ");
									}
								}
							}
						}
					}
					bw.newLine();
					bw.close();
					
					break;
				case "list" :
					String listName = lineScanner.next();
					BufferedWriter bw_2 = new BufferedWriter(new FileWriter(outputFile,true));
					for(Page p : diskDirectory.pageList) {
						if(!p.record.isEmpty()) {
							if(p.record.get(0).typeName.equals(listName)) {
								for(int a=0;a<p.record.size();a++) {
									Collections.sort(p.record, new SortbyKey());
									for(int q=0;q<p.record.get(a).fields.length;q++) {
										bw_2.write(p.record.get(a).fields[q]+" ");
									}
									bw_2.newLine();
								}
							}
						}
					}
					bw_2.close();
					break;
				}
			break;
			}
			
		lineScanner.close();	
		}
		fileWriter.close();
		scan.close();
	}

}

class SortbyKey implements Comparator<Record> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(Record a, Record b) 
    { 
        return a.fields[0] - b.fields[0]; 
    } 
}

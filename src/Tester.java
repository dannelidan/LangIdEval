import java.util.*;
import java.io.*;

/**
 * Class to perform tests on Language Detection Libraries {@link LDL}  
 * and print the results.
 *
 */


public class Tester{

    private static final int MAX_LIST_SIZE = 10000;
    
    private int indexcounter;
    private TreeMap<String, Integer> PostByLang;  
    private  TreeMap<String, Integer> CorrByLang;
    
    private  TreeMap<String, HashMap<String, Integer>> LangByLang;

    private  LinkedList<Blogpost> lista;
    private  LinkedList<LangPrediction> langscout;

    
    public Tester(){
	indexcounter = 0;	
	
	PostByLang = new TreeMap<String, Integer>();
	CorrByLang = new TreeMap<String, Integer>();
    
	LangByLang= new TreeMap<String, HashMap<String, Integer>>();
	
	lista = new LinkedList<Blogpost>();
	
	langscout = new LinkedList<LangPrediction>();
	
	
	
	
    }
    


    /**
     * Method to compare language codes of the key (the list of 
     * {@link Blogpost}) with the language codes guessed by the LDL
     * library.
     */

    private void compareToKey(){
	
	for (int i = 0; i< langscout.size(); i++){
	    
	    String thekey = langscout.get(i).getCorrLangCode();
	    
	    String theguess = langscout.get(i).getPredictedLangCode();
	    
	    if (! LangByLang.containsKey(thekey)){
		LangByLang.put(thekey, new HashMap<String, Integer>());
		LangByLang.get(thekey).put(theguess, new Integer(1));
		
	    }
	    else if (! LangByLang.get(thekey).containsKey(theguess)){
		
		LangByLang.get(thekey).put(theguess, new Integer(1));
		
	    }
	    else {
		Integer former = LangByLang.get(thekey).get(theguess);
		LangByLang.get(thekey).put(theguess, former + new Integer(1));
		
	    }
	    




	    if (thekey.equals(theguess)){
		if (! CorrByLang.containsKey(thekey)){
		    CorrByLang.put(thekey, new Integer(1));
		}
		
		else {
		    Integer former =  CorrByLang.get(thekey);
		    CorrByLang.put(thekey, former + new Integer(1)); 
		    
		    
		}
	    }
	    
	}
    }


    /**
     * Prints detection speed per language to the
     * specified <code>PrintStream</code>.
     */



    public void printTimeByLang(PrintStream out){
    
	out.println("<Detection speed per language>");	

	out.print("Lang");
	out.printf("%7s", "Speed");
	out.println();
	double allovertime = 0.00;
	double alloverbytes = 0.00;
	double alloverbytespersec = 0.00;

	for (String lang: LangByLang.keySet()){
	    double time = 0.00;
	    double mbytespersec = 0.00;
	    double bytes = 0.00;
	    int noofposts = 0;

	    out.print(lang + ": ");
	    for ( LangPrediction candid : langscout){
		if (lang.equals(candid.getCorrLangCode())){ 
		    time = time + candid.getNanoTime();
		    bytes = bytes + candid.getCharCount();
		    allovertime += time;
		    alloverbytes += bytes;
		    noofposts++;
		}
		
	    }
	    
	    
	    
	    
	    mbytespersec = (bytes/1000000)/(time/1000000000);
	    out.printf("%7s", (String.format("%4.2f", mbytespersec) + " MB/sec"));
	    out.printf("%26s", "Av. bytes/post: " + bytes/noofposts);
	    out.println();

	}

	alloverbytespersec = (alloverbytes/1000000)/(allovertime/1000000000);
	out.println("Average speed/language: " + String.format("%4.2f", alloverbytespersec) + " MB/sec");

    }

    /**
     * Prints the wrongly detected posts along 
     * with the list index of the {@link Blogpost} to the 
     * specified <code>PrintStream</code>.
     */
    

    
    public void printWrongPosts(PrintStream out){
	

	out.println("<Wrongly detected posts>");	
	
	for ( LangPrediction bp : langscout) {
	    
	    if (! bp.getPredictedLangCode().equals(bp.getCorrLangCode())){
		
		out.println(bp.getIndex() +". " + bp.getCorrLangCode() +" " + bp.getPredictedLangCode());
		out.println(lista.get(bp.getIndex()).getPost()); 
		
		
	    }
	        
	}
	
	
    }
    
    
    /**
     * Prints a confusion matrix to the 
     * specified <code>PrintStream</code>showing the distribution
     * of falsely and correctly detected languages by the LDL. 
     * 
     */
    
    
    public void printConfusionMatrix(PrintStream out){
	out.println("<Confusion Matrix>");
	out.print(" ");
	for (String str : LangByLang.keySet()){
	    
	    out.printf("%4s",str);
	}
	out.println();
	for (String st: LangByLang.keySet()){
	    
	    out.print(st); 
	    
	    for (String str : LangByLang.keySet()){
		if (LangByLang.get(st).containsKey(str)){
		    out.printf("%4s",LangByLang.get(st).get(str));
		}
		else{
		    out.printf("%4s",0);
		}
	    }
	    
	    out.println();

	    
	}

	out.println("<Other Wrongly detected languages>");
	for (String str : LangByLang.keySet()){

	    for (String str2: LangByLang.get(str).keySet()){
		if (! LangByLang.containsKey(str2)){
		    out.println(str +"> " + str2 +": " + LangByLang.get(str).get(str2));
		}
	    }
	}








    }

    /**
     * Printing the detection accuracy of the LDL for each 
     * language code within the 
     * {@link Blogpost} list to the specified <code>PrintStream</code>.
     *
     *
     */

    public void printAccuracy(PrintStream out){
	
	
	Integer totalt;
	Integer hittat;
	double accuracy = 0.00;
	double temp = 0.00;
	double averageaccuracy = 0.00;

	out.println("------------------------------------------------------------------");
	out.println("<Detection accuracy>");
	out.printf("Lang");
	out.printf("%6s","Found");
	out.printf("%6s","(key)");
	out.printf("%8s","Accuracy\n");
        for ( String key : PostByLang.keySet() ) {  // "for each key in the map's key set"
	    totalt = PostByLang.get(key);
	    if (CorrByLang.containsKey(key)){
		hittat = CorrByLang.get(key);
	    }
	    else {
		hittat = 0;
	    }
	    
	    accuracy = (double)hittat/(double)totalt;
	    temp += accuracy;

	    out.print(key +":");
	    out.printf("%6s",hittat);
	    out.printf("%6s","("+totalt+")");
	    out.printf("%8s", String.format("%.2f", (accuracy* 100)));
	    out.println(" %");
	   
	}

	averageaccuracy = temp/PostByLang.size();
	out.println("\nAverage accuracy: " + String.format("%.2f", (averageaccuracy * 100)) + "%");
       out.println("-----------------------------------------------------");
	
       
    }
    

    /** 
     * Gathers key data from the specified file into 
     * a list of {@link Blogpost} and gathers detection 
     * guess data to a list of {@link LangPrediction}.
     */


    public void collectData(LDL library, File file) throws FileNotFoundException{
	
	System.out.println("Detecting data from " + file.getName());
	
	LinkedList<Blogpost> nylista = DocumentReader.readData(file);
	
	System.out.println("Hittade poster: " + nylista.size());
	
	
	
	long elapsedTime;
	System.out.println("Detecting...");
	for (Blogpost post: nylista){
	    if ((lista.size() + nylista.size()) <= MAX_LIST_SIZE){
		
		String guess="error";
		String postInFocus = post.getPost();
		int bytes= 0;
		try{
		    bytes = postInFocus.getBytes("UTF-8").length;
		//Gör en loop som kör 10 rundor, spara average time.
		}
		catch (UnsupportedEncodingException e){
		    System.out.println("Encoding error!");
		    System.out.println("Exiting...");
		    System.exit(0);
		}
		
		library.detect(postInFocus);
		guess = library.getLangGuess();
		elapsedTime = library.getDetectionTime();

		
		if (! guess.equals("error") || ! guess.equals(null)){
		    
		    String thekey = post.getLangCode();
		    langscout.add(new LangPrediction(indexcounter,guess,thekey,elapsedTime, bytes));
		    
		    
		    if (! PostByLang.containsKey(thekey)){
			PostByLang.put(thekey, new Integer(1));
		    }
		    
		    else {
			Integer former = PostByLang.get(thekey);
			PostByLang.put(thekey, former + new Integer(1)); 
			
			
		    }   
		    
		    
		    
		
		}
		else {
		    System.err.println("problems?: " + postInFocus);
		}
		
		indexcounter++;
		
	    }
	    else {
		System.out.println("To much data! Quitting...");
		return;
	    }
	    
	    
	}
	
	
	    lista.addAll(nylista);
	   



	
    }
    
    /**
     * Runs a test by running <code>collectData()</code> and  <code>compareKey()</code>
     * on the input provided from the specified file or folder of files.
     * @param prefix a string to prepend to the result file in order 
     * to distinguish one library from another.
     * @param folder the .txt file or folder of .txt files containing test data.
     * @param library the LDL being tested.
     */



    public void runTest(String prefix, File folder, LDL library){
	
	
	if (folder.isFile()){
	    if (folder.getName().matches(".*\\.txt")){
		try{
		    collectData(library, folder);    
		}
		
		catch(IOException e){
		    System.out.println("Unable to open " + folder.getName());
		    e.printStackTrace();
		}    
	    }
	}
	
	else if (folder.isDirectory()){
	    
	    
	    File[] listOfFiles = folder.listFiles();
	    for (File files: listOfFiles){
		if (files.getName().matches(".*\\.txt")){
		    try{
			collectData(library, files);
		    }
		    catch(IOException e){
			System.out.println("Unable to open " + files.getName());
			e.printStackTrace();
		    }    
		}
	    }
	}
	
	
	
	compareToKey();
	
	try{
	    
	    FileOutputStream outfile = new FileOutputStream("testresults/"+ prefix +"_results.txt");
	    
	    
	    PrintStream outstream = new PrintStream(outfile);
	    outstream.println(library.getDescription());
	    printConfusionMatrix(outstream);
	    printAccuracy(outstream);
	    
	   
	    printTimeByLang(outstream);
	    
	    outfile = new FileOutputStream("testresults/" + prefix + "_wrongposts.txt");
	    
	    
	    
	    try{
		outstream = new PrintStream(outfile,false,"UTF-8");
	    }
	    catch(UnsupportedEncodingException e){
		System.out.println("Encoding error...");
		e.printStackTrace();
	    }
	    outstream.println(library.getDescription());
	    printWrongPosts(outstream);
	}
	catch (FileNotFoundException e){
	    System.out.println("Could'nt write to file...");
	    e.printStackTrace();
	}
	
	
	
    }



	
}
	


    
    
    
    

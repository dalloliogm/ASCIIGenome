package commandHelp;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import exceptions.InvalidCommandLineException;

public class CommandHelp {

	private String name;
	private String args= "";
	private String briefDescription;
	private String additionalDescription= "";
	protected Section inSection= Section.GENERAL;
	private int LINE_LEN= 100;
	
	/* C o n s t r u c t o r */
	
	public CommandHelp(){}
	
	/* M e t h o d s */

	public String printBriefHelp(){
		
		String INDENT= "      ";
		
		String helpStr= this.name + " " + this.args + "\n";
		for(String line : this.wrapLines(this.briefDescription, LINE_LEN - INDENT.length())){
			helpStr += (INDENT + line + "\n");
		}
		return helpStr;
		
	}
	
	public String printCommandHelp(){
	
		String INDENT= "      ";
		
		String helpStr= this.name + " " + this.args + "\n";
		String fullDescr= this.briefDescription + " " + this.additionalDescription;
		for(String line : this.wrapLines(fullDescr, 80 - INDENT.length())){
			helpStr += (INDENT + line + "\n");
		}
		return helpStr;
		
	}
	
	/** Wrap lines once maxLen is exceeded.
	 * Use ~ to separate words that should stay on the same line and to add spaces.*/
	private List<String> wrapLines(String text, int maxLen){
		
		// Words separated by \n only are split into different words since we put a space after \n.
		text= text.replaceAll("\n", "\n "); 
		
		Iterable<String> words = Splitter.on(" ").trimResults(CharMatcher.is(' ')).omitEmptyStrings().split(text);
		
		String line= "";
		List<String> lines= new ArrayList<String>();
		for(String w : words){
			line += (w + " ");
			if(line.trim().length() >= maxLen || w.endsWith("\n")){
				lines.add(line.trim().replaceAll("~", " "));
				line= "";
			} 
		}
		if(!line.isEmpty()){
			lines.add(line.trim().replaceAll("~", " "));
		}
		return lines;
	}
	
	/* S e t t e r   and   G e t t e r s */
	protected void setName(String name) throws InvalidCommandLineException {
		if(!CommandList.cmds().contains(name)){
			throw new InvalidCommandLineException();
		}
		this.name = name;
	}
	protected String getName(){
		return this.name;
	}
	
	protected String getArgs() {
		return this.args;
	}	
	protected void setArgs(String args) {
		this.args = args;
	}
	
	protected void setAdditionalDescription(String description) {
		this.additionalDescription = description;
	}
	protected String getAdditionalDescription() {
		return this.additionalDescription;
	}
	
	protected void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}
	protected String getBriefDescription() {
		return this.briefDescription;
	}


}

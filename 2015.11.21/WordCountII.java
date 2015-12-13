package javaCourse;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Hashtable;

public class WordCountII 
{
	/**
	 * read the text content
	 * @param filePath
	 * @return String
	 */
	public static String readTxtFile(String filePath)
	{
		String encoding = "GBK";
		String lineText = null, text = null;
		try
		{
			File file = new File(filePath);
			//judge if the text file is existed or not
			if(file.isFile() && file.exists())
			{
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while((lineText = bufferedReader.readLine()) != null)
				{
					//test
					//System.out.println(lineText);
					text += lineText;
				}
				read.close();
			}
			else
			{
				System.out.println("Could not find the specified file!");
			}
		}
		catch(Exception e)
		{
			System.out.println("Read the file contents error!");
			e.printStackTrace();
		}
		return text;
	}
	
	/**
	 * get the file1's List
	 * @param t1
	 * @return List
	 */
	public List<String> getFile1List(String t1)
	{
		String[] tmp1 = t1.split(" ");
		List<String> l1 = new ArrayList<String>();
		
		l1.clear();
		//to solve the start word problem such as "nullI"
		tmp1[0] = tmp1[0].replace("null", "");
		for(int i = 0; i <= tmp1.length - 1; i++)
		{
			if(tmp1[i] != " " && tmp1[i] != "" && tmp1[i] != null && !l1.contains(tmp1[i]))
			{
				l1.add(tmp1[i]);
			}
		}
		return l1;
	}
	
	/**
	 * get the file2's List
	 * @param t2
	 * @return List
	 */
	public List<String> getFile2List(String t2)
	{
		String[] tmp2 = t2.split(" ");
		List<String> l2 = new ArrayList<String>();
		
		l2.clear();
		//to solve the start word problem such as "nullI"
		tmp2[0] = tmp2[0].replace("null", "");
		for(int i = 0; i <= tmp2.length - 1; i++)
		{
			if(tmp2[i] != " " && tmp2[i] != "" && tmp2[i] != null &&!l2.contains(tmp2[i]))
			{
				l2.add(tmp2[i]);
			}
		}
		return l2;
	}
	
	/**
	 * merge the two files
	 * @param t1
	 * @param t2
	 * @return List
	 */
	public List<String> mergeTwoFilesList(String t1, String t2)
	{
		String[] tmp1 = t1.split(" "), tmp2 = t2.split(" ");
		List<String> l12 = new ArrayList<String>();
		
		l12.clear();
		//to solve the start word problem such as "nullI"
		tmp1[0] = tmp1[0].replace("null", "");
		tmp2[0] = tmp2[0].replace("null", "");
		for(int i = 0; i <= tmp1.length - 1; i++)
		{
			if(tmp1[i] != " " && tmp1[i] != "" && tmp1[i] != null && !l12.contains(tmp1[i]))
			{
				l12.add(tmp1[i]);
			}
		}
		for(int i = 0; i <= tmp2.length - 1; i++)
		{
			if(tmp2[i] != " " && tmp2[i] != "" && tmp2[i] != null && !l12.contains(tmp2[i]))
			{
				l12.add(tmp2[i]);
			}
		}
		return l12;
	}
	
	/**
	 * get the file after merging
	 * @param t1
	 * @param t2
	 * @return List
	 */
	public List<String> getMergedFilesList(String t1, String t2)
	{
		return mergeTwoFilesList(t1, t2);
	}
	
	/**
	 * get Union List from file1 and file2
	 * @param t1
	 * @param t2
	 * @return List
	 */
	public List<String> getUnionList(String t1, String t2)
	{
		List<String> l1 = getFile1List(t1);
		List<String> l2 = getFile2List(t2);
		List<String> lu = new ArrayList<String>();
		
		lu.clear();
		for(int i = 0; i <= l2.size() - 1; i++)
		{
			if(l1.contains(l2.get(i)))
			{
				lu.add(l2.get(i));
			}
		}
		return lu;
	}
	
	/**
	 * get the file1's count
	 * @param t1
	 * @return int
	 */
	public int getFile1Count(String t1)
	{
		List<String> l1 = getFile1List(t1);
		return l1.size();
	}
	
	/**
	 * get the File2's count
	 * @param t2
	 * @return int
	 */
	public int getFile2Count(String t2)
	{
		List<String> l2 = getFile2List(t2);
		return l2.size();
	}
	
	public static void main(String[] args)
	{
		WordCountII w = new WordCountII();
		String filePath1 = "C:\\Users\\Sheldon Anderson\\Desktop\\poem1.txt";
		String filePath2 = "C:\\Users\\Sheldon Anderson\\Desktop\\poem2.txt";
		String text1 = readTxtFile(filePath1);
		String text2 = readTxtFile(filePath2);
		//we replace "'" with the specific string "XieNiantao" to avoid the solution to remove the punctuation marks
		text1 = text1.replace("'", "XieNiantao");
		text2 = text2.replace("'", "XieNiantao");
		//remove the punctuation marks in the file1 and file2 
		text1 = text1.replaceAll("[\\pP\\p{Punct}]", " ").replaceAll(" +", " ");
		text2 = text2.replaceAll("[\\pP\\p{Punct}]", " ").replaceAll(" +", " ");
		//we replace "'" back!
		text1 = text1.replace("XieNiantao","'");
		text2 = text2.replace("XieNiantao","'");
		
		//the file merged from file1 and file2
		List<String> l12 = w.getMergedFilesList(text1, text2);
		System.out.println("The file merged from file1 and file2 is: ");
		for(int i = 0; i <= l12.size() - 1; i++)
		{
			if(l12.get(i) != null)
			{
				System.out.print(l12.get(i) + " ");
			}
		}
		System.out.println();
		//the union set of file1 and file2
		List<String> lu = w.getUnionList(text1, text2);
		System.out.println("The union set of file1 and file2 is: ");
		for(int i = 0; i <= lu.size() - 1; i++)
		{
			if(lu.get(i) != null)
			{
				System.out.print(lu.get(i) + " ");
			}	
		}
		System.out.println();
		//the count of file1
		int cnt1 = w.getFile1Count(text1);
		System.out.println("The count of file1 is: " + cnt1);
		//the count of file2
		int cnt2 = w.getFile2Count(text2);
		System.out.println("The count of file2 is: " + cnt2);
	}
}

package loj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class WordCount 
{
	//define two file paths
	public static String path1 = "E:\\A.txt";
	public static String path2 = "E:\\B.txt";

	public String readTextFile(String path)
	{
		//the string that needs to be output in the end
		StringBuffer txt = new StringBuffer();
		try 
		{
            String encoding="GBK";
            File file=new File(path);
            //judge whether the file exists
            if(file.isFile() && file.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//consider about the encoding format
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null)
                {
                	txt.append(lineTxt+" ");
                }
                read.close();
            }
            else
            {
            	System.out.println("找不到指定的文件");
            }
		} 
		catch (Exception e) 
		{
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return txt.toString();
	}

	public String[] makeArray(String a,String b)
	{
		//marge the words in the two files into one large word string
		List<String> lista = getUnionSet(a.split(" "));	//A file string that has been deleted the repeated words
        List<String> listb = getUnionSet(b.split(" "));	//B file string that has been deleted the repeated words
        StringBuffer comb = new StringBuffer(); 
        StringBuffer la = new StringBuffer();
        StringBuffer lb = new StringBuffer();
        for(int i=0;i<lista.size();i++)
        {
        	la.append(lista.get(i)+" ");	//turn list into string
        }
        for(int i=0;i<listb.size();i++)
        {
        	lb.append(listb.get(i)+" ");
        }
        //obtain a string after concatenation
        comb.append(la.toString()+" "+lb.toString());
        //convert the string into an array of strings
        String[] cmb = comb.toString().split(" ");
        return cmb;
	}

	public List<String> getUnionSet(String[] str)
	{
		List<String> list = new LinkedList<String>();
		//traverse the string array, put the nonrepetitive elements into the list
        for(int i = 0; i < str.length; i++) 
        {
            if(!list.contains(str[i])) 
            {
                list.add(str[i]);
            }
        }
        return list;
	}

	public List<String> getCrossSet(String[] cmb)
	{
        List<String> list1 = new LinkedList<String>();
        List<String> list2 = new LinkedList<String>();
        for(int i = 0; i < cmb.length; i++) 
        {
            if(!list1.contains(cmb[i])) 
            {
                list1.add(cmb[i]);
            }
            else
            {
            	list2.add(cmb[i]);
            }
        }
        return list2;
	}

	public int getCount(String str)
	{
		String[] s = str.split(" ");
		return s.length;
	}
	
	public void getWordPercent(int num,String[] str,String s)
	{
		List<String> list = getCrossSet(str); //get the intersection list
		String[] ss = s.split(" "); //convert the string in the file into an array of strings
		List<String> list1 = new LinkedList<String>();
		for(int i=0;i<ss.length;i++)
		{
			list1.add(ss[i]);
		}
		list1.removeAll(list);	//remove the intersection part
		float per = (float)list1.size()/num;
		System.out.println(per);
	}

	public static void main(String[] args) 
	{
		WordCount wc = new WordCount();
		//obtain the word strings form the two files
		String a = wc.readTextFile(path1);
		String b = wc.readTextFile(path2);
		//obtain the array strings after concatenation
		String[] word = wc.makeArray(a,b);
		//output the union set
		System.out.println("A文件和B文件单词并集是：");
		List<String> ls = wc.getUnionSet(word);
		for(String lsu:ls)
		{
			System.out.print(lsu+" ");
		}
		System.out.println();
		//output the intersection
		System.out.println("A文件和B文件单词交集是：");
		List<String> li = wc.getCrossSet(word);
		for(String lsu:li)
		{
			System.out.print(lsu+" ");
		}
		System.out.println();
		//output the total word count in the file A 
		System.out.println("A文件单词总数是：");
		int num_a = wc.getCount(a);
		System.out.println(num_a);
		//output the total word count in the file B
		System.out.println("B文件单词总数是：");
		int num_b = wc.getCount(b);
		System.out.println(num_b);
		//output the proportion of words
		System.out.println("wd属于A且wd不属于B的单词占A文件的百分比是: ");
		wc.getWordPercent(num_a,word,a);
		System.out.println("wd属于B且wd不属于A的单词占A文件的百分比是: ");
		wc.getWordPercent(num_b,word,b);
	}
}

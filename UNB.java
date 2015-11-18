package set;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class UNB {
	public static void readFile(String filePath,Set list) {
		try {
			//读文件
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),"Unicode");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                //文件内容写入set
                while((lineTxt = bufferedReader.readLine()) != null){
                    list.add(lineTxt);
                }
                read.close();
                }else{
                	System.out.println("cann't find the file!");
                }
            } catch (Exception e) {
            	System.out.println("wrong while reading!");
            	e.printStackTrace();
            }
	}
	public static void union(String fileP1,String fileP2) {
		Set<String> list1 = new HashSet<String>();
		Set<String> list2 = new HashSet<String>();
		
		readFile(fileP1,list1);
		readFile(fileP2,list2);
		//交集
		list1.addAll(list2);
		System.out.println("union:" + list1);
	}
	public static void intersection(String fileP1,String fileP2) {
		Set<String> list1 = new HashSet<String>();
		Set<String> list2 = new HashSet<String>();
		
		readFile(fileP1,list1);
		readFile(fileP2,list2);
		//并集
		list1.retainAll(list2);
		System.out.println("intersection:" + list1);
	}
	public static void count(String fileP1,String fileP2) {
		Set<String> list1 = new HashSet<String>();
		Set<String> list2 = new HashSet<String>();
		Set<String> result = new HashSet<String>();
		
		readFile(fileP1,list1);
		readFile(fileP2,list2);
		
		int countAllP1 = 0;
		int countAllP2 = 0;
		double countPartP1 = 0.0;
		double countPartP2 = 0.0;
		
		countAllP1 = list1.size();
		countAllP2 = list2.size();
		//list1-list2
		result.clear();
		result.addAll(list1);
		result.removeAll(list2);
		countPartP1 = (double)result.size() / countAllP1;//计算比例
		System.out.println("list1-list2:" + countPartP1);
		//list2-list1
		result.clear();
		result.addAll(list2);
		result.removeAll(list1);
		countPartP2 = (double)result.size() / countAllP2;//计算比例
		System.out.println("list2-list1：" + countPartP2);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileP1 = "list1.txt";
		String fileP2 = "list2.txt";
		union(fileP1,fileP2);
		intersection(fileP1,fileP2);
		count(fileP1,fileP2);
	}

}

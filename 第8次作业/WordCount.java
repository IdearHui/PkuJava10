package com.task.wordcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class WordCount {
	//定义两个文件的路径
	public static String path1 = "E:\\A.txt";
	public static String path2 = "E:\\B.txt";
	/**
	 * TODO 读取本地txt文件
	 * @param path
	 * @return
	 */
	public String readTextFile(String path){
		StringBuffer txt = new StringBuffer();//最后要输出的字符串
		try {
            String encoding="GBK";
            File file=new File(path);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	txt.append(lineTxt+" ");
//                	System.out.println(lineTxt);
                }
                read.close();
            }else{
            	System.out.println("找不到指定的文件");
            }
		} catch (Exception e) {
        System.out.println("读取文件内容出错");
        e.printStackTrace();
		}
		return txt.toString();
	}
	
	/**
	 * TODO 将两个字符串拼接成大字符串数组
	 * @param a,b
	 * @return
	 */
	public String[] makeArray(String a,String b){
		//将两个文件中的单词合并成一个大的字符串
		List<String> lista = getUnionSet(a.split(" "));//A文件去重后的字符串list
        List<String> listb = getUnionSet(b.split(" "));//B文件去重后的字符串list
        StringBuffer comb = new StringBuffer(); 
        StringBuffer la = new StringBuffer();
        StringBuffer lb = new StringBuffer();
        for(int i=0;i<lista.size();i++)
        	la.append(lista.get(i)+" ");//把list转换成字符串
        for(int i=0;i<listb.size();i++)
        	lb.append(listb.get(i)+" ");
        //获得了拼接后的字符串
        comb.append(la.toString()+" "+lb.toString());
        
        String[] cmb = comb.toString().split(" ");//将字符串转换成字符串数组
        
        return cmb;
	}
	/**
	 * TODO 获取两个文件中单词的并集
	 * @param str
	 */
	public List<String> getUnionSet(String[] str){
		List<String> list = new LinkedList<String>();
        for(int i = 0; i < str.length; i++) {//遍历该字符串数组将不重复的元素放入list对象中
            if(!list.contains(str[i])) {
                list.add(str[i]);
            }
        }
        return list;
	}
	
	/**
	 * TODO 获取两个文件中单词的交集
	 * @param cmb
	 */
	public List<String> getCrossSet(String[] cmb){
        List<String> list1 = new LinkedList<String>();
        List<String> list2 = new LinkedList<String>();
        for(int i = 0; i < cmb.length; i++) {//遍历该字符串数组将不重复的元素放入list对象中
            if(!list1.contains(cmb[i])) {
                list1.add(cmb[i]);
            }else
            	list2.add(cmb[i]);
        }
        return list2;
	}
	
	/**
	 * TODO 获取文件中单词数
	 * @param str
	 */
	public int getCount(String str){
		String[] s = str.split(" ");
		return s.length;
	}
	
	public void getWordPercent(int num,String[] str,String s){
		List<String> list = getCrossSet(str);//获得交集的list
		String[] ss = s.split(" ");//将某个文件里的字符串转换成字符串数组
		List<String> list1 = new LinkedList<String>();
		for(int i=0;i<ss.length;i++)
			list1.add(ss[i]);
		list1.removeAll(list);//去掉交集部分
		float per = (float)list1.size()/num;
		System.out.println(per);
	}
	/**
	 * TODO 主函数  用于测试
	 * @param args
	 */
	public static void main(String[] args) {
		WordCount wc = new WordCount();
		//获取两个文件中的单词字符串
		String a = wc.readTextFile(path1);
		String b = wc.readTextFile(path2);
		//获得拼接后的字符串数组
		String[] word = wc.makeArray(a,b);
		//输出并集
		System.out.println("A文件和B文件单词并集是：");
		List<String> ls = wc.getUnionSet(word);
		for(String lsu:ls)
			System.out.print(lsu+" ");
		System.out.println();
		//输出交集
		System.out.println("A文件和B文件单词交集是：");
		List<String> li = wc.getCrossSet(word);
		for(String lsu:li)
			System.out.print(lsu+" ");
		System.out.println();
		//输出各文件中单词总数
		System.out.println("A文件单词总数是：");
		int num_a = wc.getCount(a);
		System.out.println(num_a);
		System.out.println("B文件单词总数是：");
		int num_b = wc.getCount(b);
		System.out.println(num_b);
		//输出单词比例
		System.out.println("wd∈A且wd∉B的单词占A文件的百分比是: ");
		wc.getWordPercent(num_a,word,a);
		System.out.println("wd∈B且wd∉A的单词占A文件的百分比是: ");
		wc.getWordPercent(num_b,word,b);
	}
}

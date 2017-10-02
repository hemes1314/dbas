package com.ishansong.action.travelway;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TrafficParticiple5 {
	public static List<String> traffic_lines = new ArrayList<String>();
	public static List<String> negative_lines = new ArrayList<String>();
	public static List<String> traffic_sec_lines = new ArrayList<String>();
	public static List<String> traffic_rest = new ArrayList<String>();
	
	public static HashMap<Integer, String> traffic_map = new HashMap<Integer, String>();
	public static HashMap<Integer, String> negative_map = new HashMap<Integer, String>();

	
	
	public static String StringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？[-+]]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim().replace("[0-9]", "");
	}

	public static boolean traffic_con_List(List<String> traffic_list, String targetValue) {
		String[] array = new String[traffic_list.size()];
		traffic_list.toArray(array);
		return Arrays.asList(array).contains(targetValue);
	}

	public static boolean negative_con_List(List<String> negative_list, String targetValue) {
		String[] array = new String[negative_list.size()];
		negative_list.toArray(array);
		return Arrays.asList(array).contains(targetValue);
	}

	public static Object getMaxKey(HashMap<Integer, String> map) {
		if (map == null)
			return null;
		Set<Integer> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return obj[obj.length - 1];
	}

	public static Object getMaxValue(HashMap<Integer, String> map) {
		if (map == null)
			return null;
		Set<Integer> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return map.get(obj[obj.length - 1]);
	}

	public static Object getMinKey(HashMap<Integer, String> map) {
		if (map == null)
			return null;
		Set<Integer> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return obj[0];
	}

	public static Object getMinValue(HashMap<Integer, String> map) {
		if (map == null)
			return null;
		Set<Integer> set = map.keySet();
		Object[] obj = set.toArray();
		Arrays.sort(obj);
		return map.get(obj[0]);
	}
	
	public   static   void  removeDuplicate(List list)   { 
		   for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )   { 
		    for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )   { 
		      if  (list.get(j).equals(list.get(i)))   { 
		        list.remove(j); 
		      } 
		    } 
		  } 
		  
	} 
	
	public String traffic_participle_minging(String arg) throws IOException {

		if (arg.isEmpty()) {
			System.out.println("需要输入备注的信息！！！");
		} else {
			System.out.println("输入的备注信息是: " + arg + "\n");
		}

		String traffic_out_str = null;// 分析出的交通工具
		String traffic_no_str = null;// 分析出的交通工具
		String remark_line = arg;// 备注信息
		int min_negative_map = 0;
		int max_negative_map = 0;
		int min_traffic_map = 0;		
		int max_traffic_map = 0;

		int index_traffic = 0;
		int index_negative = 0;


//		String traffic = "dict_data/traffic.txt"; // 交通的字典信息
//		String traffic_sec = "dict_data/traffic_sec.txt"; // 交通的字典信息
//		String negative = "dict_data/negative.txt"; // 否定词的字典信息

		String traffic = this.getClass().getClassLoader().getResource("/").getPath()+"/com/ishansong/files/traffic.txt"; // 交通的字典信息
		String negative = this.getClass().getClassLoader().getResource("/").getPath()+"/com/ishansong/files/negative.txt"; // 否定词的字典信息
		String traffic_sec = this.getClass().getClassLoader().getResource("/").getPath()+"/com/ishansong/files/traffic_sec.txt"; // 交通的字典信息

		File f_traffic = new File(traffic);
		File f_traffic_sec= new File(traffic_sec);
		File f_negative = new File(negative);

		if (!f_traffic.exists()) {
			System.out.println("traffic 文件不存在");
			return "traffic 文件不存在";
		} else {
			try {
				BufferedReader br_fr_traffic = new BufferedReader(new FileReader(traffic));
				for (String line = br_fr_traffic.readLine(); line != null; line = br_fr_traffic.readLine()) {
					String[] traffic_name = line.split(" ");
					traffic_lines.add(traffic_name[0]);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!f_traffic_sec.exists()) {
			System.out.println("traffic_sec 文件不存在");
			return "traffic_sec 文件不存在";
		} else {
			try {
				BufferedReader br_fr_traffic = new BufferedReader(new FileReader(traffic_sec));
				for (String line = br_fr_traffic.readLine(); line != null; line = br_fr_traffic.readLine()) {
					String[] traffic_name = line.split(" ");
					traffic_sec_lines.add(traffic_name[0]);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (!f_negative.exists()) {
			System.out.println("negative 文件不存在");
			return "negative 文件不存在";
		} else {
			try {
				BufferedReader br_fr_negative = new BufferedReader(new FileReader(negative));
				for (String line = br_fr_negative.readLine(); line != null; line = br_fr_negative.readLine()) {
					String[] negative_name = line.split(" ");
					negative_lines.add(negative_name[0]);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Segment seg = HanLP.newSegment().enableCustomDictionary(true); // 添加自定义字典
		for (int i = 0; i < traffic_lines.size(); i++) {
			CustomDictionary.add(traffic_lines.get(i).toString());
		}
		
		
		List<Term> termlist = seg.seg(StringFilter(remark_line));
		for (Term term : termlist) {
			// System.out.println(term);
			if (term.toString().endsWith("/nz")) {
				String[] term_word = term.toString().split("/");
				if (traffic_con_List(traffic_lines, term_word[0].toString())) {
					while ((index_traffic = remark_line.indexOf(term_word[0].toString(), index_traffic)) != -1) {
						traffic_map.put(index_traffic, term_word[0].toString());
						index_traffic = index_traffic + term_word[0].length();

					}

				}
			}

			if (term.toString().endsWith("/d")) {
				String[] term_word = term.toString().split("/");
				if (negative_con_List(negative_lines, term_word[0].toString())) {
					while ((index_negative = remark_line.indexOf(term_word[0].toString(), index_negative)) != -1) {
						negative_map.put(index_negative, term_word[0].toString());
						index_negative = index_negative + term_word[0].length();
					}
				}

			}
		}
		
		if (negative_map.size() > 0) {
			min_negative_map = (Integer) getMinKey(negative_map);
		} else {
			min_negative_map = 0;
		}


		if (negative_map.size() > 0) {
			max_negative_map = (Integer) getMaxKey(negative_map);
		} else {
			max_negative_map = 0;
		}

		if (traffic_map.size() > 0) {
			min_traffic_map = (Integer) getMinKey(traffic_map);
		} else {
			min_negative_map = 0;
		}

		if (traffic_map.size() > 0) {
			max_traffic_map = (Integer) getMaxKey(traffic_map);
		} else {
			max_traffic_map = 0;
		}
		
		// 判断逻辑
		//提取交通工作，判断前后是否有否定词汇 a) 如果没有否定词汇，则全部输出，b)有否定词汇，输出不含否定词汇的交通工具
		//
		if (traffic_map.size() > 0 && negative_map.size() <= 0) { // 只有交通工具，没有否定词
			for (int k : traffic_map.keySet()) {
				traffic_out_str = traffic_map.get(k) + "|" + traffic_out_str;
			}
			
			traffic_out_str = (String) traffic_out_str.subSequence(0, traffic_out_str.lastIndexOf("|"));
		} else if (traffic_map.size() <= 0) {
			System.out.println("3333333");
			traffic_out_str = null;
		} else if (traffic_map.size() > 0 && negative_map.size() > 0) {
			Integer cnt_traffic_map = traffic_map.size();
			Integer cnt_negative_map = negative_map.size();
			if (cnt_traffic_map >= 1 && cnt_traffic_map <= cnt_negative_map) {
				System.out.println("11111111111111");
				for (String value : traffic_map.values()) {
					for(int i=0; i<traffic_sec_lines.size();i++){
						if(traffic_sec_lines.get(i).equals(value)){
							traffic_sec_lines.remove(i);
						}
					}
				}  
				
				for(int i=0; i<traffic_sec_lines.size();i++){
					traffic_no_str=traffic_no_str+'|'+traffic_sec_lines.get(i);
				}
				traffic_out_str=traffic_no_str.substring(5, traffic_no_str.length());
				
			}else{
				System.out.println("22222222222");
				for (int n : negative_map.keySet()) {
//					System.out.println("否定词是"+negative_map.get(n).toString()+"位置是"+n);
					for (int k : traffic_map.keySet()){
//						System.out.println("交通工具是"+traffic_map.get(k).toString()+"位置是"+k);
						if(n<k){
							if(Math.abs(k-n)<=2){
								traffic_rest.add(traffic_map.get(k).toString());
							}
							
						}else{
							if(Math.abs(k-n)<=3){
								traffic_rest.add(traffic_map.get(k).toString());
							}
						}
						
					}
				}
				
				removeDuplicate(traffic_rest);
				System.out.println(traffic_rest);
				Iterator<Map.Entry<Integer, String>> it = traffic_map.entrySet().iterator();
				while(it.hasNext()){
		            Map.Entry<Integer, String> entry = it.next();
		            Integer key = entry.getKey();
		            String value= entry.getValue();
		            for(int i=0; i<traffic_rest.size();i++){
						if(value.equals(traffic_rest.get(i))){
							it.remove();
						}
					}
		        }
				for (int k : traffic_map.keySet()) {
					traffic_out_str = traffic_map.get(k) + "|" + traffic_out_str;
				}
				traffic_out_str = (String) traffic_out_str.subSequence(0, traffic_out_str.lastIndexOf("|"));
				
			}
			
		}

		traffic_map.clear();
		negative_map.clear();
		traffic_no_str="";
		return traffic_out_str;
	}
	
	public static void main(String[] args) throws IOException {
		String testline = "汽车火车都可以吧";
		TrafficParticiple5 collect = new TrafficParticiple5();
		//collect.traffic_participle_minging(testline);
		String rest = collect.traffic_participle_minging(testline);
		System.out.println(rest);
	}

}

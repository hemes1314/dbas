package com.ishansong.action.travelway;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.io.*;

public class traffic_participle_new2 {
	public static List<String> traffic_lines = new ArrayList<String>(); // 分词出来的交通工具
	public static List<String> negative_lines = new ArrayList<String>(); // 分词出来的否定词汇
	public static List<String> traffic_sec_lines = new ArrayList<String>(); // 标准交通工具
	public static List<String> traffic_rest = new ArrayList<String>(); // 存储中间交通工具

	public static HashMap<Integer, String> negative_traffic_map = new HashMap<Integer, String>();// 分词出来的交通工具
	public static HashMap<Integer, String> negative_traffic_sort = new HashMap<Integer, String>();// 分词出来的交通工具
	public static HashMap<Integer, String> traffic_map = new HashMap<Integer, String>();// 分词出来的交通工具
	public static HashMap<Integer, String> negative_map = new HashMap<Integer, String>();// 分词出来的否定词汇
	public static HashMap<String, String> traffic_similty_map = new HashMap<String, String>();// 转换标准交通工具

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

	public static void removeDuplicate(List list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
	}

	public static String traffic_participle_minging(String arg) throws IOException {

		if (arg.isEmpty()) {
			System.out.println("需要输入备注的信息！！！");
		} else {
			System.out.println("输入的备注信息是: " + arg + "\n");
		}

		String traffic_out_str = null;// 最终输出地交通工具
		String traffic_no_str = null;// 去除掉得交通工具
		String traffic_similty_str = null;// 转换相近的交通工具
		String remark_line = arg;// 备注信息
		int min_negative_map = 0;
		int max_negative_map = 0;
		int min_traffic_map = 0;
		int max_traffic_map = 0;

		int index_traffic = 0;
		int index_negative = 0;
		int dist_na_0 = 0;
		int dist_na_2 = 0;

		String traffic = "dict_data/traffic.txt"; // 可识别的交通的字典信息
		String traffic_sec = "dict_data/traffic_sec.txt"; // 标准交通的字典信息
		String negative = "dict_data/negative.txt"; // 否定词的字典信息
		String traffic_similty = "dict_data/traffic_similty.txt"; // 近义词转换交通工具的字典信息

		File f_traffic = new File(traffic);
		File f_traffic_sec = new File(traffic_sec);
		File f_negative = new File(negative);
		File f_traffic_similty = new File(traffic_similty);

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

		if (!f_traffic_similty.exists()) {
			System.out.println("traffic_similty 文件不存在");
			return "traffic_similty 文件不存在";
		} else {
			try {
				BufferedReader br_fr_traffic_similty = new BufferedReader(new FileReader(f_traffic_similty));
				for (String line = br_fr_traffic_similty.readLine(); line != null; line = br_fr_traffic_similty
						.readLine()) {
					String[] traffic_similty_name = line.split("\\|");
					traffic_similty_map.put(traffic_similty_name[0], traffic_similty_name[1]);
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
			// System.out.println("交通工具是" + traffic_lines.get(i).toString());
			CustomDictionary.add(traffic_lines.get(i).toString());
		}

		List<Term> termlist = seg.seg(StringFilter(remark_line));
		for (Term term : termlist) {
			// 装载识别出的交通工具字典
			if (term.toString().endsWith("/nz")) {
				String[] term_word = term.toString().split("/");
				if (traffic_con_List(traffic_lines, term_word[0].toString())) {
					while ((index_traffic = remark_line.indexOf(term_word[0].toString(), index_traffic)) != -1) {
						// 对识别出的交通工具，进行标准化
						for (String similty : traffic_similty_map.keySet()) {
							if (term_word[0].toString().equals(similty)) {
								traffic_similty_str = traffic_similty_map.get(similty);
								break;
							} else {
								traffic_similty_str = term_word[0].toString();
							}
						}

						traffic_map.put(index_traffic, traffic_similty_str);
						negative_traffic_map.put(index_traffic, traffic_similty_str + "|" + "ta");
						index_traffic = index_traffic + term_word[0].length();

					}

				}
			}
			// 装载识别出的否定词汇
			if (term.toString().endsWith("/d")) {
				String[] term_word = term.toString().split("/");
				if (negative_con_List(negative_lines, term_word[0].toString())) {
					while ((index_negative = remark_line.indexOf(term_word[0].toString(), index_negative)) != -1) {
						negative_map.put(index_negative, term_word[0].toString());
						negative_traffic_map.put(index_negative, term_word[0].toString() + "|" + "na");
						index_negative = index_negative + term_word[0].length();
					}
				}

			}
		}
		int key_sort = negative_traffic_map.size() - 1;
		for (Integer k : negative_traffic_map.keySet()) {

			negative_traffic_sort.put(key_sort, k + "|" + negative_traffic_map.get(k));
			key_sort--;
		}

		// 判断逻辑
		// 提取交通工作，判断前后是否有否定词汇 a) 如果没有否定词汇，则全部输出，b)有否定词汇，输出不含否定词汇的交通工具
		//
		if (traffic_map.size() > 0 && negative_map.size() <= 0) { // 只有交通工具，没有否定词
			for (int k : traffic_map.keySet()) {
				traffic_out_str = traffic_map.get(k) + "|" + traffic_out_str;
			}
			traffic_out_str = (String) traffic_out_str.subSequence(0, traffic_out_str.lastIndexOf("|"));
		} else if (traffic_map.size() <= 0) { // 沒有交通工具訴求的
			traffic_out_str = null;
		} else if (traffic_map.size() > 0 && negative_map.size() > 0) {
			for (Integer k : negative_traffic_sort.keySet()) {
				System.out.println("所有内容是--------" + negative_traffic_sort.get(k));
				if (negative_traffic_sort.get(k).split("\\|")[2].toString().equals("ta")) {
					if (k == 0) {// 交通工具在句尾
						if (negative_traffic_sort.get(k + 1).split("\\|")[2].equals("na")) {// 前面一个字符是否定词
							System.out.println("句尾");
							if (Math.abs(
									Integer.parseInt(negative_traffic_sort.get(k).split("\\|")[0]) - Integer.parseInt(
											negative_traffic_sort.get(k + 1).split("\\|")[0])) > negative_traffic_sort
													.get(k).split("\\|")[1].length()) {
								traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
							}
						} else {
							traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
						}

					} else if (k == negative_traffic_sort.size() - 1) {// 交通工具在句首
						if (negative_traffic_sort.get(k - 1).split("\\|")[2].equals("na")) {// 前面一个字符是否定词
							System.out.println("句首");
							if (Math.abs(
									Integer.parseInt(negative_traffic_sort.get(k).split("\\|")[0]) - Integer.parseInt(
											negative_traffic_sort.get(k - 1).split("\\|")[0])) > negative_traffic_sort
													.get(k).split("\\|")[1].length()) {
								traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
							}
						} else {
							System.out.println("句首");
							traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
						}

					} else { // 交通工具在句中
						System.out.println("第---------" + k);
						System.out.println("总大小---------" + negative_traffic_sort.size());
						if (negative_traffic_sort.get(k - 1).split("\\|")[2].equals("na")
								|| negative_traffic_sort.get(k + 1).split("\\|")[2].equals("na")) {
//							if (java.lang.Math.abs(
//									Integer.parseInt(negative_traffic_sort.get(k).split("\\|")[0]) - Integer.parseInt(
//											negative_traffic_sort.get(k - 1).split("\\|")[0])) > negative_traffic_sort
//													.get(k).split("\\|")[1].length()) {
//								System.out.println("11111111111");
//								traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
//							}
//
//							if (java.lang.Math.abs(
//									Integer.parseInt(negative_traffic_sort.get(k).split("\\|")[0]) - Integer.parseInt(
//											negative_traffic_sort.get(k + 1).split("\\|")[0])) > negative_traffic_sort
//													.get(k+1).split("\\|")[1].length()) {
//								System.out.println("222222222222");
//								traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
//							}

						} else {
							traffic_rest.add(negative_traffic_sort.get(k).split("\\|")[1].toString());
						}
					}
				}
			}
			removeDuplicate(traffic_rest);
			for (int i = 0; i < traffic_rest.size(); i++) {
				// System.out.println(traffic_rest.get(i));
				traffic_out_str = traffic_rest.get(i) + '|' + traffic_out_str;
			}
			if (traffic_out_str == null) {
				Iterator<String> traffic_sec_str = traffic_sec_lines.iterator();
				while (traffic_sec_str.hasNext()) {
					String traffic_str = traffic_sec_str.next();
					for (String value : traffic_map.values()) {
						long distince = CoreSynonymDictionary.distance(value, traffic_str);
						if (traffic_str.equals(value)) {
							traffic_sec_str.remove();
						}
					}
				}
				for (int i = 0; i < traffic_sec_lines.size(); i++) {
					traffic_out_str = traffic_sec_lines.get(i) + '|' + traffic_out_str;
				}
				if (traffic_out_str == null) {
					traffic_out_str = traffic_out_str;
				} else {
					traffic_out_str = (String) traffic_out_str.subSequence(0, traffic_out_str.lastIndexOf("|"));
				}
			} else {
				traffic_out_str = (String) traffic_out_str.subSequence(0, traffic_out_str.lastIndexOf("|"));
			}

		}

		traffic_map.clear();
		negative_map.clear();
		traffic_rest.clear();
		traffic_no_str = "";
		traffic_similty_str = "";
		return traffic_out_str;
	}

	public static void main(String[] args) throws IOException {
		String testline = "自行车别来，什么的卡卡卡汽车可以，摩托";
		traffic_participle collect = new traffic_participle();
		// collect.traffic_participle_minging(testline);
		String rest = collect.traffic_participle_minging(testline);
		System.out.println("------------正式結果﹣﹣﹣﹣﹣﹣﹣﹣﹣﹣");
		System.out.println(rest);
	}

}

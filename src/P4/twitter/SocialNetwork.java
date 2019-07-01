/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
	//（新增的public类），返回提到的用户名集合（用户名是否考虑大小写）
	public static Set<String> getMentionedUsers(Tweet tw) {
    	String word="",line,temp;
    	Set<String> resultSet = new HashSet<String>();
    	int i,k=0,tag,flag = 1;
    	char letter;
    	//定义一个包括{A..Z, a..z, 0..9, _, -}的字符数组
    	char[] letter_set = new char[100];
    	letter_set[k++]= '-';
    	letter_set[k++] = '_';
    	for(i=0;i<26;i++)
    	{
    		letter_set[k++] = (char) ('A' + i);
    		letter_set[k++] = (char) ('a' + i);
    	}
		for (i = 0; i < 10; i++) {
			letter_set[k++] = (char) ('0' + i);
		}
		line = tw.getText();
		String String_set = String.valueOf(letter_set);
		tag = line.indexOf('@');// 取出第一个@所在下标
		if (tag != -1) {
			do {
				if (tag != 0)// 判断@是否为首字符
				{
					letter = line.charAt(tag - 1);
					temp = String.valueOf(letter);
					if (String_set.contains(temp))// 判断是否为用户名有效字符
					{
						flag = 0;// 若letter是有效字符，无用户名
					}
				}
				if (flag == 1)// 若有用户名
				{
					for (i = tag + 1; i < line.length(); i++)// 获取计算名
					{
						letter = line.charAt(i);
						temp = String.valueOf(letter);
						if (!String_set.contains(temp))// 判断是否为用户名有效字符
						{
							break;// 遇到非法字符退出
						}
						word =word + letter;
					}
					if (!resultSet.contains(word))// 避免重复添加
					{
						resultSet.add(word);
						word = "";
					}
				}
				tag = line.indexOf('@', tag + 1);// 遍历下一个@
			} while (tag != -1);
		}
    	return resultSet;
    }
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	//键值对是作者+follow的对象
    	//相当于判断@Bert是否出现在推文中
    	Map<String, Set<String>> result_map = new HashMap<>();
    	Set<String> follow = new HashSet<>();
    	String author;
    	for(Tweet tw:tweets)
    	{
    		author = tw.getAuthor();
    		//相当于判断@Bert是否出现在推文中
    		follow = getMentionedUsers(tw);
    		//如果一个作者有多个推文
    		if(!follow.isEmpty())//如果不为空，添加至set集合
    		{
    			result_map.put(author,follow);
    		}
    	}
    	return result_map;
//        throw new RuntimeException("not implemented");
    }
//    In this problem, you will implement one additional kind of evidence in guessFollowsGraph().
//    另一个证据：如果A和B相互关注，B和C相互关注，则判定A和C相互关注
    
	public static Map<String, Set<String>> MyGuessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> result_map = new HashMap<>();//关注的地图
    	Map<String, Set<String>> result_map1 = new HashMap<>();//关注的地图
    	Map<String, Set<String>> mutual_map = new HashMap<>();//相互关注的地图
    	Map<String, Set<String>> smartFollow = new HashMap<>();//题目要求的地图
    	Set<String> temp_set= new HashSet<>();
    	Set<String> tempset= new HashSet<>();
    	Set<String> follow = new HashSet<>();
    	List<String> delList = new ArrayList<String>();
    	String author;
    	for(Tweet tw:tweets)
    	{
    		author = tw.getAuthor();
    		//相当于判断@Bert是否出现在推文中
    		follow = getMentionedUsers(tw);
    		if(!follow.isEmpty())//如果不为空，添加至set集合
    		{
    			result_map.put(author,follow);
    			result_map1.put(author,follow);
    		}
    	}
    	//第一重循环列举每一个作者
    	for(String au:result_map.keySet())
    	{
    		//第二重循环遍历作者的关注人数集合
			for(String mention:result_map.get(au))
    		{
				temp_set.add(mention);
				if((result_map.get(mention)) == null||!(result_map.get(mention)).contains(au))
    				//被关注者的关注集合无作者,则删除
    			{
					delList.add(mention);
				
    			}
    		}
			temp_set.removeAll(delList);
			mutual_map.put(au,temp_set);
			delList = new ArrayList<String>();
			temp_set = new HashSet<String>();
			
    	}
    	//得到相互关注的集合mutual_map
    	//第一重循环遍历map的键值
    	for (String ps: mutual_map.keySet()) 
    	{
    		for(String temp:result_map1.get(ps))//避免改变result_map1
    		{
    			tempset.add(temp);
    		}
    		if(!(mutual_map.get(ps)).isEmpty())//如果不为空
    		{
	    		for(String end:mutual_map.get(ps))//第二次遍历集合中相互关注者
	    		{
	    			for(String per:mutual_map.get(end))
	    			//第三次遍历相互关注者的关注集合，若有作者未关注的人则添加关注
	    			{
	    				if(!(result_map1.get(ps)).contains(per))//若有作者未关注的人则添加关注
	    				{
	    					delList.add(per);
	    				}
	    			}
	    		}
    		}
    		tempset.addAll(delList);
    		if(tempset.contains(ps))//用户不关注自身
    		{
    			tempset.remove(ps);
    		}
    		smartFollow.put(ps,tempset);
    		tempset = new HashSet<String>();
			delList = new ArrayList<String>();
    	}
		return smartFollow;
	}
    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	List<String> name= new ArrayList<String>();
    	List<String> influencer_set = new ArrayList<String>();
    	Map<String, Integer> mentioned = new HashMap<String, Integer>();
    	int count;
    	//定义一个字符串列表，name记录名字列表
    	//对Map进行遍历，从记录Set中的每一个被追随者，并计数
    	//第一次循环得到所有被追随者名字集合name，可以重复
    	for(String o:followsGraph.keySet())
    	{
    		for(String temp:followsGraph.get(o))
    		{
    			if(temp != "")
    			{
    				name.add(temp);
    			}
    		}
    	}
    	//第二次循环，遍历name，对每个被追随者名字计数，并放入map中
    	for(String s:name)
    	{
    		if(!mentioned.containsKey(s))//判断是否map中存在
    		{
    			count = 1;
    			mentioned.put(s,count);
    		}
    		else {//如果已经存在，加1
    			count = mentioned.get(s) + 1;
    			mentioned.put(s,count);
			}
    	}
    	//对map排序，遍历map,使得map降序排列
    	Map<String, Integer> result = mentioned.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    	//result降序排列
    	for(String fo:result.keySet())
    	{
    		influencer_set.add(fo);
    	}
    	return influencer_set;
    }
}

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
	//��������public�ࣩ�������ᵽ���û������ϣ��û����Ƿ��Ǵ�Сд��
	public static Set<String> getMentionedUsers(Tweet tw) {
    	String word="",line,temp;
    	Set<String> resultSet = new HashSet<String>();
    	int i,k=0,tag,flag = 1;
    	char letter;
    	//����һ������{A..Z, a..z, 0..9, _, -}���ַ�����
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
		tag = line.indexOf('@');// ȡ����һ��@�����±�
		if (tag != -1) {
			do {
				if (tag != 0)// �ж�@�Ƿ�Ϊ���ַ�
				{
					letter = line.charAt(tag - 1);
					temp = String.valueOf(letter);
					if (String_set.contains(temp))// �ж��Ƿ�Ϊ�û�����Ч�ַ�
					{
						flag = 0;// ��letter����Ч�ַ������û���
					}
				}
				if (flag == 1)// �����û���
				{
					for (i = tag + 1; i < line.length(); i++)// ��ȡ������
					{
						letter = line.charAt(i);
						temp = String.valueOf(letter);
						if (!String_set.contains(temp))// �ж��Ƿ�Ϊ�û�����Ч�ַ�
						{
							break;// �����Ƿ��ַ��˳�
						}
						word =word + letter;
					}
					if (!resultSet.contains(word))// �����ظ����
					{
						resultSet.add(word);
						word = "";
					}
				}
				tag = line.indexOf('@', tag + 1);// ������һ��@
			} while (tag != -1);
		}
    	return resultSet;
    }
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	//��ֵ��������+follow�Ķ���
    	//�൱���ж�@Bert�Ƿ������������
    	Map<String, Set<String>> result_map = new HashMap<>();
    	Set<String> follow = new HashSet<>();
    	String author;
    	for(Tweet tw:tweets)
    	{
    		author = tw.getAuthor();
    		//�൱���ж�@Bert�Ƿ������������
    		follow = getMentionedUsers(tw);
    		//���һ�������ж������
    		if(!follow.isEmpty())//�����Ϊ�գ������set����
    		{
    			result_map.put(author,follow);
    		}
    	}
    	return result_map;
//        throw new RuntimeException("not implemented");
    }
//    In this problem, you will implement one additional kind of evidence in guessFollowsGraph().
//    ��һ��֤�ݣ����A��B�໥��ע��B��C�໥��ע�����ж�A��C�໥��ע
    
	public static Map<String, Set<String>> MyGuessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> result_map = new HashMap<>();//��ע�ĵ�ͼ
    	Map<String, Set<String>> result_map1 = new HashMap<>();//��ע�ĵ�ͼ
    	Map<String, Set<String>> mutual_map = new HashMap<>();//�໥��ע�ĵ�ͼ
    	Map<String, Set<String>> smartFollow = new HashMap<>();//��ĿҪ��ĵ�ͼ
    	Set<String> temp_set= new HashSet<>();
    	Set<String> tempset= new HashSet<>();
    	Set<String> follow = new HashSet<>();
    	List<String> delList = new ArrayList<String>();
    	String author;
    	for(Tweet tw:tweets)
    	{
    		author = tw.getAuthor();
    		//�൱���ж�@Bert�Ƿ������������
    		follow = getMentionedUsers(tw);
    		if(!follow.isEmpty())//�����Ϊ�գ������set����
    		{
    			result_map.put(author,follow);
    			result_map1.put(author,follow);
    		}
    	}
    	//��һ��ѭ���о�ÿһ������
    	for(String au:result_map.keySet())
    	{
    		//�ڶ���ѭ���������ߵĹ�ע��������
			for(String mention:result_map.get(au))
    		{
				temp_set.add(mention);
				if((result_map.get(mention)) == null||!(result_map.get(mention)).contains(au))
    				//����ע�ߵĹ�ע����������,��ɾ��
    			{
					delList.add(mention);
				
    			}
    		}
			temp_set.removeAll(delList);
			mutual_map.put(au,temp_set);
			delList = new ArrayList<String>();
			temp_set = new HashSet<String>();
			
    	}
    	//�õ��໥��ע�ļ���mutual_map
    	//��һ��ѭ������map�ļ�ֵ
    	for (String ps: mutual_map.keySet()) 
    	{
    		for(String temp:result_map1.get(ps))//����ı�result_map1
    		{
    			tempset.add(temp);
    		}
    		if(!(mutual_map.get(ps)).isEmpty())//�����Ϊ��
    		{
	    		for(String end:mutual_map.get(ps))//�ڶ��α����������໥��ע��
	    		{
	    			for(String per:mutual_map.get(end))
	    			//�����α����໥��ע�ߵĹ�ע���ϣ���������δ��ע��������ӹ�ע
	    			{
	    				if(!(result_map1.get(ps)).contains(per))//��������δ��ע��������ӹ�ע
	    				{
	    					delList.add(per);
	    				}
	    			}
	    		}
    		}
    		tempset.addAll(delList);
    		if(tempset.contains(ps))//�û�����ע����
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
    	//����һ���ַ����б�name��¼�����б�
    	//��Map���б������Ӽ�¼Set�е�ÿһ����׷���ߣ�������
    	//��һ��ѭ���õ����б�׷�������ּ���name�������ظ�
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
    	//�ڶ���ѭ��������name����ÿ����׷�������ּ�����������map��
    	for(String s:name)
    	{
    		if(!mentioned.containsKey(s))//�ж��Ƿ�map�д���
    		{
    			count = 1;
    			mentioned.put(s,count);
    		}
    		else {//����Ѿ����ڣ���1
    			count = mentioned.get(s) + 1;
    			mentioned.put(s,count);
			}
    	}
    	//��map���򣬱���map,ʹ��map��������
    	Map<String, Integer> result = mentioned.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    	//result��������
    	for(String fo:result.keySet())
    	{
    		influencer_set.add(fo);
    	}
    	return influencer_set;
    }
}

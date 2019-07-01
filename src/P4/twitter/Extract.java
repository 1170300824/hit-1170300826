/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	Instant begin,end;
    	int i;
    	Tweet tw;
    	tw = tweets.get(0);
    	begin = tw.getTimestamp();
    	end = tw.getTimestamp();
    	for(i=1;i<tweets.size();i++)
    	{
    		tw =tweets.get(i);
    		if(tw.getTimestamp().isBefore(begin))//得到最前时间
    		{
    			begin = tw.getTimestamp();
    		}
    		if(tw.getTimestamp().isAfter(end))//得到最后时间
    		{
    			end = tw.getTimestamp();
    		}
    	}
    	Timespan minTimespan = new Timespan(begin, end);
    	return minTimespan;
//        throw new RuntimeException("not implemented");
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
    	String word = "",line,temp;
    	Set<String> resultSet = new HashSet<String>();
    	int i,k=0,tag,flag = 1;
    	char letter;
    	char[] word1 = new char[100];//存储用户名,不超过100字母
    	//定义一个包括{A..Z, a..z, 0..9, _, -}的字符数组
    	char[] letter_set = new char[100];
    	letter_set[k++]= '-';
    	letter_set[k++] = '_';
    	for(i=0;i<26;i++)
    	{
    		letter_set[k++] = (char) ('A' + i);
    		letter_set[k++] = (char) ('a' + i);
    	}
    	for(i=0;i<10;i++)
    	{
    		letter_set[k++] = (char) ('0' + i);
    	}
    	for(Tweet tw:tweets)
    	{
    		line = tw.getText();
    		String String_set = String.valueOf(letter_set);
			tag = line.indexOf("@");
			if(tag == -1)
			{
				break;//若没有，则跳出
			}
			do
    		{
    			if(tag != 0)//判断@是否为首字符
    			{
    				letter = line.charAt(tag-1);
    				temp = String.valueOf(letter);
    				if(String_set.contains(temp))//判断是否为用户名有效字符
    				{
    					flag = 0;//若letter是有效字符，无用户名
    				}
    			}
    			if(flag == 1)//若有用户名
    			{
    				for(i=tag+1;i<line.length();i++)//获取计算名
    				{
    					letter = line.charAt(i);
    					temp = String.valueOf(letter);
    					if(!String_set.contains(temp))//判断是否为用户名有效字符
    					{
    						break;//遇到非法字符退出
    					}
    					word = word + letter;
    				}
    				word = String.valueOf(word1).trim();//字符数组转字符串
					if(!resultSet.contains(word))//如果不包括则添加
					{
						resultSet.add(word);
						word = "";
					}
    			}
				tag = line.indexOf('@',tag+1);
    		}while(tag != -1);//依次遍历推文中的'@'
    	}
    	return resultSet;
//        throw new RuntimeException("not implemented");
    }

}

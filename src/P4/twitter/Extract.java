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
    		if(tw.getTimestamp().isBefore(begin))//�õ���ǰʱ��
    		{
    			begin = tw.getTimestamp();
    		}
    		if(tw.getTimestamp().isAfter(end))//�õ����ʱ��
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
    	char[] word1 = new char[100];//�洢�û���,������100��ĸ
    	//����һ������{A..Z, a..z, 0..9, _, -}���ַ�����
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
				break;//��û�У�������
			}
			do
    		{
    			if(tag != 0)//�ж�@�Ƿ�Ϊ���ַ�
    			{
    				letter = line.charAt(tag-1);
    				temp = String.valueOf(letter);
    				if(String_set.contains(temp))//�ж��Ƿ�Ϊ�û�����Ч�ַ�
    				{
    					flag = 0;//��letter����Ч�ַ������û���
    				}
    			}
    			if(flag == 1)//�����û���
    			{
    				for(i=tag+1;i<line.length();i++)//��ȡ������
    				{
    					letter = line.charAt(i);
    					temp = String.valueOf(letter);
    					if(!String_set.contains(temp))//�ж��Ƿ�Ϊ�û�����Ч�ַ�
    					{
    						break;//�����Ƿ��ַ��˳�
    					}
    					word = word + letter;
    				}
    				word = String.valueOf(word1).trim();//�ַ�����ת�ַ���
					if(!resultSet.contains(word))//��������������
					{
						resultSet.add(word);
						word = "";
					}
    			}
				tag = line.indexOf('@',tag+1);
    		}while(tag != -1);//���α��������е�'@'
    	}
    	return resultSet;
//        throw new RuntimeException("not implemented");
    }

}

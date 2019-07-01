/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
    	List<Tweet> name_result = new ArrayList<Tweet>();
    	for(Tweet tw1:tweets)//遍历推文
    	{
    		if(tw1.getAuthor().equals(username))//如果该推文的ID是username，添加至列表
    		{
    			name_result.add(tw1);
    		}
    	}
    	return name_result;
//        throw new RuntimeException("not implemented");
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
    	List<Tweet> time_result = new ArrayList<Tweet>();
    	for(Tweet tw1:tweets)//遍历推文
    	{
    		if(timespan.getStart().isBefore(tw1.getTimestamp())&&timespan.getEnd().isAfter(tw1.getTimestamp()))
    		//如果该推文的时间在时间跨度内，则添加至数组
    		{
    			time_result.add(tw1);
    		}
    	}
    	return time_result;
//    	throw new RuntimeException("not implemented");
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static boolean Isexit(Tweet tw,String word) {
    	//判断该推文是否包括目标单词
    	String line;
    	line = tw.getText().toLowerCase();//全部转成小写
    	word = word.toLowerCase();
		String[] tweet_word = line.split(" ");//按空格分割
		List<String> target_list = Arrays.asList(tweet_word);
		if(target_list.contains(word))//末尾是否包含字符串结尾
		{
			return true;
		}
		return false;
	}
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
    	List<Tweet> word_result = new ArrayList<Tweet>();
    	for(Tweet tw1:tweets)//遍历推文
    	{
    		for(String word:words)//遍历单词列表单词
    		{
    			if(Isexit(tw1,word))
    	    		//如果该推文包括至少一个目标单词，则添加至数组
    	    		{
    	    			word_result.add(tw1);
    	    			break;
    	    		}
    		}
    	}
    	return word_result;
//    	throw new RuntimeException("not implemented");
    }

}

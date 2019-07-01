package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private List<Person> friendList;
	private static ArrayList<String> namelist = new ArrayList<String>();

//	 Person类的构造方法  
	public Person(String name) {
		if(namelist.contains(name))
		{
			System.out.println("输入重名，结果无效");
			System.exit(0);
		}
		this.name = name;
		namelist.add(name);
		friendList = new ArrayList<>();
	}
	//	添加相邻节点 
	public void addFriend(Person pb) {
		friendList.add(pb);
	}
//	获取当前Person的名字
	public String getName() {
		return name;
	}
	//获得当前person的相邻节点列表 
	public List<Person> getFriendList() {
		return this.friendList;
	}
}

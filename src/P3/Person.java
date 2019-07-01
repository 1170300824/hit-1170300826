package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String name;
	private List<Person> friendList;
	private static ArrayList<String> namelist = new ArrayList<String>();

//	 Person��Ĺ��췽��  
	public Person(String name) {
		if(namelist.contains(name))
		{
			System.out.println("���������������Ч");
			System.exit(0);
		}
		this.name = name;
		namelist.add(name);
		friendList = new ArrayList<>();
	}
	//	������ڽڵ� 
	public void addFriend(Person pb) {
		friendList.add(pb);
	}
//	��ȡ��ǰPerson������
	public String getName() {
		return name;
	}
	//��õ�ǰperson�����ڽڵ��б� 
	public List<Person> getFriendList() {
		return this.friendList;
	}
}

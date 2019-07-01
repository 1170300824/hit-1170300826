package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import P3.Person;

public class FriendshipGraph {
	// 计算两点之间的距离
	private List<Person> people= new ArrayList<Person>();
	public int getDistance(Person per1, Person per2) {
		if (per1 == per2) {
			return 0;
		}
		int dis = 1;// dis表示距离
		//广度优先搜索
		Queue<Person> queue = new LinkedList<Person>();
		// LinkedList实现队列接口
		HashMap<Person, Integer> map = new HashMap<Person, Integer>();
		queue.offer(per1);
		while (!queue.isEmpty()) { // 队列非空
			Person v1 = queue.poll(); // 移除并返回头部元素
			for (Person ps : v1.getFriendList()) {
				if (!map.containsKey(ps))//如果没有被访问过
				{
					map.put(ps, dis);
					queue.offer(ps); // 压入队列
					if (ps == per2)// 找到
					{
						return map.get(ps);
					}
				}
			}
			dis++;
		}
		return -1;
	}
	public void addEdge(Person per1, Person per2) {
		// TODO Auto-generated method stub
		per1.addFriend(per2);
	}

	public void addVertex(Person per) {
		// TODO Auto-generated method stub
			people.add(per);
	}
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
	}
}

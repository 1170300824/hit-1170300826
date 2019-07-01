package P3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import P3.FriendshipGraph;

public class FriendshipGraphTest {

	/**
	 * Tests that assertions are enabled.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	//test addVertex
	//测试顶点是否添加成功
	@Test
	public void addVertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addVertex(rachel);
	}
	//test addEdge
	//测试边是否添加成功
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		graph.addEdge(rachel,ross);
	}
	//test getDistance
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person d1 = new Person("d1");
		Person d2 = new Person("d2");
		Person d3 = new Person("d3");
		Person d4 = new Person("d4");
		Person d5 = new Person("d5");
		Person d6 = new Person("d6");
		graph.addVertex(d1);
		graph.addVertex(d2);
		graph.addVertex(d3);
		graph.addVertex(d4);
		graph.addVertex(d5);
		graph.addVertex(d6);
		graph.addEdge(d1, d2);
		graph.addEdge(d1, d5);
		graph.addEdge(d2, d4);
		graph.addEdge(d2, d3);
		graph.addEdge(d5, d6);
		assertEquals(1, graph.getDistance(d1, d5), 0.001);
		assertEquals(2, graph.getDistance(d1, d6), 0.001);
		assertEquals(0, graph.getDistance(d5, d5), 0.001);
		assertEquals(-1, graph.getDistance(d4, d6), 0.001);
	}
}

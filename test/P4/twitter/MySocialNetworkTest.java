package P4.twitter;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {
	private static final Instant d1 = Instant.parse("2019-03-10T10:00:00Z");
	private static final Instant d2 = Instant.parse("2019-03-11T11:00:00Z");
	private static final Instant d3 = Instant.parse("2019-03-12T10:10:00Z");
	private static final Instant d4 = Instant.parse("2019-03-13T11:00:00Z");
	private static final Instant d5 = Instant.parse("2019-03-14T10:00:00Z");
	private static final Instant d6 = Instant.parse("2019-03-15T11:00:00Z");
	private static final Instant d7 = Instant.parse("2019-03-16T10:00:00Z");
	private static final Instant d8 = Instant.parse("2019-03-17T11:00:00Z");
	private static final Instant d9 = Instant.parse("2019-03-17T13:00:00Z");
	private static final Instant d10 = Instant.parse("2019-03-17T11:45:00Z");
	private static final Instant d11 = Instant.parse("2019-03-18T10:00:33Z");
	private static final Instant d12 = Instant.parse("2019-03-18T11:00:00Z");

	private static final Tweet tweet1 = new Tweet(1, "c",
			"@Pascal @php Most the extreme sports have lost the mainstream nowadays. Bmx is at the olympics now though.",
			d1);
	private static final Tweet tweet2 = new Tweet(2, "cplus",
			"RT @verilog: CONGRATULATIONS TO THE COLTS!They won State Special Olympics Basketball in their division! Way to go athletes!",
			d2);
	private static final Tweet tweet3 = new Tweet(3, "python", "i am a good language.", d3);
	private static final Tweet tweet4 = new Tweet(4, "java",
			"If you have the inclination to do so, also see #kdlang Olympics performance of this beautiful song.", d4);
	private static final Tweet tweet5 = new Tweet(5, "javascript",
			"RT @R: 3 physios  from @R supporting Team GB #sogbphysio at Special Olympics in Abu Dhabi. So proud of",
			d5);
	private static final Tweet tweet6 = new Tweet(6, "R", "I am the best!you are wrong.", d6);
	private static final Tweet tweet7 = new Tweet(7, "html", "@verilog @Pascal I am the best!you are wrong.", d7);
	private static final Tweet tweet8 = new Tweet(8, "php",
			"@c @python Full house for Special Olympics volleyball today at Aspire!", d8);
	private static final Tweet tweet9 = new Tweet(9, "matlab",
			"@html @javascript Full house for Special Olympics volleyball today at Aspire!", d9);
	private static final Tweet tweet10 = new Tweet(10, "verilog", "@cplus @html I am beter", d10);
	private static final Tweet tweet11 = new Tweet(11, "Pascal",
			"@c @html I love when my husband officiates for Special Olympics and sees our BHS family!", d11);
	private static final Tweet tweet12 = new Tweet(12, "lisp", "@R you are right.", d12);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void MySocialNectworkTest() {
		Map<String, Set<String>> smartGraph = SocialNetwork.MyGuessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3,
				tweet4, tweet5, tweet6, tweet7, tweet8, tweet9, tweet10, tweet11, tweet12));
		// ≤‚ ‘≤ø∑÷
		assertTrue("Expected Pascal contains php", (smartGraph.get("Pascal")).contains("php"));
		assertTrue("Expected php contains Pascal", (smartGraph.get("php")).contains("Pascal"));
		assertTrue("Expected c contains html", (smartGraph.get("c")).contains("html"));
		assertTrue("Expected html contains c", (smartGraph.get("html")).contains("c"));
		assertTrue("Expected verilog contains Pascal", (smartGraph.get("verilog")).contains("Pascal"));
		assertTrue("Expected Pascal contains verilog", (smartGraph.get("Pascal")).contains("verilog"));
	}
}

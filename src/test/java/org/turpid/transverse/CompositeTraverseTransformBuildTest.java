package org.turpid.transverse;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CompositeTraverseTransformBuildTest {

	static Transformlet<Integer, String> integerToString() {
		return (in, cli, clo, ctx) -> {
			return in.toString();
		};
	}

	static Transformlet<List<Integer>, List<String>> listToList() {
		return (in, cli, clo, ctx) -> {
			List<String> r = new ArrayList<String>();
			for (Integer i : in) {
				r.add(ctx.transform(i, Integer.class, String.class));
			}
			return r;
		};
	}

	@Test
	public void testTransformation() {
		CompositeTransform tf = new CompositeTransform(
				integerToString(), listToList());
		String s = tf
				.transform(Integer.valueOf(5), Integer.class, String.class);
		assertEquals("5", s);

		List<String> ls = tf.transform(Arrays.asList(1, 2, 3), List.class,
				List.class);
		assertEquals(3, ls.size());
		assertEquals(String.class, ls.get(0).getClass());
		assertEquals("[1, 2, 3]", ls.toString());
	}

	@Test
	public void testTraverse() {
		StringBuffer buf = new StringBuffer();
		Traverselet<String> t1 = (in, ctx) -> {
			buf.append(in);
			ctx.traverse(in.length());
		};
		Traverselet<Integer> t2 = (in, ctx) -> {
			buf.append(in);
		};
		CompositeTraverse tr = new CompositeTraverse(t1, t2);
		tr.traverse("test");
		assertEquals("test4", buf.toString());
	}

	@Test
	public void testTraverseAndBuild() {
		Traverselet<String> t1 = (in, ctx) -> {
			ctx.traverse(in.length());
		};
		Buildlet<String, List, Buildlet.Root> b1 = (in, p, stack, cli, clo, clp) -> {
			List<String> res = new ArrayList();
			res.add(in);
			return res;
		};
		Buildlet<Integer, List, String> b2 = (in, p, stack, cli, clo, clp) -> {
			List<Integer> res = new ArrayList();
			res.add(in);
			return res;
		};
		CompositeTraverse ct = new CompositeTraverse(new CompositeBuild(b1),
				t1);
		ct.traverse("Hello");
		List res = ct.getResult(List.class);
		assertEquals("Hello", res.get(0));

	}
}

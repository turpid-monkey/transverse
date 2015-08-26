package org.turpid.transverse;

import static org.junit.Assert.*;

import org.junit.Test;
import org.turpid.transverse.ValidationHook.Validatelet;

public class ValidationTest {

	@Test
	public void testValidation() {
		Traverselet<String> t1 = (in, ctx) -> {
			for (int i = 0; i < in.length(); i++)
				ctx.traverse(in.charAt(i));
		};
		Validatelet<Character, String> v1 = (in, parent, stack, cli, clo, clp) -> {
			if (in == 'a')
				return new ValidationHook.ValidationMessage("No 'a' allowed");
			return null;
		};
		ValidationHook hook = new ValidationHook(v1);
		CompositeTraverse traverse = new CompositeTraverse(hook, t1);
		traverse.traverse("test");
		assertEquals(0, hook.getMessages().size());
		traverse.traverse("taste");
		assertEquals(1, hook.getMessages().size());
		hook.reset();
		traverse.traverse("a tasty test");
		assertEquals(2, hook.getMessages().size());
		hook.reset();
		traverse.traverse('a');
		assertEquals(1, hook.getMessages().size());
	}

}

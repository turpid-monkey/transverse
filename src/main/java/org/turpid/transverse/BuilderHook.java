package org.turpid.transverse;

import java.util.Stack;

public class BuilderHook implements TraverseHook {

	Buildlet[] buildlets;
	Object result;

	public BuilderHook(Buildlet... buildlets) {
		this.buildlets = buildlets;
	}

	@Override
	public void handle(Object in, Object p, Stack stackIn, Class cli, Class clp) {
		Object r;
		for (Buildlet b : buildlets) {
			try {
				r = b.build(in, p, stackIn, cli, null, clp);
				if (p instanceof Traverselet.Root)
					result = r;
			} catch (ClassCastException e) {
				// nop
			}
		}
	}

	public <T> T getResult(Class<T> cls) {
		return cls.cast(result);
	}

}

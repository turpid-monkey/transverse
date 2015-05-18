package org.turpid.transverse;

import java.util.Stack;

public class CompositeBuild implements Buildlet {
	Buildlet[] buildlets;

	public CompositeBuild(Buildlet... transformlets) {
		this.buildlets = transformlets;
	}

	@Override
	public Object build(Object in, Object p, Stack stackIn, Class cli,
			Class clo, Class clp) {
		for (Buildlet t : buildlets) {
			try {
				return t.build(in, p, stackIn, cli, clo, clp);
			} catch (ClassCastException e) {
				// nope
			}
		}
		return null;
	}
}
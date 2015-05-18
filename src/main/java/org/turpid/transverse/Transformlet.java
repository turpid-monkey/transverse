package org.turpid.transverse;

@FunctionalInterface
public interface Transformlet<In, Out> {
	Out transform(In in, Class<In> cli, Class<Out> clo,
			CompositeTransform context);
}
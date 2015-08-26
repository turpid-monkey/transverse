package org.turpid.transverse;


@FunctionalInterface
public interface Validatelet<In> {
	void validate(In in, ValidationContext ctx);

}
package org.turpid.transverse;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ValidationHook implements TraverseHook {

	public static class ValidationMessage {
		String message;

		public ValidationMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public static interface Validatelet<In, Parent> extends
			Buildlet<In, ValidationMessage, Parent> {

	}

	List<ValidationMessage> messages = new ArrayList<ValidationHook.ValidationMessage>();
	Validatelet[] validations;

	public ValidationHook(Validatelet... validations) {
		this.validations = validations;
	}

	@Override
	public void handle(Object in, Object p, Stack stackIn, Class cli, Class clp) {
		for (Validatelet v : validations) {
			try {
				ValidationMessage msg = (ValidationMessage) v.build(in, p,
						stackIn, cli, ValidationMessage.class, clp);
				if (msg != null)
					messages.add(msg);
			} catch (ClassCastException e) {
				// nop
			}
		}
	}

	public List<ValidationMessage> getMessages() {
		return messages;
	}

	public void reset() {
		messages.clear();
	}
}

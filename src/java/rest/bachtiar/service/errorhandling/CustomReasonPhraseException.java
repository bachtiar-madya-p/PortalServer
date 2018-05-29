package rest.bachtiar.service.errorhandling;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomReasonPhraseException extends Exception {
		
	private static final long serialVersionUID = -271582074543512905L;
	
	private final int businessCode;

	public CustomReasonPhraseException(int businessCode, String message) {
		super(message);
		this.businessCode = businessCode;
	}

	public int getBusinessCode() {
		return businessCode;
	}
		
}

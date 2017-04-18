import java.nio.file.AccessDeniedException;
import com.documentum.fc.client.DfSingleDocbaseModule;
import com.emc.modules.util.MsgService;

public class ErrorModule extends DfSingleDocbaseModule {

	public Void throwError(int errorcode, String formId) throws Exception {

		try
		{

			switch (errorcode) {
				case 0:
					throw new AccessDeniedException("Kind check ur permissions .You can not perform this operatoin.");
				case 1:
					throw new ArrayIndexOutOfBoundsException("Error occuerd in storing vaues in arrays..Try later");
				case 2:
					throw new NullPointerException("XXX was null ,Kindly fix the value and try later");
				case 3:
					throw new RuntimeException("Object state must be XXXX but is LLLLCC.The operation Failed");

				default:
					throw new IllegalArgumentException("Invalid arguments ::DD FF CC GG");
			}
		} catch (Exception e)
		{
			new MsgService(formId).storeMessage("Error Occured  :\n" + e.getMessage());
			throw (e);
		}

	}

}
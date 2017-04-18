import java.util.ArrayList;
import java.util.List;
import com.documentum.fc.client.DfSingleDocbaseModule;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfDocbaseConstants;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfId;

public class TestModule extends DfSingleDocbaseModule {

	public String getObjID(IDfId id) throws DfException {
		return ((IDfSysObject) getSession().getObject(id)).getChronicleId().getId();
	}

	public String getObjIDWithSleep(IDfId id) throws DfException, InterruptedException {
		System.out.println("Sleeping for 5 seconds");
		Thread.sleep(5000);
		return ((IDfSysObject) getSession().getObject(id)).getChronicleId().getId();
	}

	public String getObjIDWithMoreSleep(IDfId id) throws DfException, InterruptedException {
		System.out.println("Sleeping for 10 seconds");
		Thread.sleep(10000);
		return ((IDfSysObject) getSession().getObject(id)).getChronicleId().getId();
	}

	public NameAndlabels getnameAndversionLabels(IDfId id) throws DfException, InterruptedException {

		NameAndlabels nL = new NameAndlabels();
		IDfSysObject iDfSysObject = (IDfSysObject) getSession().getObject(id);
		nL.labels.addAll(java.util.Arrays.asList(iDfSysObject.getAllRepeatingStrings(DfDocbaseConstants.R_VERSION_LABEL, "##").split("##")));
		nL.name = iDfSysObject.getObjectName();
		return nL;
	}

	public Void throwError(int errorcode) {

		switch (errorcode) {
			case 0:
				int i = 5 / 0;
			case 1:
				String[] arr = new String[2];
				String value = arr[9];
			case 2:
				String test = null;
				test.split("");
			case 3:
				throw new RuntimeException("Some dummy Exception");

			default:
				throw new IllegalArgumentException("Need better arguments");
		}

	}

	public class NameAndlabels
	{
		String name = "";
		List<String> labels = new ArrayList<>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getLabels() {
			return labels;
		}

		public void setLabels(List<String> labels) {
			this.labels = labels;
		}

	}
}

package com.emc.errorhandle;

import com.documentum.fc.client.DfSingleDocbaseModule;
import com.documentum.fc.common.DfException;
import com.emc.modules.util.MsgService;

public class MsgServiceModule extends DfSingleDocbaseModule {

	public String getMessage(final String formId) throws DfException {
		return new MsgService(formId).getMessage();
	}

}

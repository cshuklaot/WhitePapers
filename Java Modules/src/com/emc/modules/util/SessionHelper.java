package com.emc.modules.util;

import com.documentum.com.DfClientX;
import com.documentum.fc.client.DfAuthenticationException;
import com.documentum.fc.client.DfIdentityException;
import com.documentum.fc.client.DfPrincipalException;
import com.documentum.fc.client.DfServiceException;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

public class SessionHelper {

	private IDfSession session;

	public SessionHelper(IDfSession processSession) {
		this.session = processSession;
	}

	public IDfSession getNewManagerSession() throws DfIdentityException, DfAuthenticationException,
			DfPrincipalException, DfServiceException, DfException {
		final IDfLoginInfo info = session.getSessionManager().getIdentity(session.getDocbaseName());
		final IDfSessionManager newMan = new DfClientX().getLocalClient().newSessionManager();
		newMan.setIdentity(session.getDocbaseName(), info);
		return newMan.newSession(session.getDocbaseName());

	}

}

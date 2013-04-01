package com.ironiacorp.scm.git;

import com.ironiacorp.scm.Repository;
import com.ironiacorp.scm.RepositoryTransaction;
import com.ironiacorp.scm.TransactionStatus;

public class GitTransaction extends RepositoryTransaction {

	public GitTransaction(Repository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TransactionStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}

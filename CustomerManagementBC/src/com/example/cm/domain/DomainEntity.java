package com.example.cm.domain;

public abstract class DomainEntity<T> {
	protected T identity;

	public DomainEntity(T identity) {
		this.identity = identity;
	}

	public T getIdentity() {
		return identity;
	}

	public void setIdentity(T identity) {
		this.identity = identity;
	}
	
}

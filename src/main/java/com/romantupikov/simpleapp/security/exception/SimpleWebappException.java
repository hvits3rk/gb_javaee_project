package com.romantupikov.simpleapp.security.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public abstract class SimpleWebappException extends RuntimeException {
}

package com.braincourt.onehotvectors.writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Writer {


    public Logger LOG = LoggerFactory.getLogger(this.getClass());

    public abstract void write();

}

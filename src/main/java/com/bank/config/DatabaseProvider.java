package com.bank.config;

import com.google.inject.Provider;
import org.h2.engine.Database;

public class DatabaseProvider implements Provider<Database> {


    @Override
    public Database get() {
        return null;
    }
}

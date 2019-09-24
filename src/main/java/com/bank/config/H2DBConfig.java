package com.bank.config;

import java.net.URL;

public interface H2DBConfig {
    static final String URL ="jdbc:h2:mem:bank_db;DB_CLOSE_DELAY=-1";
    static final String USER ="sa";
    static final String PASSWD ="s$cret";
    static final String DB_DRIVER = "org.h2.Driver";
    static final String PERSISTENCE_UNIT_NAME = "account";



}

package com.bank.config;

public interface JsonDBConfig {
    //Actual location on disk for database files, process should have read-write permissions to this folder
    String DB_FILES_LOCATION = "/db";
    String DB_FILES_LOCATION_TEST = "/db_test";

    //Java package name where POJO's are present
    String BASE_SCAN_PACKAGE = "com.bank.model.jsondb";

}

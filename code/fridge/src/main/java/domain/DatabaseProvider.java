package com.freezer.domain;

/**
 * Created by JD on 07.12.2016.
 */
interface DatabaseProvider {

    boolean init(String host, int port);

    void setDatabaseName(String databaseName);

    boolean connect();
}

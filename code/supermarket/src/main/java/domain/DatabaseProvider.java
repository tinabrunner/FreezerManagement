package domain;

interface DatabaseProvider {

	boolean init(String host, int port);

	void setDatabaseName(String databaseName);

	boolean connect();
}

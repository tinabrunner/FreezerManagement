package domain;

public interface DatabaseProvider {
	
	boolean connect();
	void setDatabaseName(String databaseName);
}

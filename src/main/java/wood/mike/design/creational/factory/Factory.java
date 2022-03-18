package wood.mike.design.creational.factory;

/**
 * Factory method design
 */
public class Factory {
    public static void main(String[] args) {
        Database database = DatabaseFactory.getDatabase("oracle");
        Long id = database.insert();
    }
}

class DatabaseFactory {
    public static Database getDatabase( final String dbName ) {
        return switch (dbName) {
            case "oracle" -> new Oracle();
            case "mysql" -> new MySql();
            default -> throw new IllegalStateException("Unknown database vendor: " + dbName);
        };
    }
}

sealed interface Database permits Oracle, MySql{
    Long insert();
}

final class Oracle implements Database{
    @Override
    public Long insert() {
        return null;
    }
}

final class MySql implements Database{
    @Override
    public Long insert() {
        return null;
    }
}

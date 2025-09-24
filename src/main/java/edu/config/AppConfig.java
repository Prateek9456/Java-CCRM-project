package edu.ccrm.config;

import java.util.Properties;

/**
 * Application Configuration class implementing Singleton Design Pattern
 * Ensures only one instance of configuration exists throughout the application
 */
public class AppConfig {
    
    // Singleton instance - volatile for thread safety
    private static volatile AppConfig instance;
    
    // Application properties
    private final Properties properties;
    
    // Default configuration values
    private static final String DEFAULT_DATA_PATH = "data/";
    private static final String DEFAULT_BACKUP_PATH = "backup/";
    private static final int DEFAULT_MAX_CREDITS = 24;
    private static final String DEFAULT_APP_NAME = "Campus Course Registration Management";
    private static final String DEFAULT_APP_VERSION = "1.0.0";
    
    /**
     * Private constructor to prevent external instantiation
     */
    private AppConfig() {
        this.properties = new Properties();
        initializeDefaults();
    }
    
    /**
     * Get the singleton instance (thread-safe double-checked locking)
     * @return the singleton AppConfig instance
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    /**
     * Initialize default configuration values
     */
    private void initializeDefaults() {
        properties.setProperty("app.name", DEFAULT_APP_NAME);
        properties.setProperty("app.version", DEFAULT_APP_VERSION);
        properties.setProperty("data.path", DEFAULT_DATA_PATH);
        properties.setProperty("backup.path", DEFAULT_BACKUP_PATH);
        properties.setProperty("max.credits", String.valueOf(DEFAULT_MAX_CREDITS));
        properties.setProperty("file.encoding", "UTF-8");
        properties.setProperty("date.format", "dd-MM-yyyy");
        properties.setProperty("backup.auto.enabled", "true");
    }
    
    /**
     * Get a property value
     * @param key the property key
     * @return the property value, or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get a property value with default fallback
     * @param key the property key
     * @param defaultValue the default value if key not found
     * @return the property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Set a property value
     * @param key the property key
     * @param value the property value
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    /**
     * Get the data directory path
     * @return data directory path
     */
    public String getDataPath() {
        return getProperty("data.path", DEFAULT_DATA_PATH);
    }
    
    /**
     * Get the backup directory path
     * @return backup directory path
     */
    public String getBackupPath() {
        return getProperty("backup.path", DEFAULT_BACKUP_PATH);
    }
    
    /**
     * Get maximum credit limit
     * @return maximum credit limit
     */
    public int getMaxCredits() {
        try {
            return Integer.parseInt(getProperty("max.credits", String.valueOf(DEFAULT_MAX_CREDITS)));
        } catch (NumberFormatException e) {
            return DEFAULT_MAX_CREDITS;
        }
    }
    
    /**
     * Get application name
     * @return application name
     */
    public String getAppName() {
        return getProperty("app.name", DEFAULT_APP_NAME);
    }
    
    /**
     * Get application version
     * @return application version
     */
    public String getAppVersion() {
        return getProperty("app.version", DEFAULT_APP_VERSION);
    }
    
    /**
     * Check if auto backup is enabled
     * @return true if auto backup is enabled
     */
    public boolean isAutoBackupEnabled() {
        return Boolean.parseBoolean(getProperty("backup.auto.enabled", "true"));
    }
    
    /**
     * Get all properties
     * @return copy of all properties
     */
    public Properties getAllProperties() {
        return new Properties(properties);
    }
    
    /**
     * Prevent cloning of singleton instance
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton instance cannot be cloned");
    }
    
    @Override
    public String toString() {
        return String.format("AppConfig [%s v%s, DataPath: %s, BackupPath: %s]",
                           getAppName(), getAppVersion(), getDataPath(), getBackupPath());
    }
}

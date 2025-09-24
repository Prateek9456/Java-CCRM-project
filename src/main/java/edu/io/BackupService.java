package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * Backup Service with recursive directory operations
 * Demonstrates recursion and Date/Time API usage
 */
public class BackupService {
    
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    
    private final AppConfig config;
    
    public BackupService() {
        this.config = AppConfig.getInstance();
    }
    
    /**
     * Perform backup operation - creates timestamped backup directory
     * Uses Date/Time API for timestamp generation
     * @return path to the created backup directory
     * @throws IOException if backup operations fail
     */
    public Path performBackup() throws IOException {
        // Generate timestamp for backup directory name
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String backupDirName = "backup-" + timestamp;
        
        // Create backup directory path
        Path backupBasePath = Paths.get(config.getBackupPath());
        Path backupDir = backupBasePath.resolve(backupDirName);
        
        // Create backup directory
        Files.createDirectories(backupDir);
        
        // Source data directory
        Path sourceDir = Paths.get(config.getDataPath());
        
        if (Files.exists(sourceDir)) {
            // Copy all files from source to backup directory
            copyDirectoryRecursively(sourceDir, backupDir);
            System.out.println("Backup completed successfully: " + backupDir);
        } else {
            System.out.println("Source data directory doesn't exist, creating empty backup: " + backupDir);
        }
        
        return backupDir;
    }
    
    /**
     * Copy directory contents recursively
     * @param source source directory
     * @param target target directory
     * @throws IOException if copy operations fail
     */
    private void copyDirectoryRecursively(Path source, Path target) throws IOException {
        // Use try-with-resources with Files.walk for recursive directory traversal
        try (Stream<Path> paths = Files.walk(source)) {
            paths.forEach(sourcePath -> {
                try {
                    Path targetPath = target.resolve(source.relativize(sourcePath));
                    
                    if (Files.isDirectory(sourcePath)) {
                        Files.createDirectories(targetPath);
                    } else {
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy: " + sourcePath, e);
                }
            });
        }
    }
    
    /**
     * Calculate directory size using recursion
     * Public method that calls private recursive helper
     * @param path directory path to calculate size for
     * @return total size in bytes
     * @throws IOException if directory access fails
     */
    public long calculateDirectorySize(Path path) throws IOException {
        if (!Files.exists(path)) {
            return 0;
        }
        
        if (Files.isRegularFile(path)) {
            return Files.size(path);
        }
        
        // Call private recursive helper method
        return calculateDirectorySizeRecursive(path);
    }
    
    /**
     * Private recursive helper method to calculate directory size
     * Demonstrates recursion concept
     * @param path directory path
     * @return total size in bytes
     * @throws IOException if file operations fail
     */
    private long calculateDirectorySizeRecursive(Path path) throws IOException {
        long totalSize = 0;
        
        // Base case: if it's a regular file, return its size
        if (Files.isRegularFile(path)) {
            return Files.size(path);
        }
        
        // Recursive case: if it's a directory, sum up all contents
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path entry : directoryStream) {
                    // Recursive call for each entry
                    totalSize += calculateDirectorySizeRecursive(entry);
                }
            }
        }
        
        return totalSize;
    }
    
    /**
     * List all backup directories
     * @return list of backup directory paths
     * @throws IOException if directory listing fails
     */
    public java.util.List<Path> listBackups() throws IOException {
        Path backupBasePath = Paths.get(config.getBackupPath());
        
        if (!Files.exists(backupBasePath)) {
            return new java.util.ArrayList<>();
        }
        
        try (Stream<Path> paths = Files.list(backupBasePath)) {
            return paths.filter(Files::isDirectory)
                       .filter(path -> path.getFileName().toString().startsWith("backup-"))
                       .sorted((p1, p2) -> p2.getFileName().toString().compareTo(p1.getFileName().toString()))
                       .collect(java.util.stream.Collectors.toList());
        }
    }
    
    /**
     * Delete old backups, keeping only the specified number of recent backups
     * @param keepCount number of recent backups to keep
     * @return number of deleted backup directories
     * @throws IOException if delete operations fail
     */
    public int cleanupOldBackups(int keepCount) throws IOException {
        java.util.List<Path> backups = listBackups();
        
        if (backups.size() <= keepCount) {
            return 0;
        }
        
        int deletedCount = 0;
        // Delete older backups (keep only the most recent ones)
        for (int i = keepCount; i < backups.size(); i++) {
            Path backupToDelete = backups.get(i);
            deleteDirectoryRecursively(backupToDelete);
            deletedCount++;
        }
        
        return deletedCount;
    }
    
    /**
     * Delete directory recursively
     * @param path directory to delete
     * @throws IOException if delete operations fail
     */
    private void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> paths = Files.walk(path)) {
                paths.sorted(java.util.Comparator.reverseOrder())
                     .forEach(p -> {
                         try {
                             Files.delete(p);
                         } catch (IOException e) {
                             throw new RuntimeException("Failed to delete: " + p, e);
                         }
                     });
            }
        }
    }
    
    /**
     * Get backup information including size
     * @param backupPath path to backup directory
     * @return formatted backup information
     */
    public String getBackupInfo(Path backupPath) {
        try {
            long size = calculateDirectorySize(backupPath);
            String sizeFormatted = formatFileSize(size);
            
            return String.format("Backup: %s, Size: %s", 
                               backupPath.getFileName().toString(), sizeFormatted);
        } catch (IOException e) {
            return String.format("Backup: %s, Size: Error calculating size", 
                               backupPath.getFileName().toString());
        }
    }
    
    /**
     * Format file size in human-readable format
     * @param bytes size in bytes
     * @return formatted size string
     */
    private String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp - 1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}

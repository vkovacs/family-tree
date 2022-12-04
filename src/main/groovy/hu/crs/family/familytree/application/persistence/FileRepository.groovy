package hu.crs.family.familytree.application.persistence

import org.springframework.stereotype.Repository

@Repository
class FileRepository {
    private final String HOME = System.getProperty("user.home")
    private final String FILE_STORE = "${HOME}/.family-tree"
    private final String BACKUP_FILE_STORE = "${FILE_STORE}/backup/"

    String filePath(String filename) {
        "$FILE_STORE/$filename"
    }

    void saveFile(String filename, String content) {
        def newPath = filePath(filename)
        persistFile(newPath, content)
    }

    void saveBackupFile(String filename, String content) {
        def newBackupPath = "$BACKUP_FILE_STORE/$filename"
        persistFile(newBackupPath, content)
    }

    String load(String filename) {
        def path = filePath(filename)
        def myFile = new File(path)

        myFile.readLines().join()
    }

    private void persistFile(String path, String content) {
        def newFile = new File(path)

        if (!newFile.exists()) newFile.getParentFile().mkdirs()

        newFile.write(content)
    }

}

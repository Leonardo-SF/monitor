package repository.file.exception;

public class FileException extends Exception {

    private FileException (String message) {
        super(message);
    }

    private FileException (String message, Exception e) {
        super(message, e);
    }

    public static class FileWriteException extends FileException {
        public FileWriteException(String msg) {
            super(msg);
        }

        public FileWriteException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class FileReadException extends FileException {
        public FileReadException(String msg) {
            super(msg);
        }

        public FileReadException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class FileLineFormatException extends FileException {
        public FileLineFormatException(String msg) {
            super(msg);
        }

        public FileLineFormatException(String msg, Exception e) {
            super(msg, e);
        }
    }

    public static class FolderCreationException extends FileException {
        public FolderCreationException(String msg) {
            super(msg);
        }

        public FolderCreationException(String msg, Exception e) {
            super(msg, e);
        }
    }

}

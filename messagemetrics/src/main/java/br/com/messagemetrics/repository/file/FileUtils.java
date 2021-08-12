package br.com.messagemetrics.repository.file;

import br.com.messagemetrics.exceptions.FileException;
import br.com.messagemetrics.model.Metric;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalTime;

@Component
public class FileUtils {

    private final MonitorFile monitorFile;
    private final FolderFile folder;

    public FileUtils(@Value("${monitor.files.path}") String fileDirectory) throws FileException.FolderCreationException {
        this.folder = new FolderFile();
        folder.setFile(getTempDirectory(fileDirectory));

        this.monitorFile = new MonitorFile();
        monitorFile.setFile(createMonitorFile());
    }

    public MonitorFile getMonitorFile() {
        return monitorFile;
    }

    private File createMonitorFile() {
        var filePath = String.format("%s/monitor-%s", folder.getFile().getAbsolutePath(), "topics");
        return new File(filePath);
    }

    public File getTopicFile(String topic) {
        var filePath = String.format("%s/monitor-%s", folder.getFile().getAbsolutePath(), topic);
        return new File(filePath);
    }

    private File getTempDirectory(String fileDirectory) throws FileException.FolderCreationException {
        var folder = new File(fileDirectory);

        if (!folder.exists() || !folder.isDirectory()) {
            var success = folder.mkdir();
            if (!success) {
                throw new FileException.FolderCreationException("Can not create folder :: " + fileDirectory);
            }
        }

        return folder;
    }

    public Metric readLastLine(File file) throws FileException.FileReadException, FileException.FileLineFormatException {
        try (var fileHandler = new RandomAccessFile(file, "r")) {
            var fileLength = fileHandler.length() - 2;
            var reverseLine = new StringBuilder();

            for(long filePointer = fileLength; filePointer >= 0; filePointer--){
                fileHandler.seek(filePointer);

                char c = (char) fileHandler.read();
                if(c == '\n') {
                    break;
                }

                reverseLine.append(c);
            }

            return parse(reverseLine.reverse().toString());
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new FileException.FileReadException("Can not read file :: " + e.getMessage(), e);
        }
    }

    public Metric parse(String line) throws FileException.FileLineFormatException {
        if (line == null || line.isEmpty()) {
            return null;
        }

        var splitedLine = line.split(";");

        if (splitedLine.length != 7) {
            throw new FileException.FileLineFormatException("Wrong number of columns :: " + line);
        }

        Metric metric = new Metric();
        metric.setId(Long.valueOf(splitedLine[0]));
        metric.setTotalRead(Long.valueOf(splitedLine[1]));
        metric.setUpdateTime(LocalTime.parse(splitedLine[2]));
        metric.setTotalUnread(Long.valueOf(splitedLine[3]));
        metric.setTotalOut(Long.valueOf(splitedLine[4]));
        metric.setTotalIn(Long.valueOf(splitedLine[5]));
        metric.setTotal(Long.valueOf(splitedLine[6]));

        return metric;
    }

    public class MonitorFile {
        private File file;

        public File getFile() {
            return file;
        }

        public MonitorFile setFile(File file) {
            this.file = file;
            return this;
        }
    }

    public class FolderFile {
        private File file;

        public File getFile() {
            return file;
        }

        public FolderFile setFile(File file) {
            this.file = file;
            return this;
        }
    }

}

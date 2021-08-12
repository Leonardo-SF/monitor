package repository.file;

import exceptions.FileException;
import exceptions.MetricException;
import br.com.messagecore.model.Metric;
import br.com.messagecore.model.MetricAverage;
import br.com.messagecore.model.Topic;
import org.springframework.stereotype.Component;
import repository.MetricRepository;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileMetricRepository implements MetricRepository {

    private final FileUtils fileUtils;

    public FileMetricRepository(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @Override
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void saveMonitor(String topic) throws MetricException {
        var monitorFile = fileUtils.getMonitorFile();
        synchronized (monitorFile) {
            try (var writer = new BufferedWriter(new FileWriter(monitorFile.getFile(), monitorFile.getFile().exists()))) {
                writer.append(topic).append("\n");
            } catch (IOException e) {
                throw new MetricException.MetricWriteException("Can not write to file monitor-topics :: " + e.getMessage(), e);
            }
        }
    }

    @Override
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public List<String> findAllMonitors() throws MetricException {
        var monitorFile = fileUtils.getMonitorFile();

        if (!monitorFile.getFile().exists()) {
            return new ArrayList<>(0);
        }

        var topics = new ArrayList<String>();

        synchronized (monitorFile) {
            try (var reader = new BufferedReader(new FileReader(monitorFile.getFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    topics.add(line);
                }

                return topics;
            } catch (IOException e) {
                throw new MetricException.MetricReadException("Can not read file monitor-topics :: " + e.getMessage(), e);
            }
        }
    }

    @Override
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void removeMonitor(String topic) throws MetricException.MetricWriteException {
        var monitorFile = fileUtils.getMonitorFile();
        PrintWriter printWriter = null;
        synchronized (monitorFile) {
            String absolutePath = monitorFile.getFile().getAbsolutePath();
            try {
                StringBuilder text = new StringBuilder();
                Files.lines(monitorFile.getFile().toPath())
                        .filter(line -> !line.equals(topic))
                        .forEach(text::append);

                Files.delete(monitorFile.getFile().toPath());

                printWriter = new PrintWriter(absolutePath);
                printWriter.print(text);
            } catch (IOException e) {
                throw new MetricException.MetricWriteException("Could not remove line from file " + absolutePath + " :: " + e.getMessage(), e);
            } finally {
                if (printWriter != null)
                    printWriter.close();
            }
        }
    }

    @Override
    public void saveTopicMetrics(Topic topic) throws MetricException {
        var file = fileUtils.getTopicFile(topic.getName());
        try (var writer =  new BufferedWriter(new FileWriter(file, file.exists()))) {
            var lastMetric = fileUtils.readLastLine(file);

            var id =  lastMetric != null ? lastMetric.getId() + 1 : 0;
            var totalIn =  lastMetric != null ? topic.getNumberOfMessages() - lastMetric.getTotal() : 0;
            var totalOut = lastMetric != null ? (lastMetric.getTotalUnread() + totalIn) - topic.getLag() : topic.getLag();

            var line = String.format("%s;%s;%s;%s;%s;%s;%s",
                    id, topic.getRead(), LocalTime.now().toString(), topic.getLag(), totalOut, totalIn,
                    topic.getNumberOfMessages());

            writer.append(line).append("\n");
        } catch (IOException | FileException.FileReadException | FileException.FileLineFormatException e) {
            throw new MetricException.MetricWriteException("Can not write to file ::" + e.getMessage(), e);
        }
    }

    @Override
    public List<Metric> findMetrics(String topic) throws MetricException {
        var file = fileUtils.getTopicFile(topic);

        if (!file.exists())
            return new ArrayList<>(0);

        var metrics = new ArrayList<Metric>();

        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                metrics.add(fileUtils.parse(line));
            }

            return metrics;
        } catch (IOException | FileException.FileLineFormatException e) {
            throw new MetricException.MetricReadException("Error reading file :: " + e.getMessage(), e);
        }
    }

    @Override
    public MetricAverage findAverage(String topic) throws MetricException {
        var file = fileUtils.getTopicFile(topic);

        if (!file.exists())
            return new MetricAverage(topic, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        try (var reader = new BufferedReader(new FileReader(file))) {
            long lag = 0L, totalIn = 0L, totalOut = 0L, totalMinutes = 0L;
            boolean first = true;
            LocalTime previousDate = null;

            String line;
            while ((line = reader.readLine()) != null) {
                var splitedLine = line.split(";");

                if (splitedLine.length != 7) {
                    continue;
                }

                lag = Long.parseLong(splitedLine[3]);
                totalOut += Long.parseLong(splitedLine[4]);
                totalIn += Long.parseLong(splitedLine[5]);

                var date = LocalTime.parse(splitedLine[2]);
                if (first) {
                    first = false;
                    previousDate = date;
                    continue;
                }

                totalMinutes += (ChronoUnit.MINUTES.between(previousDate, date));
                previousDate = date;
            }

            var totalMinutesDecimal = new BigDecimal(totalMinutes);
            var avgOut = new BigDecimal(totalOut).divide(totalMinutesDecimal, RoundingMode.HALF_UP);
            var avgIn = new BigDecimal(totalIn).divide(totalMinutesDecimal, RoundingMode.HALF_UP);
            var estimatedTime = new BigDecimal(lag).divide(avgOut, RoundingMode.HALF_UP);
            return new MetricAverage(topic, avgIn, avgOut, estimatedTime);
        } catch (IOException e) {
            throw new MetricException.MetricReadException("Error reading file :: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            return new MetricAverage(topic, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }
    }

}

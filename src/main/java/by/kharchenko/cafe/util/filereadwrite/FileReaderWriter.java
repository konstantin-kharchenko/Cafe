package by.kharchenko.cafe.util.filereadwrite;

import by.kharchenko.cafe.exception.ServiceException;

import java.io.*;

public class FileReaderWriter {
    private static final FileReaderWriter instance = new FileReaderWriter();

    private FileReaderWriter() {
    }

    public static FileReaderWriter getInstance() {
        return instance;
    }

    public String readPhoto(String path) throws ServiceException {
        String data = "";
        if (path != null) {
            try {
                File file = new File(path);
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                data = stringBuilder.toString();
            } catch (FileNotFoundException e) {
                //log
            } catch (IOException e) {
                throw new ServiceException(e);
            }
        }
        return data;
    }

    public boolean writePhoto(String data, String path) throws ServiceException {
        try (FileWriter nFile = new FileWriter(path)) {
            nFile.write(data);
            return true;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}

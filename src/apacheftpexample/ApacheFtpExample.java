package apacheftpexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

public class ApacheFtpExample {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        length = file.length();
        long startTime = System.currentTimeMillis();
        System.out.println("start: " + (System.currentTimeMillis() - startTime));

        FTPClient ftpClient = new FTPClient();
        //ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));    //Отображение лога в sysout
        ftpClient.connect("5.101.156.8");
        ftpClient.login("mailru5o_login", "im699000pass");
        ftpClient.enterLocalPassiveMode();  //невероятно важная строчка - без нее ничего не работает
        ftpClient.changeWorkingDirectory("TestDir");

        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            private int oldPercent = 0;

            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                int percent = (int) (totalBytesTransferred * 100 / length);
                if (percent != oldPercent) {
                    oldPercent = percent;
                    if (percent % 10 == 0) {
                        System.out.println("progress: " + percent);
                    }
                }
            }
        };

        ftpClient.setCopyStreamListener(streamListener);
        FileInputStream fis = new FileInputStream(file);

        System.out.println("startStore: " + (System.currentTimeMillis() - startTime));
        ftpClient.storeFile("testFile.bin", fis);       //перезаписываем файл "text.txt" файлом "file"
        System.out.println("endStore: " + (System.currentTimeMillis() - startTime));

        //ftpClient.appendFile("text.txt", fis);	//добавляем содержимое "fis" в конец файла "text.txt"
        fis.close();
        ftpClient.logout();
    }

    private static File file = new File("E:/testFile2.bin");
    private static long length;
}

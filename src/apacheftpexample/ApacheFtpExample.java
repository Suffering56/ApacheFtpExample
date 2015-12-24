package apacheftpexample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;

public class ApacheFtpExample {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FTPClient ftpClient = new FTPClient();

        ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        ftpClient.connect("5.101.156.8");
        ftpClient.login("mailru5o_login", "im699000pass");
        ftpClient.enterLocalPassiveMode();  //невероятно важная строчка - без нее ничего не работает

        FileInputStream fis = new FileInputStream("f:/text.txt");
        ftpClient.changeWorkingDirectory("TestDir");
        
        ftpClient.appendFile("text.txt", fis);	//добавляем содержимое "fis" в конец файла "text.txt"
        
        //ftpClient.storeFile("text.txt", fis);	//перезаписываем файл "text.txt" файлом "fis"
        ftpClient.logout();
    }
}

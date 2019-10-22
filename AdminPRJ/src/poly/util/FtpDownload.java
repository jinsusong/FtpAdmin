package poly.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import poly.dto.FtphistoryDTO;
public class FtpDownload {
	/* 118.219..183", 21, "", "! */
    private String serverIp ="118..232.";
    private int serverPort = 21;
    private String user ="";
    private String password ="!";
    
    public FtpDownload(String serverIp, int serverPort, String user, String password) {
		/*
		 * this.serverIp = serverIp; this.serverPort = serverPort; this.user = user;
		 * this.password = password;
		 */
    } 
    public boolean download(FtphistoryDTO fDTO, HttpServletResponse response) throws SocketException, IOException, Exception {
        FileInputStream fis = null;
        FTPClient ftpClient = new FTPClient();
        
        try {
            ftpClient.connect(serverIp, serverPort);
            ftpClient.setControlEncoding("utf-8");
            int reply = ftpClient.getReplyCode();
            System.out.println("reply : " + reply);
            
            ftpClient.login(user, password);//로그??
            
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                throw new Exception(serverIp+" FTP ?�버 ?�결 ?�패");
            }
            
            if(!ftpClient.login(user, password)) {
                ftpClient.logout();
                throw new Exception(serverIp+" FTP ?�버??로그?�하지 못했?�니??");
            }
            
            ftpClient.setSoTimeout(1000 * 10);
            boolean reply2 = ftpClient.login(user, password);
            System.out.println("reply2 : " + reply2);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalActiveMode();
            
            String[] depth = fDTO.getFtp_send_date().substring(0, 9).split("-");
			/*
			 * String ftpPath =
			 * "/"+depth[0]+"/"+depth[1]+"/"+depth[2]+"/"+fDTO.getFtp_seq();
			 */
            String ftpPath = "/180";
            System.out.println("ftpPath : " + ftpPath);
            boolean success  = false;
            OutputStream outputStream2 = new BufferedOutputStream(response.getOutputStream());
            System.out.println("1 : " +ftpClient.printWorkingDirectory());
            ftpClient.changeWorkingDirectory("/data");
            ftpClient.changeWorkingDirectory("/quiz01");
            
            InputStream inputStream = ftpClient.retrieveFileStream(ftpPath);
            System.out.println("inputSTream : " + inputStream);
            System.out.println("2 : " +ftpClient.printWorkingDirectory());
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            System.out.println("?�기");
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream2.write(bytesArray, 0, bytesRead);
            }
            System.out.println("outputStream2 : " + outputStream2);
 
            success = ftpClient.completePendingCommand();
            outputStream2.close();
            inputStream.close();
            
            return success;
            
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}


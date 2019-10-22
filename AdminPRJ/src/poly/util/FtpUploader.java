package poly.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import poly.dto.FtphistoryDTO;
import sun.security.util.PropertyExpander;
 
public class FtpUploader {
	
	private Logger log = Logger.getLogger(this.getClass());
	FTPClient ftp = null;



    //param( host server ip, username, password )
    public int ftpUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        
        System.out.println("host: " + host);
        System.out.println("user: " + user);
        System.out.println("pwd: " + pwd);
        ftp.setControlEncoding("UTF-8");
        ftp.connect(host);//호스트 연결
        reply = ftp.getReplyCode();
        System.out.println("reply : " + reply);
        
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);//로그인
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        
        return reply;
        
    }
    public boolean ftpDownload(String ftp_send_date, String ftp_status, String ftp_seq ,String ftp_filename ,HttpServletResponse response, HttpServletRequest request) throws Exception{
        System.out.println("ftpDownload start !!");
        String filePath = ftp_filename.substring(0, 11);
        String fileName = ftp_filename.substring(11);
        String[] depth = ftp_send_date.substring(0, 10).split("-");
        
        String defaultfilepath = request.getSession().getServletContext().getRealPath("/");

     

        
        boolean success2 = false;
        
        log.info("filePath : " + filePath);
        log.info("fileName : " + fileName);
        
     
        try {
        
        	setDisposition(fileName, request, response);

			/* ftp.enterLocalPassiveMode(); */
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalActiveMode();
            
            String remoteFile2 = filePath+fileName;
            log.info("system get Property :  " +System.getProperty("user.home"));
			/*
			 * File fileDir = new
			 * File(System.getProperty("user.home")+"/Documents"+"/퀴즈톡 받은 파일"+filePath);
			 */
			/*
			 * File fileDir = new File(defaultfilepath+"\\resource\\download\\"+"/퀴즈톡 받은
			 * 파일"+filePath); log.info("fileDir : " + fileDir);
			 */
            File downloadFile2 = new File(filePath+fileName);
            
            
			/*
			 * if(!fileDir.exists()) { //디렉토리 생성 메서드 fileDir.mkdirs();
			 * log.info("created directory successfully!"); }
			 */
            
			/*
			 * OutputStream outputStream2 = new BufferedOutputStream(new
			 * FileOutputStream(downloadFile2));
			 */
            OutputStream outputStream2 = response.getOutputStream();
            InputStream inputStream = ftp.retrieveFileStream(remoteFile2);
            log.info("inputStream : " + inputStream);
            byte[] bytesArray = new byte[4096];
            int bytesRead = -1;
            while((bytesRead =inputStream.read(bytesArray))!= -1) {
            	outputStream2.write(bytesArray,0,bytesRead);
            	log.info("outputStream2 : " + outputStream2);
            	log.info("bytesArray : " +bytesArray);
            }
            success2 = ftp.completePendingCommand();
            if(success2) {
            	System.out.println("file 2 has been downloaded successfully.");
            }else{
            	log.info("file has been downloaded fail");
            }

            outputStream2.close();
            inputStream.close();
            outputStream2 = null;
            inputStream = null;
            
	    } finally {
	        if (ftp.isConnected()) {
	            ftp.disconnect();
	            ftp = null;
	        }
	        
	        
	    }
        
        System.out.println("ftpDownload end !!");
        return success2;
    }
    
    
    void setDisposition(String fileName, HttpServletRequest request,HttpServletResponse response) throws Exception {
        String browser = getBrowser(request);
        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll(
            "\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\""
            + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\""
            + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < fileName.length(); i++) {
            char c = fileName.charAt(i);
            if (c > '~') {
                sb.append(URLEncoder.encode("" + c, "UTF-8"));
            } else {
                sb.append(c);
            }
        }
        encodedFilename = sb.toString();
        } else {
            throw new IOException("Not supported browser");
        }

        response.setHeader("Content-Disposition", dispositionPrefix
        + encodedFilename);

        if ("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }
    
    
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if (header.indexOf("MSIE") > -1) {
             return "MSIE";
        } else if (header.indexOf("Chrome") > -1) {
             return "Chrome";
        } else if (header.indexOf("Opera") > -1) {
             return "Opera";
        } else if (header.indexOf("Firefox") > -1) {
             return "Firefox";
        } else if (header.indexOf("Mozilla") > -1) {
             if (header.indexOf("Firefox") > -1) {
                  return "Firefox";
             }else{
                  return "MSIE";
             }
        }
        return "MSIE";
   }


    
    public boolean folder(String path)throws SocketException, IOException, Exception{
    	boolean folderTF = false;
    	if(path == null) {
    		path = "";
    	}
    	
    	//폴더 경로 확인을 위해 년월일 split
    	String[] paths = path.split("/");
		
		  for(int i=0; i<paths.length; i++) {
			 System.out.println("paths " + i + " : " + paths[i]); }
		 
    	try {
    		ftp.printWorkingDirectory();
    		ftp.changeWorkingDirectory("/data");
    		ftp.changeWorkingDirectory("/quiz01");
    		
    		
    		if(paths.length >2) {
	    		//년 폴더가 없으면 생성

    			
    			boolean existedY = ftp.changeWorkingDirectory(paths[0]);
	    		if(!existedY) {
	    			ftp.makeDirectory(paths[0]);
	    			ftp.changeWorkingDirectory(paths[0]);
	    		}
	    		//생성 후 이동, 있으면 그냥 이동
	    		//ftp.changeWorkingDirectory(paths[0]);
	    		
	    		ftp.printWorkingDirectory();
	    		
	    		boolean existedM = ftp.changeWorkingDirectory(paths[1]);
	    		if(!existedM) {
	    			ftp.makeDirectory(paths[1]);
	    			ftp.changeWorkingDirectory(paths[1]);
	    		}
	    		
	    		
	    		boolean existedD = ftp.changeWorkingDirectory(paths[2]);
	    		if(!existedD) {
	    			ftp.makeDirectory(paths[2]);
	    			ftp.changeWorkingDirectory(paths[2]);
	    		}
	    		ftp.printWorkingDirectory();
	    		String nowDir = "/"+ paths[0] + "/" + paths[1] + "/" + paths[2];
	    		
	    		if(ftp.printWorkingDirectory().equals(nowDir)) {
	    			folderTF = true;
	    		}
    		}
    	}catch (Exception e) {
    		folderTF = false;
    		e.printStackTrace();
    		
    	}
		return folderTF;
    }
    
    
    //param( 보낼파일경로+파일명, 호스트에서 받을 파일 이름, 호스트 디렉토리 )
    public void uploadFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try(InputStream input = new FileInputStream(new File(localFileFullName))){
        this.ftp.storeFile(hostDir + fileName, input);
        //storeFile() 메소드가 전송하는 메소드
        }
    }
 
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
	public ArrayList<FtphistoryDTO> Getfile(List<FtphistoryDTO> fList) {
		
		log.info(this.getClass() + "Getfile start !!!");
		if(fList == null) {
    		fList = new ArrayList<FtphistoryDTO>();
    	}
		
		String[] depth;
		String ftpPath ="";
		
		
		ArrayList<FtphistoryDTO> addfileList = new ArrayList<FtphistoryDTO>();
		 
		
    	//폴더 경로 확인을 위해 년월일 split
    	try {
    		
    		
    		  for(int k=0; k < fList.size(); k++) { 
    			depth = fList.get(k).getFtp_send_date().substring(0, 10).split("-");
    			ftpPath ="/"+depth[0]+"/"+depth[1]+"/"+depth[2];  
    			  
    			log.info("ftpPath : " + ftpPath);
				ftp.printWorkingDirectory();
				List<String> listfile = new ArrayList<String>();
				
				
				String[] listnames = ftp.listNames(ftpPath);
				
				log.info("length : " + listnames.length);
				for(int i=0; i<listnames.length; i++) {
					log.info("listname : " + listnames[i]);
					listfile.add(i,listnames[i]);
				}
				//DTO set
				FtphistoryDTO addfileDTO = new FtphistoryDTO();
				
				addfileDTO.setFtp_seq(fList.get(k).getFtp_seq()); 
				addfileDTO.setFtp_ip(fList.get(k).getFtp_ip()); 
				addfileDTO.setFtp_hostname(fList.get(k).getFtp_hostname()); 
	  			addfileDTO.setFtp_send_date(fList.get(k).getFtp_send_date()); 
	  			addfileDTO.setFtp_status(fList.get(k).getFtp_status()); 
	  			addfileDTO.setChg_dt( fList.get(k).getChg_dt());
	  			addfileDTO.setList_name(listnames);
	  			addfileList.add(addfileDTO);
	  			addfileDTO =null;
    		  }
    		  for(int i=0; i<addfileList.size(); i++) {
    			  for(int j=0; j< addfileList.get(i).getList_name().length; j++) {
					/* log.info(addfileList.get(i).getList_name()[j]); */
    			  }
    		  }
    	}catch (Exception e) {
    		e.printStackTrace();
    		
    	}
    	log.info(this.getClass() + "Getfile END !!!");
		return addfileList;
	}
	

}

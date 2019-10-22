package poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.FtphistoryDTO;
import poly.dto.UserDTO;
import poly.dto.BoardDTO;
import poly.persistance.mapper.FtphistoryMapper;
import poly.persistance.mapper.UserMapper;
import poly.service.IUserService;
import poly.service.IFtphistoryService;
import poly.util.CmmUtil;
import poly.util.FtpUploader;

@Service("UserService")
public class UesrService implements IUserService {

	private Logger log = Logger.getLogger(this.getClass());
	
	
	@Resource(name = "UserMapper")
	private UserMapper userMapper; 

	@Override
	public UserDTO getUserinfo(UserDTO uDTO) throws Exception {
		if (uDTO == null) {
			uDTO = new UserDTO();
		}
		
		uDTO = userMapper.getUserinfo(uDTO);
		if (uDTO == null) {
			uDTO = new UserDTO();
		}
		log.info("uDTO.get : " + CmmUtil.nvl(uDTO.getEmail()));
		log.info("uDTO.get : " + CmmUtil.nvl(uDTO.getPwd()));
		log.info("uDTO.get : " + CmmUtil.nvl(uDTO.getName()));
		
		return uDTO;
	}

	@Override
	public HashMap<String, Object> getboardList(BoardDTO bDTO) throws Exception {
		if(bDTO == null) {
			bDTO = new BoardDTO();
		}
		
		//?òÏù¥Ïß?
		log.info(" service start!!!" );
		int page = Integer.parseInt(CmmUtil.nvl(bDTO.getPageno()));
		log.info("page : " + page);
		int listCnt = userMapper.gettotalCount();
		log.info("listCnt Ï¥?Í∞?àò : " + listCnt);
		
		
		BoardDTO paging = new BoardDTO();
		paging.pageInfo(page, listCnt);
		
		HashMap<String , Integer> hMap = new HashMap<String, Integer>();
		int i = paging.getStartList();
		int j = paging.getListSize();
		
		hMap.put("startlist",i);
		hMap.put("listsize",j);
		
		List<FtphistoryDTO> fList = userMapper.getEnableList(hMap);
		
		FtpUploader ftpUploader = new FtpUploader();
		int reply = ftpUploader.ftpUploader("118.219.", "", "!");
		log.info("reply : " + reply);
		
		ArrayList<FtphistoryDTO> getfileList= ftpUploader.Getfile(fList);
		log.info("getfileList : " + getfileList);
		
		
		
		ftpUploader.disconnect();
		ftpUploader = null;
		
	
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("fList", fList);
		resultMap.put("fileList", getfileList);
		resultMap.put("paging",paging);
		resultMap.put("listCnt", listCnt);
		
		paging = null;
		fList = null;
		
		log.info(" service end!!!" );
		return resultMap;
	}

	@Override
	public FtphistoryDTO gethistoryInfo(FtphistoryDTO fDTO) throws Exception {
		return userMapper.gethistoryInfo(fDTO);
	}

	@Override
	public boolean resDownload(FtphistoryDTO fDTO, HttpServletResponse res, HttpServletRequest req ) throws Exception {
		
		//FtpUploader.setDisposition(seq, req, res);
		String serverIp ="118.219.";
		String user = "";
		String pwd = "!";
		boolean downloadres = false;
		
		
		FtpUploader ftpUploader = new FtpUploader();
		int reply = ftpUploader.ftpUploader(serverIp, user, pwd);
		log.info("reply : " + reply);
		if(reply == 220) {
			downloadres = ftpUploader.ftpDownload(fDTO.getFtp_send_date() ,fDTO.getFtp_status(), fDTO.getFtp_seq(), fDTO.getFtp_filename(), res, req );
			  log.info("downloadres : " + downloadres);
			  
		}else {
			 log.info("ftp ?§Ïö¥Î°úÎìú ?§Ìå®\n ?ëÏÜç ?§Ìå®.");
		}
		
		ftpUploader = null;
		
		return downloadres;
	}
}

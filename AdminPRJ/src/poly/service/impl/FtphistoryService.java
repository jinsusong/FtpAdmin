package poly.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import poly.dto.FtphistoryDTO;
import poly.persistance.mapper.FtphistoryMapper;
import poly.service.IFtphistoryService;
import poly.util.CmmUtil;
import poly.util.FtpUploader;

@Service("FtphistoryService")
public class FtphistoryService implements IFtphistoryService {

	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "FtphistoryMapper")
	private FtphistoryMapper ftphistoryMapper;

	@Override
	public FtphistoryDTO insertHistory(FtphistoryDTO fDTO) throws Exception {

		log.info("insertHistory start @@@");

		if (fDTO == null) {
			fDTO = new FtphistoryDTO();
		}
		
		FtpUploader ftpUploader = new FtpUploader();
		int reply = ftpUploader.ftpUploader("118.219.", "", "!");

		FtphistoryDTO rDTO = null;
		
		boolean createFolder = false;
		String success = "FAIL";

		// ftp ?ëÏÜç ?±Í≥µ ??220 Î∞òÌôò
		if (reply == 220) {
			log.info("if reply start\n inserDB & create folder & return json");
			int insertHistory = ftphistoryMapper.insertHistory(fDTO);
			log.info("inserHistory : " + insertHistory);
			log.info("get seq : " + CmmUtil.nvl(fDTO.getFtp_seq()));

			// DB insert ?±Í≥µ??1 Î∞òÌôò
			if (insertHistory == 1) {
				rDTO = ftphistoryMapper.getHistory(fDTO);
				log.info("rDTO seq : " + CmmUtil.nvl(rDTO.getFtp_seq()));
				
				log.info("rDTO.get ftpsendDate : " + CmmUtil.nvl(rDTO.getFtp_send_date()));
				
				// create folder
				 createFolder = ftpUploader.folder(CmmUtil.nvl(rDTO.getFtp_send_date())); 
				/* createFolder = ftpUploader.folder("2019-09-06"); */ 
				log.info("createFolder : " + createFolder);
				if (createFolder) {
					success = "OK";
				} else {
					success = "FAIL";
				}
				/* ftpUploader.folder("2019-09-04"); */

			}else {
				success = "FAIL";
			}

			//FTP ?ëÏÜç ?¥Ï†ú Î∞?Î©îÎ™®Î¶??†Îãπ ?¥Ï†ú
			ftpUploader.disconnect();
			ftpUploader = null;
			

		}else {
			success = "FAIL"; 
		}

		//?úÎπÑ???§Ìñâ ÏµúÏ¢ÖÍ≤∞Í≥º ?Ä?•Ìï¥??Ïª®Ìä∏Î°§Îü¨ Î≥¥ÎÇ¥Í∏?
		rDTO.setSuccess(success);
		log.info("rDTO. set success : " + rDTO.getSuccess());
		log.info("insertHistory End!!!");
		
		return rDTO;
	}

	@Override
	public FtphistoryDTO getHistory(FtphistoryDTO fDTO) throws Exception {
		return ftphistoryMapper.getHistory(fDTO);
	}

	@Override
	public FtphistoryDTO updateHistory(FtphistoryDTO fDTO) throws Exception {
		if(fDTO == null) {
			fDTO = new FtphistoryDTO();
		}
		int updateHistory = ftphistoryMapper.updateHistory(fDTO);
		log.info("update : " + updateHistory);
		
		FtphistoryDTO rDTO = new FtphistoryDTO();
		if(rDTO == null) {
			rDTO = new FtphistoryDTO();
		}
		String success= null;
		
		if (updateHistory == 1) {
			log.info("update ?±Í≥µ ");
			success = "OK";
		} else {
			log.info("update ?§Ìå® ");
			success = "FAIL";
		}
		rDTO.setSuccess(success);
		
		
		return rDTO;
	}

	@Override
	public int deleteHistory(FtphistoryDTO rDTO) throws Exception {
		log.info("start deleteHistory !!");
		log.info("end deleteHistory !!");
		return ftphistoryMapper.deleteHistory(rDTO);
	}

	/*
	 * @Override public int insertFilename(FtphistoryDTO rDTO) throws Exception {
	 * return ftphistoryMapper.insertFilename(rDTO); }
	 */

}

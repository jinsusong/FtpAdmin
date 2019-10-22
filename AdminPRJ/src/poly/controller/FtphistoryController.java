package poly.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.FtphistoryDTO;
import poly.service.IFtphistoryService;
import poly.util.CmmUtil;

/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 * */
@CrossOrigin(origins="*")
@Controller
public class FtphistoryController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "FtphistoryService")
	private IFtphistoryService ftphistoryService;

	/*
	 * @GetMapping(value="/sendFtpAsk") public ResponseEntity<FtphistoryDTO>
	 * sendFtpAsk(
	 * 
	 * @PathVariable(value="p_ip") String p_ip,
	 * 
	 * @PathVariable(value="p_host") String p_host )throws Exception {
	 * log.info(this.getClass() + "sendFtpAsk start !!!");
	 * 
	 * FtphistoryDTO fDTO = new FtphistoryDTO(); fDTO.setP_host(p_host);
	 * fDTO.setP_ip(p_ip);
	 * 
	 * 
	 * log.info(this.getClass() + "sendFtpAsk end !!!"); return new
	 * ResponseEntity<FtphistoryDTO>(fDTO, HttpStatus.OK); }
	 */

	@RequestMapping(value = "/sendFtpAsk", method=RequestMethod.GET , produces="text/plain;charset=UTF-8")
	public @ResponseBody String sendFtpAsk(HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("sendFtpAsk start @@@");

		String p_ip = CmmUtil.nvl(req.getParameter("p_ip"));
		String p_host = CmmUtil.nvl(req.getParameter("p_host"));
		
		String msg = "";
		
		if (p_ip.length()<1) {
			msg = "p_ip 파라미터의 값이 누락되었습니다.";
		}
		
		if (p_host.length()<1) {
			msg = "p_host 파라미터의 값이 누락되었습니다.";
		}

		FtphistoryDTO rDTO = new FtphistoryDTO();
		
		//초기 유효성 검사에서 데이터가 정확히 넘어온 경우
		if (msg.length()<1) {
			
			FtphistoryDTO fDTO = new FtphistoryDTO();
			
			fDTO.setP_ip(p_ip);
			fDTO.setP_host(p_host);
			
			rDTO = ftphistoryService.insertHistory(fDTO);
			if(rDTO == null) {
				rDTO = new FtphistoryDTO();
			}
			
			fDTO = null;
		}
		
		// 상태정보 json 형태 만들기
		
		JSONObject jo1 = new JSONObject();
		log.info("CmmUtil.nvl(rDTO.getSuccess()) : " + CmmUtil.nvl(rDTO.getSuccess()));
		log.info(CmmUtil.nvl(rDTO.getFtp_send_date()));
		log.info("seq : " + CmmUtil.nvl(rDTO.getFtp_seq()));
		
		jo1.put("success", CmmUtil.nvl(rDTO.getSuccess()));
		if(CmmUtil.nvl(rDTO.getSuccess()).equals("OK")) {
			jo1.put("seq", CmmUtil.nvl(rDTO.getFtp_seq()));
			jo1.put("uploadPath",  CmmUtil.nvl(rDTO.getFtp_send_date()));
			//보낸시간을 filename으로 설정 동일한 값이 생기지 않게
			/*
			rDTO.setFtp_filename(rDTO.getFtp_seq()+rDTO.getFtp_hostname());
			
			 * int filename = ftphistoryService.insertFilename(rDTO); jo1.put("filename",
			 * CmmUtil.nvl(rDTO.getFtp_filename()));
			 */
		}else {
			msg = "FTP 초기 작업이 실패하였습니다.";
			jo1.put("success", "FAIL");
			jo1.put("message", msg);
			jo1.put("seq", CmmUtil.nvl(rDTO.getFtp_seq()));
			int deleteHistory = ftphistoryService.deleteHistory(rDTO);
			log.info("deleteHistory  : " + deleteHistory);
			
		}
		
		
		String jsonStr = jo1.toJSONString();
		System.out.println(jsonStr.replace("\\", ""));

		log.info("sendFtpAsk end @@@");
		return jsonStr.replace("\\", "");
	}

	// ftp 전송 완료api
	@RequestMapping(value = "/sendFtpOk", method=RequestMethod.GET , produces="text/plain;charset=UTF-8")
	public @ResponseBody String sendFtpOk(HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("sendFtpOk start !!");
		String ftp_seq = CmmUtil.nvl(req.getParameter("ftp_seq"));
		log.info("ftp_seq : " + CmmUtil.nvl(ftp_seq));
		
	String msg = "";
		
		if (ftp_seq.length()<1) {
			msg = "ftp_seq 파라미터의 값이 누락되었습니다.";
		}
		
		FtphistoryDTO uDTO = new FtphistoryDTO();
		
		if (msg.length()<1) {
			
			FtphistoryDTO fDTO = new FtphistoryDTO();
			
			fDTO.setFtp_seq(ftp_seq);
			
			uDTO = ftphistoryService.updateHistory(fDTO);
			if(uDTO == null) {
				uDTO = new FtphistoryDTO();
			}
			log.info("uDTO : " + uDTO.getSuccess());
			fDTO = null;
		}
		

		// 상태정보 json 형태 만들기
		
			JSONObject jo1 = new JSONObject();
			jo1.put("updsuccess", CmmUtil.nvl(uDTO.getSuccess()));
			
			if(CmmUtil.nvl(uDTO.getSuccess()).equals("OK")) {
				jo1.put("updsuccess", CmmUtil.nvl(uDTO.getSuccess()));
			}else {
				msg = "FTP 완료 작업이 실패하였습니다.";
				jo1.put("updsuccess", "FAIL");
				jo1.put("message", CmmUtil.nvl(msg));
				
			}
			String jsonStr = jo1.toJSONString();
			System.out.println(jsonStr);
	
		

		ftp_seq = null;
		uDTO = null;
		
		log.info("sendFtpOk end !!");
		return jsonStr;
	}

}

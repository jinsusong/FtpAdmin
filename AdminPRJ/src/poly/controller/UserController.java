package poly.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.net.URLCodec;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import poly.dto.NoticeDTO;
import poly.dto.UserDTO;
import poly.dto.BoardDTO;
import poly.dto.FtphistoryDTO;
import poly.service.IUserService;
import poly.service.INoticeService;
import poly.util.AES256Util;
import poly.util.CmmUtil;
import poly.util.FtpDownload;
import poly.util.FtpUploader;
import sun.net.ftp.FtpClient;

/*
 * Controller 선언해야만 Spring 프레임워크에서 Controller인지 인식 가능
 * 자바 서블릿 역할 수행
 * */
@Controller
public class UserController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource(name = "UserService")
	private IUserService userService;

	// 관리자 로그인 화면 보여주기
	@RequestMapping(value = "/signIn", method = { RequestMethod.GET, RequestMethod.POST })
	public String Index() {
		log.info(this.getClass());
		log.info("/signIn");
		return "/admin/signIn";
	}

	// 로그인 후 admin/home 보여주기
	@RequestMapping(value = "admin/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String NoticeReg(HttpServletRequest req, HttpServletResponse res, ModelMap model, HttpSession session)
			throws Exception {
		log.info(this.getClass().getName() + ".admin/home start!");
		String email = CmmUtil.nvl(req.getParameter("email"));

		/* aes */
		String key = "aes256-quiztok-key";
		AES256Util aes256 = new AES256Util(key);
		URLCodec codec = new URLCodec();
		
		String encPwd = codec.encode(aes256.aesEncode(CmmUtil.nvl(req.getParameter("pwd"))));
		/* String decLoginidx = aes256.aesDecode(codec.decode(encPwd)); */
		
		
		/* aes */

		UserDTO uDTO = new UserDTO();
		uDTO.setEmail(email);
		uDTO.setPwd(encPwd);

		email = "";

		String url = "";
		String msg = "";
		System.out.println(!uDTO.getEmail().equals(""));
		System.out.println(!uDTO.getPwd().equals(""));

		System.out.println((uDTO.getEmail() != null) && (uDTO.getPwd() != null));
		if ((uDTO.getEmail() != null) && (uDTO.getPwd() != null)) {
			log.info("email, pwd exist");

			uDTO = userService.getUserinfo(uDTO);
			if (CmmUtil.nvl(uDTO.getName()) != "") {
				session.setAttribute("name", CmmUtil.nvl(uDTO.getName()));
				msg = "로그인 성공하셨습니다!";
				url = "/admin/homeView.do";
			} else {
				msg = "로그인 실패하셨습니다";
				url = "/signIn.do";
			}
		} else {
			log.info("email, pwd is empty");

			url = "/signIn.do";
			msg = "로그인 실패하였습니다.";
		}

		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		log.info(this.getClass().getName() + ".admin/home end!");

		url = null;
		msg = null;
		uDTO = null;

		return "/alert";
	}

	// 로그아웃
	@RequestMapping(value = "/signOut", method = { RequestMethod.GET, RequestMethod.POST })
	public String signOut(HttpSession session) {
		log.info(this.getClass());
		log.info("/signOut start !");
		session.invalidate();
		log.info("/signOut end !");
		return "redirect:/signIn.do";
	}

	// 관리자 로그인 화면 보여주기
	@RequestMapping(value = "/admin/homeView", method = { RequestMethod.GET, RequestMethod.POST })
	public String homeView(Model model) throws Exception {
		log.info(this.getClass());
		log.info("/homeView start !");
		
		String pageno = "1";
		
		//페이징 하는 DTO
		BoardDTO bDTO = new BoardDTO();
		bDTO.setPageno(pageno);

		// 페이징 끝나고 리스트 가져온 DTO
		HashMap<String, Object> resultMap = userService.getboardList(bDTO);
		List<FtphistoryDTO> fileList=	(List<FtphistoryDTO>)resultMap.get("fileList"); 
		BoardDTO paging = (BoardDTO)resultMap.get("paging");        	
		int listCnt = (int)resultMap.get("listCnt");
		
	
		
		  System.out.println("fileList : " + fileList); 
		  for(int i=0; i < fileList.size(); i++) {
			  System.out.println(fileList.get(i).getFtp_seq()); 
		  }
		 
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("paging", paging);
		model.addAttribute("listCnt", listCnt);
		
		resultMap = null;
		paging = null;
		bDTO = null;
		pageno = "";
		
		log.info(this.getClass() + " adminBoard end !!!");
		

		/*
		 * $.ajax({ url : "/admin/board.do", method : "post", data : { "pageno" : "1",
		 * }, dataType: "text", success:function(data){ $('#fList').html(data); } });
		 */

		log.info("/homeView end !");
		return "/admin/home";
	}
	// 관리자 ftphitory 호출

	@RequestMapping(value = "/admin/board", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminBoard(HttpServletRequest req, Model model) throws Exception {
		log.info(this.getClass() + " adminBoard start !!!");
		String pageno = req.getParameter("pageno");
		log.info("pageno : " + pageno);

		//페이징 하는 DTO
		BoardDTO bDTO = new BoardDTO();
		bDTO.setPageno(pageno);

		// 페이징 끝나고 리스트 가져온 DTO
		HashMap<String, Object> resultMap = userService.getboardList(bDTO);
		List<FtphistoryDTO> fileList=	(List<FtphistoryDTO>)resultMap.get("fileList"); 
		BoardDTO paging = (BoardDTO)resultMap.get("paging");        	
		int listCnt = (int)resultMap.get("listCnt");
		
	
		/*
		 * System.out.println("fList : " + fList); for(int i=0; i < fList.size(); i++) {
		 * System.out.println(fList.get(i).getFtp_seq()); }
		 */
		
		model.addAttribute("fileList", fileList);
		model.addAttribute("paging", paging);
		model.addAttribute("listCnt", listCnt);
		
		resultMap = null;
		paging = null;
		bDTO = null;
		pageno = "";
		
		log.info(this.getClass() + " adminBoard end !!!");
		
		
		return "/admin/table";
	}
	//ftp 관리자 페이지에서 다운로드 요청
	@RequestMapping(value="/admin/ftpDownload",method= {RequestMethod.GET, RequestMethod.POST})
	public void ftpDownload(HttpServletRequest req, HttpServletResponse res, Model model)throws Exception{
		log.info(this.getClass() + ".ftpDownload start !!!");
		String seq = CmmUtil.nvl(req.getParameter("seq"));
		String sendDt = CmmUtil.nvl(req.getParameter("sendDt"));
		String status = CmmUtil.nvl(req.getParameter("status"));
		String filename = CmmUtil.replace2(CmmUtil.nvl(req.getParameter("filename")));
		
		/* /2019/10/17/실습장비 지원신청서& #40;양식& #41;_2019년도.xlsx */
		
		log.info("seq : " +  seq);
		log.info("sendDt : " +  sendDt);
		log.info("status : " +  status);
		log.info("filename : " +  filename);
		
		
		FtphistoryDTO fDTO = new FtphistoryDTO();
		boolean result = false;
		fDTO.setFtp_seq(seq);
		fDTO.setFtp_send_date(sendDt);
		fDTO.setFtp_status(status);
		fDTO.setFtp_filename(filename);
		
		String msg = "";
		String url = "";
		try {
			boolean resDownload = userService.resDownload(fDTO, res, req);	
			
			log.info("resDownload : " + resDownload);
			  if(resDownload) { 
				  //ftp 파일 존재 
				  msg = "ftp 다운로드 완료,\\nPath : /My documents/퀴즈톡 받은 파일" ; 
			  }else { 
				  //ftp 파일 존재  하지 않음 
				  msg ="ftp 다운로드 실패,\\n파일이 존재하지 않습니다.";
			  }
			url="/admin/homeView.do";
			
			model.addAttribute("url", url);
			model.addAttribute("msg", msg);
			
			
			url = "";
			msg ="";
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		log.info(this.getClass() + ".ftpDownload end !!!");
		
	}
	
	

}

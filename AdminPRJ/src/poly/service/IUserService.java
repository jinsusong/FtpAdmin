package poly.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import poly.dto.BoardDTO;
import poly.dto.FtphistoryDTO;
import poly.dto.UserDTO;
/*import poly.dto.boardDTO;*/


public interface IUserService {
	UserDTO getUserinfo(UserDTO uDTO)throws Exception;

	HashMap<String, Object> getboardList(BoardDTO bDTO)throws Exception;

	FtphistoryDTO gethistoryInfo(FtphistoryDTO fDTO)throws Exception;

	boolean resDownload(FtphistoryDTO fDTO, HttpServletResponse res, HttpServletRequest req) throws Exception;




}

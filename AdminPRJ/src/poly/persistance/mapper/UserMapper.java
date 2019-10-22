package poly.persistance.mapper;

import java.util.HashMap;
import java.util.List;

import config.Mapper;
import poly.dto.BoardDTO;
import poly.dto.FtphistoryDTO;
import poly.dto.NoticeDTO;
import poly.dto.UserDTO;

@Mapper("UserMapper")
public interface UserMapper {
	UserDTO getUserinfo(UserDTO uDTO)throws Exception;

	int gettotalCount()throws Exception;

	List<FtphistoryDTO> getEnableList(HashMap<String, Integer> hMap)throws Exception;

	FtphistoryDTO gethistoryInfo(FtphistoryDTO fDTO)throws Exception;

}

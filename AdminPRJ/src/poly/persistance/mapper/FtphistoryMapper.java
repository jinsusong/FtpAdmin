package poly.persistance.mapper;

import java.util.List;

import config.Mapper;
import poly.dto.FtphistoryDTO;
import poly.dto.NoticeDTO;

@Mapper("FtphistoryMapper")
public interface FtphistoryMapper {

	int insertHistory(FtphistoryDTO fDTO)throws Exception;

	FtphistoryDTO getHistory(FtphistoryDTO fDTO)throws Exception;

	int updateHistory(FtphistoryDTO fDTO)throws Exception;

	int deleteHistory(FtphistoryDTO rDTO)throws Exception;

	int insertFilename(FtphistoryDTO rDTO)throws Exception;

}

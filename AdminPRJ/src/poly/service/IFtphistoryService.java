package poly.service;

import poly.dto.FtphistoryDTO;


public interface IFtphistoryService {
	FtphistoryDTO insertHistory(FtphistoryDTO fDTO)throws Exception;

	FtphistoryDTO getHistory(FtphistoryDTO fDTO)throws Exception;

	FtphistoryDTO updateHistory(FtphistoryDTO fDTO)throws Exception;

	int deleteHistory(FtphistoryDTO rDTO)throws Exception;

	/* int insertFilename(FtphistoryDTO rDTO)throws Exception; */
	
}


package poly.dto;

public class BoardDTO {

	private int listCnt; // 총 게시물 개수 
	private int pageCnt; //총 페이지 범뮈의 개 
	private	int page; // 현재 페이지(1,2,3, ~ ,31,32,33) 
	private int range; //현재 페이지 범위(1~10,	11~20,21~30)
	private int listSize = 10; // 한 페이지 목록의 개수(한 페이지에 출력되는 게시글 개수)10
	private int rangeSize = 10; // 한 페이지 범위의 개수(한 페이지에 출력되는 범위 개수)10 
	private int	startPage; // 시작번호(1,11,21,31) 
	private int endPage; //끝번호(10,20,30,33) 
	private	int startList; // 게시판 시작번호 
	private boolean prev; //이전페이지(<) 
	private boolean	next; // 다음페이지(>)

	private String pageno; // 페이지 번호
	
	

	public int getListCnt() {
		return listCnt;
	}

	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getRangeSize() {
		return rangeSize;
	}

	public void setRangeSize(int rangeSize) {
		this.rangeSize = rangeSize;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartList() {
		return startList;
	}

	public void setStartList(int startList) {
		this.startList = startList;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public String getPageno() {
		return pageno;
	}

	public void setPageno(String pageno) {
		this.pageno = pageno;
	}

	public void pageInfo(int page, int listCnt) { 
		this.endPage = page;
		this.listCnt = listCnt;
  
  //전체 페이지 수 
	this.pageCnt = (int)Math.ceil(listCnt/(listSize*1.0));
	//현재 페이지 범위
	this.range=(int)Math.ceil(page/10.0); 
	//시작 페이지 
	this.startPage=(range-1)*rangeSize +1; 
	//끝 페이지 
	this.endPage =range * rangeSize; 
	//게시판 시작번호
	this.startList=(page -1) * listSize; 
	//이전 버튼 상태 
	this.prev = range == 1 ?false : true; 
	//다음 버튼 상태 
	this.next = endPage > pageCnt ? false : true;
  
	if(this.endPage > this.pageCnt) { 
		this.endPage = this.pageCnt; 
		this.next = false; 
		} 
	}
}

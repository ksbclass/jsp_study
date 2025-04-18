CREATE TABLE board (
    num INT AUTO_INCREMENT PRIMARY KEY ,  -- 게시글 번호. 기본키
    writer VARCHAR(30),					  -- 게시글 작성자
    pass VARCHAR(30),					  -- 게시글 비밀번호(수정 삭제시 필요)
    title VARCHAR(100),					  -- 게시글 제목
    content VARCHAR(2000),				  -- 게시글 내용
    file1 VARCHAR(200),					  -- 첨부파일명
    boardid VARCHAR(2),					  -- 게시판 종류 : 1: 공지사항, 2: 자유 게시판
    regdate DATETIME,					  -- 게시글 등록 일자,일시
    readcnt INT(10),                      -- 조회수.상세보기시 1씩 증가
    grp INT,							  -- 답글 작성시 원글의 게시글 번호
    grplevel INT(3),                      -- 답글의 레벨 0 : 원글, 1 : 원글의 답글, 2 : 답글의 답글 ...
    grpstep INT(5)                        -- 그룹의 게시 순서
);

select * from board;

select * from book;


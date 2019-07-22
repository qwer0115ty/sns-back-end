package com.boot.service;

import java.io.File;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot.jpa.board.BoardFileRepository;
import com.boot.jpa.board.BoardLikeRepository;
import com.boot.jpa.board.BoardRepository;
import com.boot.jpa.board.support.BoardLikeRepositorySupport;
import com.boot.jpa.board.support.BoardRepositorySupport;
import com.boot.model.AwsS3File;
import com.boot.model.board.Board;
import com.boot.model.board.BoardFile;
import com.boot.model.board.BoardLike;
import com.boot.model.board.BoardLikeResponseBoardDto;
import com.boot.model.board.BoardsTarget;
import com.boot.util.CustomUtils;
import com.google.common.reflect.TypeToken;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private AwsS3Service awsS3Service;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardFileRepository boardFileRepository;
	
	@Autowired
	private BoardLikeRepository boardLikeRepository;
	
	@Autowired
	private BoardLikeRepositorySupport boardLikeRepositorySupport;
	
	@Autowired
	private BoardRepositorySupport boardRepositorySupport;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ServletContext servletContext;
	
	@Transactional
	@Override
	public Board insertBoard (int userSeq, MultipartFile mf, String content) throws Exception {
		Board b = new Board();
		b.setUserSeq(userSeq);
		b.setContent(content);
		b = boardRepository.save(b);

		File file = CustomUtils.makeTempFile(mf, servletContext.getRealPath("/"));
		file = CustomUtils.makeSquareImage(file);
		
		AwsS3File asf = new AwsS3File();
		asf.setOriginalFilename(mf.getOriginalFilename());
		asf.setSaveFilename(file.getName());
		asf.setPath("board/" + b.getSeq() + "/");
		
		asf = awsS3Service.uploadFile(file, asf);
		
		BoardFile bf = new BoardFile();
		bf.setAwsS3Seq(asf.getSeq());
		bf.setBoardSeq(b.getSeq());
		bf = boardFileRepository.save(bf);
		
		file.delete();
		
		return b;
	}
	
	@Transactional
	@Override
	public Board getBoard (int seq) {
		Board board = boardRepository.findOne(seq);
		
		board.getUser();

		BoardFile bf = boardFileRepository.findFirstByBoardSeq(board.getSeq());
		bf.getAwsS3File();
		
		board.setBoardFile(bf);
		return board;
	}
	
	@Transactional
	@Override
	public Page<Board> getBoards (Pageable pageable, BoardsTarget target) {
		Page<Board> boards = null;
		
		if (target.equals(BoardsTarget.ALL)) {
			boards = boardRepository.findAllByOrderBySeqDesc(pageable);
		} else if (target.equals(BoardsTarget.LIKE)) {
			int userSeq = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
			boards = boardLikeRepositorySupport.getLikedBoards(pageable, userSeq);
		} else if (target.equals(BoardsTarget.FOLLOW)) {
			int userSeq = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
			boards = boardRepositorySupport.getFollowingUsersBoards(pageable, userSeq);
		}
		boards.forEach(board -> {
			board.getUser();
			BoardFile bf = boardFileRepository.findFirstByBoardSeq(board.getSeq());
			bf.getAwsS3File();
			board.setBoardFile(bf);
		});
		return boards;
	}
	
	@Transactional
	@Override
	public int deleteBoard (Board board) throws Exception {
		boardLikeRepository.deleteByBoardSeq(board.getSeq());
		boardFileRepository.delete(board.getBoardFile().getSeq());
		awsS3Service.deleteFile(board.getBoardFile().getAwsS3File());
		boardRepository.delete(board);
		return 1;
	}
	
	@Transactional
	@Override
	public Board updateBoard (Board board, MultipartFile mf) throws Exception {
		if(mf != null) {
			File file = CustomUtils.makeTempFile(mf, servletContext.getRealPath("/"));
			file = CustomUtils.makeSquareImage(file);
			
			AwsS3File asf = new AwsS3File();
			asf.setOriginalFilename(mf.getOriginalFilename());
			asf.setSaveFilename(file.getName());
			asf.setPath("board/" + board.getSeq() + "/");
			
			asf = awsS3Service.uploadFile(file, asf);
			
			BoardFile boardFile  = board.getBoardFile();
			AwsS3File deleteFile = boardFile.getAwsS3File();
			
			boardFile.setAwsS3Seq(asf.getSeq());
			boardFileRepository.save(boardFile);
			awsS3Service.deleteFile(deleteFile);
			
			file.delete();
		}
		
		return boardRepository.save(board);
	}
	
	@Transactional
	public BoardLikeResponseBoardDto setBoardLike (int userSeq, int boardSeq) throws Exception {
		BoardLike bl = boardLikeRepository.findFirstByBoardSeqAndUserSeq(boardSeq, userSeq);
		
		if(bl != null) {
			boardLikeRepository.delete(bl);
		} else {
			bl = new BoardLike();
			bl.setBoardSeq(boardSeq);
			bl.setUserSeq(userSeq);
			
			boardLikeRepository.save(bl);
		}
		
		boardLikeRepository.flush();
		Board b = boardRepository.findOne(boardSeq);
		
		return modelMapper.map(b, new TypeToken<BoardLikeResponseBoardDto>() {
			private static final long serialVersionUID = 1L;}.getType());
	}
	
	@Transactional
	@Override
	public Page<Board> getBoards (Pageable pageable, int userSeq) {
		Page<Board> boards = boardRepository.findByUserSeqOrderBySeqDesc(pageable, userSeq);;
		
		boards.forEach(board -> {
			board.getUser();
			BoardFile bf = boardFileRepository.findFirstByBoardSeq(board.getSeq());
			bf.getAwsS3File();
			board.setBoardFile(bf);
		});
		return boards;
	}
}

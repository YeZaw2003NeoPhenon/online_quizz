package com.example.online_quizz_ritzy_system.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.online_quizz_ritzy_system.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	 @Query("SELECT CASE WHEN COUNT(q) > 0 THEN TRUE ELSE FALSE END " + 
			"FROM Question q WHERE q.question = :question AND q.subject = :subject")
	 boolean existsByQuestionAndSubject(@Param("question") String question, @Param("subject") String subject);
    
   	 @Query("SELECT DISTINCT q.subject FROM Question q")
	 List<String> findDistinctSubject();
	 
	 Page<Question>findAllBySubjectAndQuestionContaining(String subject , String question , Pageable pageable);
	 
	 Page<Question>findBySubject(String subject, Pageable pageable);
	 
	 Page<Question>findAllByQuestionContaining(String question , Pageable pageable);
	 
}

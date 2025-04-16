package com.example.form.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.form.entity.Form;


public interface FormRepository extends JpaRepository<Form, Integer> {

    // Fix the method name: change from finaALl to findAll
    Page<Form> findAll(Pageable pageable);
    
 // Assuming your entity is named 'Student'
	/*
	 * Page<Form>
	 * findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCase(
	 * String name, String email, String mobile, Pageable pageable);
	 */
	/*
	 * Page<Form>
	 * findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileContainingIgnoreCase
	 * (String keyword, String keyword2, String keyword3, Pageable pageable);
	 */
	/*
	 * @Query("SELECT s FROM Student s WHERE " +
	 * "CAST(s.stdId AS string) LIKE %:keyword% OR " +
	 * "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.mobile) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.education) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.dob) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.gender) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	 * "LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))") Page<Form>
	 * searchAllFields(String keyword, Pageable pageable);
	 */
    
    @Query("SELECT f FROM Form f WHERE " +
    	       "LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(f.mobile) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(f.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(f.education) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "STR(f.dob) LIKE CONCAT('%', :keyword, '%') OR " +
    	       "LOWER(f.gender) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(f.address) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    	List<Form> searchAll(@Param("keyword") String keyword);


}

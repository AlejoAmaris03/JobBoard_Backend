package com.springboot.job_platform.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.springboot.job_platform.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
    public List<Company> findAllByOrderByIdAsc();

    @Query(
   """
      SELECT c
      FROM Company c
      WHERE LOWER(c.name) = LOWER(?1)
   """)
   public Company findByName(String name);
    
   @Query(
   """
      SELECT c
      FROM Company c
      WHERE c.id != ?1 AND LOWER(c.name) = LOWER(?2)
   """)
   public Company findByNameExcluding(int idCompany, String name);
}

package com.adeilsonl.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adeilsonl.todosimple.models.Task;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    //função do spring
    //automação, melhor reparação de código, menor chance de erro
    List<Task> findByUser_Id(Long id);

    //JPQL
    /*
    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    List<Task> findByUser_Id(@Param("id") Long id);
    */
    
    //query nativa sql
    /*
    @Query(value = "SELECT * task t WHERE t.user_id = :id", nativeQuery = true)
    List<Task> findByUser_Id(@Param("id") Long id);
    */
}

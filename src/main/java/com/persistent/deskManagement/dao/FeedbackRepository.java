package com.persistent.deskManagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {


}
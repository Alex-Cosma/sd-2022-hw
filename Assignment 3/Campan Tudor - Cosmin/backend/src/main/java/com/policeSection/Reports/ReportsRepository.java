package com.policeSection.Reports;

import com.policeSection.Reports.Model.Report;
import com.policeSection.section.Model.Section;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportsRepository extends MongoRepository<Report, String> {
    List<Report> findAll();
}

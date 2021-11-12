package com.example.demo.Repositories;

import com.example.demo.Model.PDFFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PDFFileRepository extends JpaRepository<PDFFile, Long> {
    PDFFile findPDFFileById(Long id);
}

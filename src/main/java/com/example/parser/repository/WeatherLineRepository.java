package com.example.parser.repository;

import com.example.parser.model.WeatherLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLineRepository extends JpaRepository<WeatherLine, String> {
}

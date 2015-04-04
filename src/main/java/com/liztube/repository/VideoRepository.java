package com.liztube.repository;

import com.liztube.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

/**
 * Data access layer to videos
 */
public interface VideoRepository extends JpaRepository<Video, Long> {
    Video findByKey(String key);
}

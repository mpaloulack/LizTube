package com.liztube.repository;

import com.liztube.entity.Video;
import com.liztube.repository.custom.VideoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access layer to videos
 */
public interface VideoRepository extends JpaRepository<Video, Long>, VideoRepositoryCustom {
    Video findByKey(String key);

    //Search methods
    //Page<Video> findByIspublic(boolean isPublic, Pageable pageable);
    //Page<Video> findByOwner_Id(long ownerId, Pageable pageable);
    //Page<Video> findByOwner_IdAndIspublicTrue(long ownerId, Pageable pageable);

    //ignoreCase
}

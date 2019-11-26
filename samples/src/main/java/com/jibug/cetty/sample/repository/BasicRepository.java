/**
 * 
 */
package com.jibug.cetty.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author zhoujumbo
 *
 */
@NoRepositoryBean
public interface BasicRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}

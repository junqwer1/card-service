package org.koreait.global.repositories;

import org.koreait.global.entities.CodeValue;
import org.springframework.data.repository.CrudRepository;

public interface CodeValueRepository extends CrudRepository<CodeValue, String> {
    CodeValue findByCode(String code);
}

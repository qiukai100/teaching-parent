package com.customize.activity.service;

import com.customize.activity.domain.vo.ActModelVO;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ModelerService {

    Model createModel(ActModelVO actModelVO) throws Exception;

    Model getModelById(String modelId);

    Page<Model> getAllModels(Pageable pageable);

    List<ProcessDefinition> deployModel(String modelId) throws Exception;

    void deleteModel(String modelId);

    byte[] getModelXml(String modelId) throws IOException;

}
